package io.getstream.ui.game

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import io.getstream.chat.android.client.channel.subscribeFor
import io.getstream.chat.android.client.events.ChannelUpdatedByUserEvent
import io.getstream.chat.android.client.events.NewMessageEvent
import io.getstream.chat.android.client.models.Message
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.utils.onSuccess
import io.getstream.data.GameChatMessage
import io.getstream.data.RandomWords
import io.getstream.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel @AssistedInject constructor(
    private val randomWord: RandomWords,
    private val chatClient: ChatClient,
    @Assisted val cid: String
) : ViewModel() {

    private val channelClient = chatClient.channel(cid)

    private val firebaseDb = FirebaseDatabase.getInstance().getReference(channelClient.groupId)

    private val _isHost = MutableStateFlow(false)
    val isHost: StateFlow<Boolean>
        get() = _isHost

    private val _randomWords = MutableStateFlow<List<String>?>(null)
    val randomWords: StateFlow<List<String>?>
        get() = _randomWords


    private val _gameChatMessages = MutableStateFlow<List<GameChatMessage>>(listOf())
    val gameChatMessages: StateFlow<List<GameChatMessage>>
        get() = _gameChatMessages

    private val _selectedWord = MutableStateFlow<String?>(null)
    val selectedWord: StateFlow<String?> = _selectedWord

    private val _newDrawingImage: MutableState<String?> = mutableStateOf(null)
    val newDrawingImage: State<String?> = _newDrawingImage

    init {
        fetchChannelInformation()
        subscribeToChannelEvents()
        subscribeToNewMessageEvent()
    }

    fun sendGuessToChannel(guess: String) = viewModelScope.launch {
        channelClient.sendMessage(
            Message(user = chatClient.getCurrentUser()!!, text = guess)
        ).await()
    }

    fun setSelectedWord(word: String) = viewModelScope.launch {
        val hostName = chatClient.getCurrentUser()?.name ?: return@launch
        channelClient.update(
            extraData = mutableMapOf(
                KEY_SELECTED_WORD to word,
                KEY_NAME to hostName.groupName, // channel name.
                KEY_HOST_NAME to hostName, // host name.
            )
        ).await()
    }

    fun broadcastBitmap(bitmap: Bitmap) = viewModelScope.launch {
        val stringBitmap = bitmap.toBase64String()
        firebaseDb.setValue(stringBitmap)
    }

    private fun fetchChannelInformation() = viewModelScope.launch {
        val result = channelClient.watch().await()
        result.onSuccess {
            _isHost.value = it.hostName == chatClient.getCurrentUser()?.name
            if (isHost.value) {
                getRandomWords()
            } else {
                subscribeToFirebaseDb()
            }
        }
    }

    private fun subscribeToFirebaseDb() {
        firebaseDb.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val stringBitmap = snapshot.getValue(String::class.java)
                    _newDrawingImage.value = stringBitmap
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun subscribeToChannelEvents() {
        channelClient.subscribeFor<ChannelUpdatedByUserEvent> {
            val channel = it.channel
            _selectedWord.value = channel.selectedWord
        }
    }

    private fun subscribeToNewMessageEvent() {
        channelClient.subscribeFor<NewMessageEvent> {
            _gameChatMessages.value = _gameChatMessages.value + GameChatMessage(
                it.user.name,
                it.message.text
            )
            if (it.message.text.lowercase() == selectedWord.value?.lowercase()) {
                finishGame(it.user)
            }
        }
    }

    private fun finishGame(user: User) = viewModelScope.launch {
        channelClient.sendMessage(
            Message(
                text = "Congratulation! ${user.name} has correct the answer. \uD83C\uDF89",
                type = "system"
            )
        ).await()
    }

    private fun getRandomWords() = viewModelScope.launch {
        val randomWords = randomWord.getRandomWords()
        _randomWords.emit(randomWords)
    }


    @AssistedFactory
    interface GameAssistedFactory {
        fun create(cid: String): GameViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideGameAssistedFactory(
            assistedFactory: GameAssistedFactory,
            cid: String
        ): ViewModelProvider.NewInstanceFactory {
            return object : ViewModelProvider.NewInstanceFactory() {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return assistedFactory.create(cid) as T
                }
            }
        }
    }
}
