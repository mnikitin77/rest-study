package com.mvnikitin.reststudy.dao

import com.mvnikitin.reststudy.entity.Animal
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface AnimalRepository: CrudRepository<Animal, Long>