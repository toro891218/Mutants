package com.mercadolibre.mutants.model.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mercadolibre.mutants.model.MutantModel;

@SpringBootTest
class MutantModelTest {
	
	MutantModel mutantModel = new MutantModel();	
	
	@Test
	void mutantModelTest() {
		
		String id = "123456789";	
		List<String> 	adn = new ArrayList<String>();
		adn.add("ACTATA");
		adn.add("AGAGTT");
		adn.add("AGAGTT");
		adn.add("GACTGT");
		adn.add("GACTGT");
		adn.add("AATGGG");
		adn.add("CTTTTA");
		adn.add("TCACTA");
		Boolean isMutant = true;
		
		MutantModel mutantModel = new MutantModel(adn, isMutant);		

		mutantModel.setId(id);
		mutantModel.setAdn(adn);
		mutantModel.setIsMutant(isMutant);

		Assertions.assertEquals(id, mutantModel.getId());
		Assertions.assertEquals(adn, mutantModel.getAdn());
		Assertions.assertEquals(isMutant, mutantModel.getIsMutant());


	}

}
