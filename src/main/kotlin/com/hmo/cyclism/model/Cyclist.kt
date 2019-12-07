package com.hmo.cyclism.model

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*


@Table(name = "cyclists")
@Entity(name = "cyclist")
data class Cyclist(@Id
                   @GeneratedValue(generator = "UUID")
                   @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
                   var id: String?,
                   @Column var name: String?,
                   @Column var team: String?){

    constructor() : this(null, null, null)

}




