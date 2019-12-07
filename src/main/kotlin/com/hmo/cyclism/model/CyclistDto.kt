package com.hmo.cyclism.model

data class CyclistDto(var id: String?, var name: String?, var team: String?) {

    constructor() : this(null, null, null)
}