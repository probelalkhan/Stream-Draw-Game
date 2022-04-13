package io.getstream.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.data.AppPreferences
import io.getstream.data.RandomWordsApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppPreferences(@ApplicationContext context: Context) = AppPreferences(context)

    @Provides
    @Singleton
    fun provideRandomWordsApi(): RandomWordsApi = RandomWordsApi.invoke()

}
