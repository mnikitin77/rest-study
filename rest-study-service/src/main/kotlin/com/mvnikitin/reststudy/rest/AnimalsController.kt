package com.mvnikitin.reststudy.rest

import com.mvnikitin.reststudy.api.AnimalsApi
import com.mvnikitin.reststudy.model.AnimalDto
import com.mvnikitin.reststudy.model.AnimalsListDto
import com.mvnikitin.reststudy.service.AnimalsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@RestController
class AnimalsController(
    private val animalsService: AnimalsService
): AnimalsApi {

    override fun getAnimals(xRequestID: UUID): ResponseEntity<AnimalsListDto> {
        return ResponseEntity.ok(animalsService.getAllAnimals(xRequestID.toString()))
    }

    override fun addAnimal(xRequestID: UUID, animalDto: AnimalDto): ResponseEntity<Unit> {
        val createdAnimal = animalsService.saveAnimal(xRequestID.toString(), animalDto)
        val uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdAnimal.id)
            .toUri()
        return ResponseEntity.created(uri).build()
    }

    override fun getAnimal(xRequestID: UUID, id: Long): ResponseEntity<AnimalDto> {
        return ResponseEntity.ok(animalsService.getAnimal(xRequestID.toString(), id))
    }

    override fun updateAnimal(xRequestID: UUID, animalDto: AnimalDto): ResponseEntity<Unit> {
        animalsService.saveAnimal(xRequestID.toString(), animalDto)
        return ResponseEntity.ok().build()
    }

    override fun deleteAnimal(xRequestID: UUID, id: Long): ResponseEntity<Unit> {
        animalsService.deleteAnimal(xRequestID.toString(), id)
        return ResponseEntity.ok().build()
    }
}