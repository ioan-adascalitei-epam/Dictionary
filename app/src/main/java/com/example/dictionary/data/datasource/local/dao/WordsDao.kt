package com.example.dictionary.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.data.datasource.local.entity.WordDefinitionEntity

@Dao
interface WordsDao {

    @Query("SELECT * FROM word_definitions WHERE word = :word")
    suspend fun getWord(word: String): List<WordDefinitionEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWordDefinition(wordDefinitionEntity: WordDefinitionEntity)

    @Query("DELETE FROM word_definitions WHERE word = :word")
    suspend fun deleteWordDefinition(word: String)

}