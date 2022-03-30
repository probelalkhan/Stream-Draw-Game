package io.getstream.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface RandomWordsApi {

    @GET("skribble_words.json")
    suspend fun getRandomWords(): List<String>


    companion object {
        private const val BASE_URL =
            "https://gist.githubusercontent.com/skydoves/b7a045f42e66a7a61fd850e566993c9d/raw/c671a08e5bad0296e30c182ace5113bf4f18bc71/"

        operator fun invoke(): RandomWordsApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(RandomWordsApi::class.java)
        }
    }
}
