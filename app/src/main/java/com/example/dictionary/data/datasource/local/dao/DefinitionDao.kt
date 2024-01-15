package com.example.dictionary.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.data.datasource.local.entity.DefinitionEntity

@Dao
interface DefinitionDao {

    @Query("SELECT * FROM definitionentity WHERE word = :word")
    suspend fun getWordDefinition(word: String): List<DefinitionEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDefinition(definitionEntity: DefinitionEntity)

    @Query("DELETE FROM definitionentity WHERE word = :word")
    suspend fun deleteDefinition(word: String)
}