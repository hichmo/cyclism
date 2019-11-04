package com.hmo.cyclism.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document("cyclists")
data class Cyclist(@Id var id: String? = null, var name: String, var team: String)




