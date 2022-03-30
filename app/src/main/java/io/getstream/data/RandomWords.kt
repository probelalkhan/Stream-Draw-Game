package io.getstream.data

import javax.inject.Inject

class RandomWords @Inject constructor(
    private val randomWordsApi: RandomWordsApi
) {

    suspend fun getRandomWords(): List<String>{
        val words = randomWordsApi.getRandomWords()
        return words.asSequence().shuffled().take(3).toList()
    }

}
