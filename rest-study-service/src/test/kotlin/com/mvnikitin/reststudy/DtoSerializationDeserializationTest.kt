package com.mvnikitin.reststudy

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.mvnikitin.reststudy.model.AnimalsListDto
import com.mvnikitin.reststudy.model.ChickenDto
import com.mvnikitin.reststudy.model.CowDto
import com.mvnikitin.reststudy.model.GoatDto
import com.mvnikitin.reststudy.utils.cock
import com.mvnikitin.reststudy.utils.cow
import com.mvnikitin.reststudy.utils.goat
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DtoSerializationDeserializationTest {

    private val objectMapper = ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .registerModules(
            JavaTimeModule(),
            KotlinModule.Builder().build()
        )
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    @Test
    @DisplayName("Generated DTO with discriminator serialization / deserialization test")
    fun `when serialized as parent it deserializes as child`() {

        val expectedAnimals = AnimalsListDto(listOf(cow, cock, goat))

        val json = objectMapper.writeValueAsString(expectedAnimals)
        val actualAnimals: AnimalsListDto = objectMapper.readValue(json)

        assertThat(actualAnimals.animals.first { it is CowDto }).isNotNull
        assertThat(actualAnimals.animals.first { it is ChickenDto }).isNotNull
        assertThat(actualAnimals.animals.first { it is GoatDto }).isNotNull
    }
}