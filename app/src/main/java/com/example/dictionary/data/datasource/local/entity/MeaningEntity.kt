package com.example.dictionary.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["word", "partOfSpeech"],
    foreignKeys = [ForeignKey(
        entity = WordDefinitionEntity::class,
        parentColumns = ["word"],
        childColumns = ["word"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["word"], unique = true),
        Index(value = ["partOfSpeech"], unique = true)]
)
data class MeaningEntity(
    val word: String,
    val partOfSpeech: String
)