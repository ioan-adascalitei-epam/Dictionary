package com.example.dictionary.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionary.data.datasource.local.dao.DefinitionDao
import com.example.dictionary.data.datasource.local.dao.MeaningDao
import com.example.dictionary.data.datasource.local.dao.PhoneticDao
import com.example.dictionary.data.datasource.local.dao.WordsDao
import com.example.dictionary.data.datasource.local.entity.DefinitionEntity
import com.example.dictionary.data.datasource.local.entity.MeaningEntity
import com.example.dictionary.data.datasource.local.entity.PhoneticEntity
import com.example.dictionary.data.datasource.local.entity.WordDefinitionEntity

@Database(
    entities = [WordDefinitionEntity::class, MeaningEntity::class, PhoneticEntity::class, DefinitionEntity::class],
    exportSchema = true,
    version = 2
)
@TypeConverters(Converters::class)
abstract class WordsDatabase : RoomDatabase() {

    abstract fun wordsDao(): WordsDao

    abstract fun definitionDao(): DefinitionDao

    abstract fun phoneticsDao(): PhoneticDao

    abstract fun meaningDao(): MeaningDao
}