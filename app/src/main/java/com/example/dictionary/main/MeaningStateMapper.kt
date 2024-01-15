package com.example.dictionary.main

import com.example.dictionary.data.model.MeaningResponse

object MeaningStateMapper {

    fun toMeaningState(meaningResponse: MeaningResponse) = MeaningState(
        meaningResponse.partOfSpeech,
        meaningResponse.definitions.map { def ->
            DefinitionState(
                antonyms = def.antonyms,
                synonyms = def.synonyms,
                definition = def.definition,
                example = def.example
            )
        })
}