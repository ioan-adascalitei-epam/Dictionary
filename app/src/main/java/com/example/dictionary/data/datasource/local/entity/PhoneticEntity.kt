package com.example.dictionary.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = WordDefinitionEntity::class,
        parentColumns = ["word"],
        childColumns = ["word"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["word"], unique = true)]
)
data class PhoneticEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val word: String,
    val audio: String?,
    val text: String
)