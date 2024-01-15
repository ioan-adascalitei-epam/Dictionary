package com.example.dictionary.data.datasource.local

import android.util.Log
import com.example.dictionary.data.datasource.DictionaryDataSource
import com.example.dictionary.data.datasource.local.entity.DefinitionEntity
import com.example.dictionary.data.datasource.local.entity.MeaningEntity
import com.example.dictionary.data.datasource.local.entity.PhoneticEntity
import com.example.dictionary.data.datasource.local.entity.WordDefinitionEntity
import com.example.dictionary.data.model.DefinitionResponse
import com.example.dictionary.data.model.MeaningResponse
import com.example.dictionary.data.model.PhoneticResponse
import com.example.dictionary.data.model.WordDefinitionsResponse
import com.example.dictionary.di.LocalDS
import com.example.dictionary.domain.model.ErrorInfo
import com.example.dictionary.util.Result
import javax.inject.Inject

@LocalDS
class LocalDataSourceImpl @Inject constructor(
    private val db: WordsDatabase,
    private val entityMapper: EntityMapper
) :
    DictionaryDataSource {

    private val wordDao = db.wordsDao()
    private val meaningsDao = db.meaningDao()
    private val definitionDao = db.definitionDao()
    private val phoneticDao = db.phoneticsDao()

    override suspend fun getWordDefinitions(word: String): Result<List<WordDefinitionsResponse>> {
        val wordEntity = wordDao.getWord(word)
        val meanings = meaningsDao.getWordMeanings(word)
        val definitions = definitionDao.getWordDefinition(word)
        val phonetics = phoneticDao.getWordPhonetics(word)

        if (wordEntity.isEmpty()) {
            return Result.Error(ErrorInfo.NoInternet("No internet connection, no results found!"))
        }

        val wordDefinitionsResponse = WordDefinitionsResponse(
            meanings = meanings.map { meaning ->
                MeaningResponse(
                    partOfSpeech = meaning.partOfSpeech,
                    definitions = definitions.takeIf { it.equals(meaning.partOfSpeech) }
                        ?.map { def ->
                            DefinitionResponse(
                                def.antonyms,
                                def.definition,
                                def.example.orEmpty(),
                                def.synonyms
                            )
                        } ?: emptyList()
                )
            },
            origin = wordEntity.firstOrNull()?.origin,
            phonetic = wordEntity.firstOrNull()?.phonetic.orEmpty(),
            phonetics = phonetics.map { PhoneticResponse(it.audio, it.text) },
            word = word
        )
        return Result.Success(listOf(wordDefinitionsResponse))
    }

    override suspend fun saveWordDefinition(wordDefinition: WordDefinitionsResponse?) {
        if (wordDefinition == null) return

        val wordEntity = entityMapper.toWordEntity(wordDefinition)
        val phoneticsEntity = entityMapper.toPhoneticEntity(wordDefinition)
        val meaningsEntity = entityMapper.toMeaningEntity(wordDefinition)
        val definitionsEntity = entityMapper.toDefinitionEntity(wordDefinition)

        wordDao.insertWordDefinition(wordEntity)
        phoneticsEntity.forEach { phoneticDao.insertPhonetic(it) }
        meaningsEntity.forEach { meaningsDao.insertMeaning(it) }
        definitionsEntity.forEach { definitionDao.insertDefinition(it) }
    }

    override suspend fun deleteWordDefinitions(words: List<String>) {
        words.forEach { word ->
            wordDao.deleteWordDefinition(word)
            phoneticDao.deletePhonetic(word)
            definitionDao.deleteDefinition(word)
            meaningsDao.deleteMeaning(word)
        }
    }
}