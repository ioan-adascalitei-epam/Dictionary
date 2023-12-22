package com.example.dictionary.data.api

import com.example.dictionary.data.model.WordDefinitionsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("{word}")
    suspend fun getWordDefinitions(@Path("word") word: String): List<WordDefinitionsDto>

}