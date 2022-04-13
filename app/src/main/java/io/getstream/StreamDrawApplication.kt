package io.getstream

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory

@HiltAndroidApp
class StreamDrawApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(
                backgroundSyncEnabled = true,
                userPresence = true,
                persistenceEnabled = true,
                uploadAttachmentsNetworkType = UploadAttachmentsNetworkType.UNMETERED
            ),
            appContext = this
        )

        val logLevel = if(BuildConfig.DEBUG) ChatLogLevel.ALL else ChatLogLevel.NOTHING
        val chatClient: ChatClient =
            ChatClient.Builder(getString(R.string.stream_api_key), this)
                .withPlugin(offlinePluginFactory)
                .logLevel(logLevel)
                .build()
    }
}
