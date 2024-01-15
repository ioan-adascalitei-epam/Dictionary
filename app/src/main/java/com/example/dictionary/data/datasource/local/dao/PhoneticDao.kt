package com.example.dictionary.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.data.datasource.local.entity.PhoneticEntity

@Dao
interface PhoneticDao {

    @Query("SELECT * FROM phoneticentity WHERE word = :word")
    suspend fun getWordPhonetics(word: String): List<PhoneticEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhonetic(phoneticEntity: PhoneticEntity)

    @Query("DELETE FROM phoneticentity WHERE word = :word")
    suspend fun deletePhonetic(word: String)
}