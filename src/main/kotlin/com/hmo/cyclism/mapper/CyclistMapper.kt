package com.hmo.cyclism.mapper

import com.hmo.cyclism.model.Cyclist
import com.hmo.cyclism.model.CyclistDto
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper
interface CyclistMapper {

    fun convertToDto(cyclist: Cyclist): CyclistDto

    @InheritInverseConfiguration
    fun convertToModel(cyclistDto: CyclistDto): Cyclist
}

