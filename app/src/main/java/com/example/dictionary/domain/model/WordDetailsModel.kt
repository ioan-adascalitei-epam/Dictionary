package com.example.dictionary.domain.model

import com.example.dictionary.data.model.MeaningResponse

data class WordDetailsModel(
    val word: String,
    val meanings: List<MeaningResponse>,
    val origin: String?,
    val audioPath: String? = null
)
