package com.example.dictionary.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.data.datasource.local.entity.MeaningEntity

@Dao
interface MeaningDao {

    @Query("SELECT * FROM meaningentity WHERE word = :word")
    suspend fun getWordMeanings(word: String): List<MeaningEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeaning(meaningEntity: MeaningEntity)

    @Query("DELETE FROM meaningentity WHERE word = :word")
    suspend fun deleteMeaning(word: String)
}