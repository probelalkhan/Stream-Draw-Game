package io.getstream.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.ConnectionData
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.utils.Result
import io.getstream.data.AppPreferences
import io.getstream.data.GameConnectionState
import io.getstream.utils.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val chatClient: ChatClient,
    private val prefs: AppPreferences
) : ViewModel() {

    private val userId: String
        get() = prefs.userId ?: generateUserId().also { prefs.userId = it }

    private val _gameConnectionState =
        MutableStateFlow<GameConnectionState>(GameConnectionState.None)
    val gameConnectionState: StateFlow<GameConnectionState>
        get() = _gameConnectionState

    val connectedChannel: Flow<Channel?> = _gameConnectionState.filterIsInstance<GameConnectionState.Success>()
        .mapNotNull { it.channel }

    private suspend fun connectUser(displayName: String): Result<ConnectionData> {
        if (chatClient.getCurrentUser() != null) {
            chatClient.disconnect()
        }

        val user = User(
            id = userId,
            extraData = mutableMapOf(
                KEY_NAME to displayName
            )
        )

        val token = chatClient.devToken(userId)
        return chatClient.connectUser(user, token).await()
    }

    private suspend fun createChannel(
        groupId: String,
        displayName: String,
        limitUser: Int,
        limitTime: Int
    ): Result<Channel> {
        return chatClient.createChannel(
            CHANNEL_MESSAGING,
            groupId,
            listOf(userId),
            extraData = mutableMapOf(
                KEY_NAME to displayName.groupName,
                KEY_LIMIT_USER to limitUser,
                KEY_LIMIT_TIME to limitTime,
                KEY_HOST_NAME to displayName
            )
        ).await()
    }

    fun createGameGroup(displayName: String, limitUser: Int, limitTime: Int) =
        viewModelScope.launch {
            val connection = connectUser(displayName)
            if (connection.isSuccess) {
                _gameConnectionState.emit(GameConnectionState.Loading)
                val groupId = generateGroupId()
                val channel = createChannel(
                    groupId = groupId,
                    displayName = displayName,
                    limitUser = limitUser,
                    limitTime = limitTime
                )
                if (channel.isSuccess) {
                    _gameConnectionState.emit(GameConnectionState.Success(channel.data()))
                } else {
                    _gameConnectionState.emit(GameConnectionState.Failure(channel.error()))
                }
            }
        }

    fun joinGameGroup(displayName: String, groupId: String) = viewModelScope.launch{
        val connection = connectUser(displayName)
        if(connection.isSuccess){
            _gameConnectionState.emit(GameConnectionState.Loading)
            val channel = chatClient.channel(groupId.channelId)
            val result = channel.addMembers(userId).await()
            if(result.isSuccess){
                _gameConnectionState.emit(GameConnectionState.Success(result.data()))
            }else{
                _gameConnectionState.emit(GameConnectionState.Failure(result.error()))
            }
        }
    }
}
