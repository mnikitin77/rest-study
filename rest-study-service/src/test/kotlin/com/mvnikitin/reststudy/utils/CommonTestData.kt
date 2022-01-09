package com.mvnikitin.reststudy.utils

import com.mvnikitin.reststudy.model.AnimalTypeDto
import com.mvnikitin.reststudy.model.ChickenDto
import com.mvnikitin.reststudy.model.CowDto
import com.mvnikitin.reststudy.model.GoatDto
import com.mvnikitin.reststudy.model.SheepDto

val cock = ChickenDto(
    name = "John",
    animalType = AnimalTypeDto.POULTRY,
    givesEggs = false
)

val cow = CowDto(
    name = "Zorka",
    animalType = AnimalTypeDto.CATTLE,
    givesMilk = true
)

val goat = GoatDto(
    name = "Theodor",
    animalType = AnimalTypeDto.CATTLE,
    givesMilk = false
)

val sheep = SheepDto(
    name = "Dolly",
    animalType = AnimalTypeDto.CATTLE,
    givesWool = true
)