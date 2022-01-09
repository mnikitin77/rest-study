package com.mvnikitin.reststudy.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Chicken(
    @Column(name = "gives_eggs")
    var givesEggs: Boolean
) : Animal()
