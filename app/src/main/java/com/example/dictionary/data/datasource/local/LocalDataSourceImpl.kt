package com.example.dictionary.data.datasource.local

import android.util.Log
import com.example.dictionary.data.datasource.DictionaryDataSource
import com.example.dictionary.data.datasource.local.dao.WordsDao
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
class LocalDataSourceImpl @Inject constructor(private val db: WordsDatabase) :
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
        if(wordEntity.isEmpty()) {
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
            origin = wordEntity.first().origin,
            phonetic = wordEntity.first().phonetic,
            phonetics = phonetics.map { PhoneticResponse(it.audio, it.text) },
            word = word
        )
        return Result.Success(listOf(wordDefinitionsResponse))
    }

    override suspend fun saveWordDefinition(wordDefinition: WordDefinitionsResponse) {
        val wordEntity = WordDefinitionEntity(
            word = wordDefinition.word,
            origin = wordDefinition.origin,
            phonetic = wordDefinition.phonetic
        )
        val phoneticsEntity = wordDefinition.phonetics.map {
            PhoneticEntity(
                word = wordDefinition.word,
                audio = it.audio,
                text = it.text
            )
        }
        val meaningsEntity = wordDefinition.meanings.map {
            MeaningEntity(
                word = wordEntity.word,
                partOfSpeech = it.partOfSpeech
            )
        }
        val definitionsEntity = mutableListOf<DefinitionEntity>()
        wordDefinition.meanings.forEach { meaning ->
            Log.v("test_tag", meaning.toString())
            val definitions = meaning.definitions.map { def ->
                DefinitionEntity(
                    word = wordEntity.word,
                    partOfSpeech = meaning.partOfSpeech,
                    definition = def.definition,
                    antonyms = def.antonyms,
                    synonyms = def.synonyms,
                    example = def.example
                )
            }
            definitionsEntity.addAll(definitions)
        }

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