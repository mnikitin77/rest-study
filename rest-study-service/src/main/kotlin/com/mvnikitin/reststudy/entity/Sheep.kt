package com.mvnikitin.reststudy.entity

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Sheep(
    @Column(name = "gives_wool")
    var givesWool: Boolean
) : Animal()