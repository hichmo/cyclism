package com.hmo.cyclism.service

import com.hmo.cyclism.mapper.CyclistMapper
import com.hmo.cyclism.model.CyclistDto
import com.hmo.cyclism.repository.CyclistRepository
import org.mapstruct.factory.Mappers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class CyclistService(
        @Autowired
        private val repository: CyclistRepository
) {

    private val mapper = Mappers.getMapper(CyclistMapper::class.java)

    fun findAll(): List<CyclistDto> =
            repository.findAll().map(mapper::convertToDto)

    fun findById(id: String): CyclistDto = repository.findById(id)
            .map(mapper::convertToDto)
            .orElseThrow(({ ResponseStatusException(HttpStatus.NOT_FOUND, "No cyclist found with id $id") }))

    fun create(cyclistDto: CyclistDto): CyclistDto {
        val cyclistSaved = repository.save(mapper.convertToModel(cyclistDto))
        return mapper.convertToDto(cyclistSaved)
    }

    fun update(cyclistDto: CyclistDto, id: String): CyclistDto {
        repository.findById(id)
                .map { existingCyclist ->
                    existingCyclist.name = cyclistDto.name
                    existingCyclist.team = cyclistDto.team
                }
                .orElseThrow(({ ResponseStatusException(HttpStatus.NOT_FOUND, "No cyclist found with id $id") }))


        val cyclistSaved = repository.save(mapper.convertToModel(cyclistDto))
        return mapper.convertToDto(cyclistSaved)
    }

    fun deleteById(id: String) {
        try {
            repository.deleteById(id)
        } catch (ex: EmptyResultDataAccessException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No cyclist found to delete with id $id", ex)
        }

    }
}
