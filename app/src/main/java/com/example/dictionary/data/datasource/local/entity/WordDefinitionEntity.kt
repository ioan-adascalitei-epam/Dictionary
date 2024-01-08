package com.example.dictionary.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_definitions")
data class WordDefinitionEntity(
    @PrimaryKey
    val word: String,
    val origin: String?,
    val phonetic: String
)
