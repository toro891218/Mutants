package com.mercadolibre.mutants.repository;

import java.util.List;
import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mercadolibre.mutants.model.MutantModel;

@EnableScan
@Repository
public interface MutantRepository extends CrudRepository<MutantModel, String>{
	Optional<MutantModel> findByAdn(List<String> adn);
}
