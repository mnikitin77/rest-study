package com.mvnikitin.reststudy.converter.toDto

import com.mvnikitin.reststudy.entity.Animal
import com.mvnikitin.reststudy.entity.AnimalType
import com.mvnikitin.reststudy.entity.Chicken
import com.mvnikitin.reststudy.entity.Cow
import com.mvnikitin.reststudy.entity.Goat
import com.mvnikitin.reststudy.entity.Sheep
import com.mvnikitin.reststudy.model.AnimalTypeDto
import com.mvnikitin.reststudy.model.ChickenDto
import com.mvnikitin.reststudy.model.CowDto
import com.mvnikitin.reststudy.model.GoatDto
import com.mvnikitin.reststudy.model.SheepDto

fun Animal.toDto () = when(this) {
    is Chicken -> this.toChickenDto()
    is Cow -> this.toCowDto()
    is Goat -> this.toGoatDto()
    is Sheep -> this.toSheepDto()
    else -> throw IllegalArgumentException("Unsupported type provided as Animal")
}

fun Chicken.toChickenDto() = ChickenDto(
    givesEggs = this.givesEggs,
    name = this.name,
    animalType = this.animalType.toDto(),
    birthYear = this.birthYear,
    type = this.type,
    id = this.id,
)

fun Cow.toCowDto() = CowDto(
    givesMilk = this.givesMilk,
    name = this.name,
    animalType = this.animalType.toDto(),
    birthYear = this.birthYear,
    type = this.type,
    id = this.id,
)

fun Goat.toGoatDto() = GoatDto(
    givesMilk = this.givesMilk,
    name = this.name,
    animalType = this.animalType.toDto(),
    birthYear = this.birthYear,
    type = this.type,
    id = this.id,
)

fun Sheep.toSheepDto() = SheepDto(
    givesWool = this.givesWool,
    name = this.name,
    animalType = this.animalType.toDto(),
    birthYear = this.birthYear,
    type = this.type,
    id = this.id,
)

fun AnimalType.toDto() = AnimalTypeDto.valueOf(this.name)