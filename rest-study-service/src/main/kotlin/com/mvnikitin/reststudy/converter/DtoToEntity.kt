package com.mvnikitin.reststudy.converter.toentity

import com.mvnikitin.reststudy.entity.Animal
import com.mvnikitin.reststudy.entity.AnimalType
import com.mvnikitin.reststudy.entity.Chicken
import com.mvnikitin.reststudy.entity.Cow
import com.mvnikitin.reststudy.entity.Goat
import com.mvnikitin.reststudy.entity.Sheep
import com.mvnikitin.reststudy.model.AnimalDto
import com.mvnikitin.reststudy.model.AnimalTypeDto
import com.mvnikitin.reststudy.model.ChickenDto
import com.mvnikitin.reststudy.model.CowDto
import com.mvnikitin.reststudy.model.GoatDto
import com.mvnikitin.reststudy.model.SheepDto

fun AnimalDto.toEntity() = when(this) {
    is ChickenDto -> this.toChickenEntity()
    is CowDto -> this.toCowEntity()
    is GoatDto -> this.toGoatEntity()
    is SheepDto -> this.toSheepEntity()
    else -> throw IllegalArgumentException("Unsupported type provided as AnimalDto")
}

fun ChickenDto.toChickenEntity() =
    Chicken(givesEggs = this.givesEggs)
        .setBaseProperties(this)

fun CowDto.toCowEntity() =
    Cow(givesMilk = this.givesMilk)
        .setBaseProperties(this)

fun GoatDto.toGoatEntity() =
    Goat(givesMilk = this.givesMilk)
        .setBaseProperties(this)

fun SheepDto.toSheepEntity() =
    Sheep(givesWool = this.givesWool)
        .setBaseProperties(this)

fun AnimalTypeDto.toEntity() = AnimalType.valueOf(this.name)

fun <T : Animal> T.setBaseProperties(baseDto: AnimalDto) =
    this.apply {
        name = baseDto.name
        animalType = baseDto.animalType.toEntity()
        birthYear = baseDto.birthYear
        type = baseDto.type
        id = baseDto.id
    }