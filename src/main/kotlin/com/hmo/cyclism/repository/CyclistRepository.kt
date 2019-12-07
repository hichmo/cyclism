package com.hmo.cyclism.repository

import com.hmo.cyclism.model.Cyclist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CyclistRepository : CrudRepository<Cyclist, String> {

}