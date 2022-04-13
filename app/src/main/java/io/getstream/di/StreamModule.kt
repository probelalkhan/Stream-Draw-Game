package io.getstream.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StreamModule {

    @Provides
    @Singleton
    fun provideChatClient() = ChatClient.instance()
}
