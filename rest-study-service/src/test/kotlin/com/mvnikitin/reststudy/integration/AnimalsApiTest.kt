package com.mvnikitin.reststudy.integration

import com.mvnikitin.reststudy.converter.toDto.toChickenDto
import com.mvnikitin.reststudy.converter.toDto.toCowDto
import com.mvnikitin.reststudy.converter.toDto.toGoatDto
import com.mvnikitin.reststudy.converter.toentity.toChickenEntity
import com.mvnikitin.reststudy.converter.toentity.toCowEntity
import com.mvnikitin.reststudy.converter.toentity.toEntity
import com.mvnikitin.reststudy.converter.toentity.toGoatEntity
import com.mvnikitin.reststudy.entity.Chicken
import com.mvnikitin.reststudy.entity.Cow
import com.mvnikitin.reststudy.model.AnimalDto
import com.mvnikitin.reststudy.model.AnimalsListDto
import com.mvnikitin.reststudy.utils.cock
import com.mvnikitin.reststudy.utils.cow
import com.mvnikitin.reststudy.utils.goat
import com.mvnikitin.reststudy.utils.headers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

class AnimalsApiTest(): ApiTestBase() {

    @Test
    fun `when POST request then a specific entity is stored in DB`() {
        val request = HttpEntity(cock, headers())
        val response = restTemplate.exchange(
            url(),
            HttpMethod.POST,
            request,
            AnimalDto::class.java,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)

        val animals = animalsRepository.findAll()
        val actual = animals.first { it is Chicken && it.name == cock.name }
        assertThat(actual).isNotNull
        assertThat(response.headers["Location"]?.get(0)).isEqualTo("${url()}/${actual.id}")
    }

    @Test
    fun `when GET all request then all available received`() {
        val one = cow.copy(type = "CowDto")
        val two = goat.copy(type = "GoatDto")
        val three = cock.copy(type = "ChickenDto")

        val savedCow = animalsRepository.save(one.toCowEntity()).toCowDto()
        val savedGoat = animalsRepository.save(two.toGoatEntity()) .toGoatDto()
        val savedCock = animalsRepository.save(three.toChickenEntity()).toChickenDto()
        val expected = mutableListOf(savedCow, savedGoat, savedCock)

        val response = restTemplate.exchange(
            url(),
            HttpMethod.GET,
            HttpEntity(null, headers()),
            AnimalsListDto::class.java,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        val actuals = response.body
        actuals!!.animals.map { it.toEntity() }
            .forEach { actual ->
                assertThat(expected
                    .filter { expItem ->
                        actual.id?.equals(expItem.id) ?: false &&
                        actual.type?.equals(expItem.type) ?: false
                    }
                    .count()
                ).isEqualTo(1)
            }
    }

    @Test
    fun `when GET request sent then correct object received`() {
        val animal = cow.copy(type = "CowDto")
        val expected = animalsRepository.save(animal.toCowEntity()).toCowDto()

        val response = restTemplate.exchange(
            "${url()}/${expected.id}",
            HttpMethod.GET,
            HttpEntity(null, headers()),
            AnimalDto::class.java,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        val actual = response.body

        // DTOs are generated as data classes
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when PUT request then a specific entity is updated in DB`() {
        val animal = cow.copy(type = "CowDto")
        val saved = animalsRepository.save(animal.toCowEntity())

        val expected = saved.toCowDto().copy(name = "Elizabeth", givesMilk = false)

        val request = HttpEntity(expected, headers())
        val response = restTemplate.exchange(
            url(),
            HttpMethod.PUT,
            request,
            Unit::class.java,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        val actual = (animalsRepository.findByIdOrNull(saved.id!!) as Cow).toCowDto()
        assertThat(actual).isNotNull
        // DTOs are generated as data classes
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `when DELETE request sent then correct object received`() {
        val expected = cow.copy(type = "CowDto")
        val saved = animalsRepository.save(expected.toCowEntity())

        val response = restTemplate.exchange(
            "${url()}/${saved.id}",
            HttpMethod.DELETE,
            HttpEntity(null, headers()),
            Unit::class.java,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        val actual = animalsRepository.findByIdOrNull(saved.id!!)
        assertThat(actual).isNull()
    }
}