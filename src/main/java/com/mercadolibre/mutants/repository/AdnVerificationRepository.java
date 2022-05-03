package com.mercadolibre.mutants.repository;


import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mercadolibre.mutants.model.AdnVerificationModel;


@EnableScan
@Repository
public interface AdnVerificationRepository extends CrudRepository<AdnVerificationModel, String>{
	
	Optional<AdnVerificationModel> findById(String id);

}
