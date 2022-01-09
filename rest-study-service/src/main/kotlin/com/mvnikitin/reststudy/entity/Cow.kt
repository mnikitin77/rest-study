package com.mvnikitin.reststudy.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Cow(
    @Column(name = "gives_milk")
    var givesMilk: Boolean
) : Animal()