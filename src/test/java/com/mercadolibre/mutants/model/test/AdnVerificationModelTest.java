package com.mercadolibre.mutants.model.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mercadolibre.mutants.model.AdnVerificationModel;

@SpringBootTest
class AdnVerificationModelTest {

	AdnVerificationModel adnVerificationModel = new AdnVerificationModel();

	@Test
	void adnVerificationModelTest() {
		
		String id = "123456789";
		long countMutantDna = 1000000000;
		long countHumanDna = 8000000;
		double ratio =(countMutantDna/countHumanDna);
				
		AdnVerificationModel adnVerificationModel = new AdnVerificationModel(id, countMutantDna, countHumanDna, ratio);		
		
		adnVerificationModel.setId(id);
		adnVerificationModel.setCountHumanDna(countHumanDna);
		adnVerificationModel.setCountMutantDna(countMutantDna);
		adnVerificationModel.setRatio(ratio);
		
		Assertions.assertEquals(id, adnVerificationModel.getId());
		Assertions.assertEquals(countHumanDna, adnVerificationModel.getCountHumanDna());
		Assertions.assertEquals(countMutantDna, adnVerificationModel.getCountMutantDna());
		Assertions.assertEquals(ratio, adnVerificationModel.getRatio());		
		
	}

}
