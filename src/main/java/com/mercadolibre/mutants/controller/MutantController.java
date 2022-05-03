package com.mercadolibre.mutants.controller;


import java.util.Collection;
import java.util.Optional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.mutants.model.AdnVerificationModel;
import com.mercadolibre.mutants.model.MutantModel;
import com.mercadolibre.mutants.service.AdnVerificationService;
import com.mercadolibre.mutants.service.MutantService;

@RestController
@RequestMapping("/mercadolibre/challenge")
public class MutantController {

	private static final Logger logger = LoggerFactory.getLogger( MutantController.class);

	@Autowired
	MutantService mutantService;
	@Autowired
	AdnVerificationService adnVerificationService;


	@PostMapping("/mutants")
	@ResponseBody
	public ResponseEntity<Void> validateMutant(@RequestBody @Valid MutantModel mutantModel) {
			
		boolean isMutant = mutantService.validateMutant(mutantModel);
		
		if (isMutant) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	@GetMapping("/stats")
	@ResponseBody
	public Optional<AdnVerificationModel> getStats() {
		logger.info("Finding the mutant System state is =" + adnVerificationService.getAdnVerificationModel());
		return adnVerificationService.getAdnVerificationModel();
	}


	
	@PostMapping("/mutants/get")
	@ResponseBody
	public Optional<MutantModel> getMutant(@RequestBody MutantModel mutantModel) {
		logger.info("Finding the mutant by adn =" + mutantModel);			
		return mutantService.getMutantByAdn(mutantModel.getAdn());
				//.orElseThrow(() -> new MutantModelNotFoundException( id));
	}	
	
	
	@GetMapping("/mutants/all")
	@ResponseBody
	public Collection<MutantModel> allMutantModels() {		
		logger.info("Searching all mutants");		
		return mutantService.getMutantAll();
	}	
	
}
