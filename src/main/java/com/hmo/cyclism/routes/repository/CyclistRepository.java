package com.hmo.cyclism.routes.repository;

import com.hmo.cyclism.routes.model.Cyclist;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CyclistRepository extends ReactiveMongoRepository<Cyclist, String> {


}
