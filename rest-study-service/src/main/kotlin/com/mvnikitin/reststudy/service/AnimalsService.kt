package com.mvnikitin.reststudy.service

import com.mvnikitin.reststudy.model.AnimalDto
import com.mvnikitin.reststudy.model.AnimalsListDto

interface AnimalsService {
    fun saveAnimal(requestId: String, animal: AnimalDto): AnimalDto
    fun getAnimal(requestId: String, id: Long): AnimalDto
    fun getAllAnimals(requestId: String): AnimalsListDto
    fun deleteAnimal(requestId: String, id: Long)
}