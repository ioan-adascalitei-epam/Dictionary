package com.example.dictionary.domain.model

import com.example.dictionary.data.model.Meaning

data class WordDetails(
    val word: String,
    val meanings: List<Meaning>,
    val origin: String?,
    val audioPath: String? = null
)
