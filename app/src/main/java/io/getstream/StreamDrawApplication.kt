package io.getstream

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.livedata.ChatDomain

@HiltAndroidApp
class StreamDrawApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val logLevel = if(BuildConfig.DEBUG) ChatLogLevel.ALL else ChatLogLevel.NOTHING
        val chatClient: ChatClient =
            ChatClient.Builder(getString(R.string.stream_api_key), this)
                .logLevel(logLevel)
                .build()

        ChatDomain.Builder(chatClient, this)
            .offlineEnabled()
            .build()
    }
}
