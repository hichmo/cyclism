package com.hmo.cyclism.controller

import com.hmo.cyclism.model.CyclistDto
import com.hmo.cyclism.service.CyclistService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cyclists")
class CyclistController(@Autowired val service: CyclistService) {

    @GetMapping
    fun findAll(): List<CyclistDto> = service.findAll()

    @GetMapping("{id}")
    fun findById(@PathVariable id: String) = service.findById(id)

    @PostMapping
    fun create(@RequestBody cyclistDto: CyclistDto) = service.create(cyclistDto)

    @PutMapping("{id}")
    fun update(@PathVariable id: String, @RequestBody cyclistDto: CyclistDto) = service.update(cyclistDto, id)

    @DeleteMapping("{id}")
    fun deleteById(@PathVariable id: String) = service.deleteById(id)
}
