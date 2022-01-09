package com.mvnikitin.reststudy.service

import com.mvnikitin.reststudy.converter.toDto.toDto
import com.mvnikitin.reststudy.converter.toentity.toEntity
import com.mvnikitin.reststudy.dao.AnimalRepository
import com.mvnikitin.reststudy.exception.ResourceNotFoundException
import com.mvnikitin.reststudy.model.AnimalDto
import com.mvnikitin.reststudy.model.AnimalsListDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AnimalsServiceImlp(
    private val animalRepository: AnimalRepository
): AnimalsService {

    override fun saveAnimal(requestId: String, animal: AnimalDto) =
        animalRepository.save(animal.toEntity()).toDto()

    override fun getAnimal(requestId: String, id: Long) =
        animalRepository.findByIdOrNull(id)?.toDto()
            ?: throw ResourceNotFoundException("Animal with id=$id not found")

    override fun getAllAnimals(requestId: String) =
        AnimalsListDto(animalRepository.findAll().map { it.toDto() })

    override fun deleteAnimal(requestId: String, id: Long) =
        try {
            animalRepository.deleteById(id)
        } catch (ex: Throwable) {
            throw ResourceNotFoundException(
                ex.message ?: "Resource with Id=$id is not found.")
        }
}