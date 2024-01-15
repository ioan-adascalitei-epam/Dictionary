package com.example.dictionary.data.datasource.remote.api

import com.example.dictionary.data.model.WordDefinitionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("v2/entries/en/{word}")
    suspend fun getWordDefinitions(@Path("word") word: String): List<WordDefinitionsResponse>

}