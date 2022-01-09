package com.mvnikitin.reststudy.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
class Animal {
    var name: String = ""
    var animalType: AnimalType = AnimalType.FISH
    var birthYear: Int? = null
    var type: String? = null

    @Id
    @GeneratedValue
    var id: Long? = null
}