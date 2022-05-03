package com.mercadolibre.mutants.service.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mercadolibre.mutants.model.AdnVerificationModel;
import com.mercadolibre.mutants.service.AdnVerificationService;

@SpringBootTest
class AdnVerificationServiceTest {

	@Autowired
	AdnVerificationService adnVerificationService;
	
	@Test
	void setRatioTest() {
		
		AdnVerificationModel adnVerificationModel = new AdnVerificationModel();
		
		long contHuman  = 1500;
		long contMutant = 1500;
		
		double ratio =   (double) (contMutant/contHuman);
				
		adnVerificationModel.setCountMutantDna(contMutant);
		adnVerificationModel.setCountHumanDna(contHuman);

		Assertions.assertEquals(ratio, adnVerificationService.setRatio(adnVerificationModel));
		
		
		/*
		 * División por cero
		 */
		
		adnVerificationModel = new AdnVerificationModel();
		
		contHuman  = 0;
		contMutant = 1500;
		
		if (contHuman == 0) {
			ratio = 1;
		} else {
			ratio =   (double) (contMutant/contHuman);
		}
		
		adnVerificationModel.setCountMutantDna(contMutant);
		adnVerificationModel.setCountHumanDna(contHuman);

		Assertions.assertEquals(ratio, adnVerificationService.setRatio(adnVerificationModel));		
		
	}
	
}
