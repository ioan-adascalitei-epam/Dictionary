package com.example.dictionary.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


@Entity(
    primaryKeys = ["word", "partOfSpeech", "definition"],
    foreignKeys = [
        ForeignKey(
            entity = MeaningEntity::class,
            parentColumns = ["word"],
            childColumns = ["word"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = MeaningEntity::class,
            parentColumns = ["partOfSpeech"],
            childColumns = ["partOfSpeech"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [
        Index(value = ["word"], unique = true),
        Index(value = ["partOfSpeech"], unique = true)]
)
data class DefinitionEntity(
    val word: String,
    val partOfSpeech: String,
    val antonyms: List<String>?,
    val definition: String,
    val example: String?,
    val synonyms: List<String>?
)