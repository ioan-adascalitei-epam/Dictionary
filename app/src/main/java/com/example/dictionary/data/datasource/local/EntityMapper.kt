package com.example.dictionary.data.datasource.local

import android.util.Log
import com.example.dictionary.data.datasource.local.entity.DefinitionEntity
import com.example.dictionary.data.datasource.local.entity.MeaningEntity
import com.example.dictionary.data.datasource.local.entity.PhoneticEntity
import com.example.dictionary.data.datasource.local.entity.WordDefinitionEntity
import com.example.dictionary.data.model.WordDefinitionsResponse

object EntityMapper {

    fun toWordEntity(entity: WordDefinitionsResponse): WordDefinitionEntity =
        WordDefinitionEntity(
            word = entity.word,
            origin = entity.origin,
            phonetic = entity.phonetic
        )

    fun toPhoneticEntity(entity: WordDefinitionsResponse): List<PhoneticEntity> =
        entity.phonetics.map {
            PhoneticEntity(
                word = entity.word,
                audio = it.audio,
                text = it.text
            )
        }

    fun toMeaningEntity(entity: WordDefinitionsResponse): List<MeaningEntity> =
        entity.meanings.map {
            MeaningEntity(
                word = entity.word,
                partOfSpeech = it.partOfSpeech
            )
        }

    fun toDefinitionEntity(entity: WordDefinitionsResponse): List<DefinitionEntity> {
        val definitionsEntity = mutableListOf<DefinitionEntity>()

        entity.meanings.forEach { meaning ->
            Log.v("test_tag", meaning.toString())
            val definitions = meaning.definitions.map { def ->
                DefinitionEntity(
                    word = entity.word,
                    partOfSpeech = meaning.partOfSpeech,
                    definition = def.definition,
                    antonyms = def.antonyms,
                    synonyms = def.synonyms,
                    example = def.example
                )
            }
            definitionsEntity.addAll(definitions)
        }

        return definitionsEntity
    }
}