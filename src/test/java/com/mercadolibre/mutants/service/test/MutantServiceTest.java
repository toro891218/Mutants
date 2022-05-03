package com.mercadolibre.mutants.service.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mercadolibre.mutants.config.PropertiesConfig;
import com.mercadolibre.mutants.exceptions.AdnException;
import com.mercadolibre.mutants.model.AdnVerificationModel;
import com.mercadolibre.mutants.model.MutantModel;
import com.mercadolibre.mutants.repository.AdnVerificationRepository;
import com.mercadolibre.mutants.repository.MutantRepository;
import com.mercadolibre.mutants.service.AdnVerificationService;
import com.mercadolibre.mutants.service.MutantService;

@SpringBootTest
class MutantServiceTest {

	@MockBean	
	MutantRepository mutantRepository;	

	@MockBean
	AdnVerificationRepository adnVerificationRepository;	

	@Autowired
	MutantService mutantService;

	@Autowired
	AdnVerificationService adnVerificationService;

	@Autowired
	PropertiesConfig propertiesConfig;	
	
	@Autowired
	private WebApplicationContext appContext;
	
	private MockMvc mockMvc;	
		
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.appContext).build();
	}	

	@Test
	void validateMutantTestNone() {

		/**
		 * 403 : Ninguna *		 
			"adn": ["ACTATA", "AGAGTT", "GTCTGT", "AATGGG", "CCTTTA", "TCACGA"]
		 */
		List<String> 	adn = new ArrayList<String>();
		adn.add("ACTATA");
		adn.add("AGAGTT");
		adn.add("GTCTGT");
		adn.add("AATGGG");
		adn.add("CCTTTA");
		adn.add("TCACGA");

		MutantModel mutantModel = new MutantModel();
		mutantModel.setAdn(adn);
		mutantModel.setIsMutant(false);

		when(mutantRepository.save(mutantModel)).thenReturn(mutantModel);

		String idMutantVerification = PropertiesConfig.getMutantsVerificationId();
		long countMutantDna = 0;
		long countHumanDna = 0;
		double ratio;				
		if (countHumanDna == 0) {
			ratio = 1;
		} else {
			ratio =   (double) (countMutantDna/countHumanDna);
		}		

		AdnVerificationModel adnVerificationModel = new AdnVerificationModel(idMutantVerification, countMutantDna, countHumanDna, ratio);

		Optional<AdnVerificationModel> AdnVerificationOpt = Optional.of(adnVerificationModel);
		when(adnVerificationRepository.findById(adnVerificationModel.getId())).thenReturn(AdnVerificationOpt);
		when(adnVerificationRepository.save(adnVerificationModel)).thenReturn(adnVerificationModel);

		boolean result = mutantService.validateMutant(mutantModel);

		if (result) {
			adnVerificationModel.setCountMutantDna(countMutantDna+1);
		}else {
			adnVerificationModel.setCountHumanDna(countHumanDna+1);
		}

		Assertions.assertEquals(false, result);
		Assertions.assertEquals(1, adnVerificationModel.getCountHumanDna());
		Assertions.assertEquals(0, adnVerificationModel.getCountMutantDna());

	}

	@Test
	void validateMutantTestDiagonalLeftOne() {
		/**
		 * 403 : Diagonal Izquierda 1 *
				"adn": ["ACTATA", "AGAGTT", "GACTGT", "AATGGG", "CCTTTA", "TCACTA"]
		 * 
		 */
		List<String> 	adn = new ArrayList<String>();			
		adn.add("ACTATA");
		adn.add("AGAGTT");
		adn.add("GACTGT");
		adn.add("AATGGG");
		adn.add("CCTTTA");
		adn.add("TCACTA");

		MutantModel mutantModel = new MutantModel();
		mutantModel.setAdn(adn);
		mutantModel.setIsMutant(false);

		when(mutantRepository.save(mutantModel)).thenReturn(mutantModel);

		String idMutantVerification = PropertiesConfig.getMutantsVerificationId();
		long countMutantDna = 0;
		long countHumanDna = 0;
		double ratio;				
		if (countHumanDna == 0) {
			ratio = 1;
		} else {
			ratio =   (double) (countMutantDna/countHumanDna);
		}		

		AdnVerificationModel adnVerificationModel = new AdnVerificationModel(idMutantVerification, countMutantDna, countHumanDna, ratio);

		Optional<AdnVerificationModel> AdnVerificationOpt = Optional.of(adnVerificationModel);
		when(adnVerificationRepository.findById(adnVerificationModel.getId())).thenReturn(AdnVerificationOpt);
		when(adnVerificationRepository.save(adnVerificationModel)).thenReturn(adnVerificationModel);		

		boolean result = mutantService.validateMutant(mutantModel);

		if (result) {
			adnVerificationModel.setCountMutantDna(countMutantDna+1);
		}else {
			adnVerificationModel.setCountHumanDna(countHumanDna+1);
		}

		Assertions.assertEquals(false, result);
		Assertions.assertEquals(1, adnVerificationModel.getCountHumanDna());
		Assertions.assertEquals(0, adnVerificationModel.getCountMutantDna());

	}

	@Test
	void validateMutantTestDiagonalLeftTwo() {
		/**
		200 OK: Diagonal Izquierda 2 * 
		{
		"adn": ["ACTATA", "AGAGTT", "GACTGT", "AATGGG", "CTTTTA", "TCACTA"]
		}
		 * 
		 */
		List<String> 	adn = new ArrayList<String>();			
		adn.add("ACTATA");
		adn.add("AGAGTT");
		adn.add("GACTGT");
		adn.add("AATGGG");
		adn.add("CTTTTA");
		adn.add("TCACTA");

		MutantModel mutantModel = new MutantModel();
		mutantModel.setAdn(adn);
		mutantModel.setIsMutant(false);

		when(mutantRepository.save(mutantModel)).thenReturn(mutantModel);

		String idMutantVerification = PropertiesConfig.getMutantsVerificationId();
		long countMutantDna = 0;
		long countHumanDna = 0;
		double ratio;				
		if (countHumanDna == 0) {
			ratio = 1;
		} else {
			ratio =   (double) (countMutantDna/countHumanDna);
		}		

		AdnVerificationModel adnVerificationModel = new AdnVerificationModel(idMutantVerification, countMutantDna, countHumanDna, ratio);

		Optional<AdnVerificationModel> AdnVerificationOpt = Optional.of(adnVerificationModel);
		when(adnVerificationRepository.findById(adnVerificationModel.getId())).thenReturn(AdnVerificationOpt);
		when(adnVerificationRepository.save(adnVerificationModel)).thenReturn(adnVerificationModel);		

		boolean result = mutantService.validateMutant(mutantModel);

		if (result) {
			adnVerificationModel.setCountMutantDna(countMutantDna+1);
		}else {
			adnVerificationModel.setCountHumanDna(countHumanDna+1);
		}

		Assertions.assertEquals(true, result);
		Assertions.assertEquals(0, adnVerificationModel.getCountHumanDna());
		Assertions.assertEquals(1, adnVerificationModel.getCountMutantDna());

	}	

	@Test
	void validateMutantTestDiagonalRightTwo() {
		/**
		200 OK: Diagonal derecho 2 *
		{
		"adn": ["ACTATA", "AGAGTT", "GTATGT", "AGTAGG", "CCGTAA", "TCAGTA"]
		}
		 * 
		 */
		List<String> 	adn = new ArrayList<String>();			
		adn.add("ACTATA");
		adn.add("AGAGTT");
		adn.add("GTATGT");
		adn.add("AGTAGG");
		adn.add("CCGTAA");
		adn.add("TCAGTA");	

		MutantModel mutantModel = new MutantModel();
		mutantModel.setAdn(adn);
		mutantModel.setIsMutant(false);

		when(mutantRepository.save(mutantModel)).thenReturn(mutantModel);

		String idMutantVerification = PropertiesConfig.getMutantsVerificationId();
		long countMutantDna = 0;
		long countHumanDna = 0;
		double ratio;				
		if (countHumanDna == 0) {
			ratio = 1;
		} else {
			ratio =   (double) (countMutantDna/countHumanDna);
		}		

		AdnVerificationModel adnVerificationModel = new AdnVerificationModel(idMutantVerification, countMutantDna, countHumanDna, ratio);

		Optional<AdnVerificationModel> AdnVerificationOpt = Optional.of(adnVerificationModel);
		when(adnVerificationRepository.findById(adnVerificationModel.getId())).thenReturn(AdnVerificationOpt);
		when(adnVerificationRepository.save(adnVerificationModel)).thenReturn(adnVerificationModel);		

		boolean result = mutantService.validateMutant(mutantModel);

		if (result) {
			adnVerificationModel.setCountMutantDna(countMutantDna+1);
		}else {
			adnVerificationModel.setCountHumanDna(countHumanDna+1);
		}

		Assertions.assertEquals(true, result);
		Assertions.assertEquals(0, adnVerificationModel.getCountHumanDna());
		Assertions.assertEquals(1, adnVerificationModel.getCountMutantDna());

	}		

	@Test
	void validateMutantTestHorizontalTwo() {
		/**
		200 : Horizontal 2
		{
		"adn": ["ACTATA", "AGGGGT", "GTCTGT", "AATGGG", "CCTTTT", "TCACGA"]
		}
		 * 
		 */
		List<String> 	adn = new ArrayList<String>();			
		adn.add("ACTATA");
		adn.add("AGGGGT");
		adn.add("GTCTGT");
		adn.add("AATGGG");
		adn.add("CCTTTT");
		adn.add("TCACGA");		

		MutantModel mutantModel = new MutantModel();
		mutantModel.setAdn(adn);
		mutantModel.setIsMutant(false);

		when(mutantRepository.save(mutantModel)).thenReturn(mutantModel);

		String idMutantVerification = PropertiesConfig.getMutantsVerificationId();
		long countMutantDna = 0;
		long countHumanDna = 0;
		double ratio;				
		if (countHumanDna == 0) {
			ratio = 1;
		} else {
			ratio =   (double) (countMutantDna/countHumanDna);
		}		

		AdnVerificationModel adnVerificationModel = new AdnVerificationModel(idMutantVerification, countMutantDna, countHumanDna, ratio);

		Optional<AdnVerificationModel> AdnVerificationOpt = Optional.of(adnVerificationModel);
		when(adnVerificationRepository.findById(adnVerificationModel.getId())).thenReturn(AdnVerificationOpt);
		when(adnVerificationRepository.save(adnVerificationModel)).thenReturn(adnVerificationModel);		

		boolean result = mutantService.validateMutant(mutantModel);

		if (result) {
			adnVerificationModel.setCountMutantDna(countMutantDna+1);
		}else {
			adnVerificationModel.setCountHumanDna(countHumanDna+1);
		}

		Assertions.assertEquals(true, result);
		Assertions.assertEquals(0, adnVerificationModel.getCountHumanDna());
		Assertions.assertEquals(1, adnVerificationModel.getCountMutantDna());

	}		

	@Test
	void validateMutantTestVerticalTwo() {
		/**
		200 : Vertical 2
		{
		"adn": ["ACTATT", "AGAGTT", "GTCTGT", "AACGGT", "CCCTTA", "TCCCGA"]
		}
		 * 
		 */
		List<String> 	adn = new ArrayList<String>();			
		adn.add("ACTATT");
		adn.add("AGAGTT");
		adn.add("GTCTGT");
		adn.add("AACGGT");
		adn.add("CCCTTA");
		adn.add("TCCCGA");

		MutantModel mutantModel = new MutantModel();
		mutantModel.setAdn(adn);
		mutantModel.setIsMutant(false);

		when(mutantRepository.save(mutantModel)).thenReturn(mutantModel);

		String idMutantVerification = PropertiesConfig.getMutantsVerificationId();
		long countMutantDna = 0;
		long countHumanDna = 0;
		double ratio;				
		if (countHumanDna == 0) {
			ratio = 1;
		} else {
			ratio =   (double) (countMutantDna/countHumanDna);
		}		

		AdnVerificationModel adnVerificationModel = new AdnVerificationModel(idMutantVerification, countMutantDna, countHumanDna, ratio);

		Optional<AdnVerificationModel> AdnVerificationOpt = Optional.of(adnVerificationModel);
		when(adnVerificationRepository.findById(adnVerificationModel.getId())).thenReturn(AdnVerificationOpt);
		when(adnVerificationRepository.save(adnVerificationModel)).thenReturn(adnVerificationModel);		

		boolean result = mutantService.validateMutant(mutantModel);

		if (result) {
			adnVerificationModel.setCountMutantDna(countMutantDna+1);
		}else {
			adnVerificationModel.setCountHumanDna(countHumanDna+1);
		}

		Assertions.assertEquals(true, result);
		Assertions.assertEquals(0, adnVerificationModel.getCountHumanDna());
		Assertions.assertEquals(1, adnVerificationModel.getCountMutantDna());

	}		

	@Test
	void validateMutantTestHoriVert() {
		/**
		200 : Horizontal 1 + Vertical 1
		{
		 "adn": ["TTCCCC", "AGTGTT", "TATAGT", "AATGGG", "CCTTTA", "TCACTA"]
		}
		 * 
		 */
		List<String> 	adn = new ArrayList<String>();			
		adn.add("TTCCCC");
		adn.add("AGTGTT");
		adn.add("TATAGT");
		adn.add("AATGGG");
		adn.add("CCTTTA");
		adn.add("TCACTA");

		MutantModel mutantModel = new MutantModel();
		mutantModel.setAdn(adn);
		mutantModel.setIsMutant(false);

		when(mutantRepository.save(mutantModel)).thenReturn(mutantModel);

		String idMutantVerification = PropertiesConfig.getMutantsVerificationId();
		long countMutantDna = 0;
		long countHumanDna = 0;
		double ratio;				
		if (countHumanDna == 0) {
			ratio = 1;
		} else {
			ratio =   (double) (countMutantDna/countHumanDna);
		}		

		AdnVerificationModel adnVerificationModel = new AdnVerificationModel(idMutantVerification, countMutantDna, countHumanDna, ratio);

		Optional<AdnVerificationModel> AdnVerificationOpt = Optional.of(adnVerificationModel);
		when(adnVerificationRepository.findById(adnVerificationModel.getId())).thenReturn(AdnVerificationOpt);
		when(adnVerificationRepository.save(adnVerificationModel)).thenReturn(adnVerificationModel);		

		boolean result = mutantService.validateMutant(mutantModel);

		if (result) {
			adnVerificationModel.setCountMutantDna(countMutantDna+1);
		}else {
			adnVerificationModel.setCountHumanDna(countHumanDna+1);
		}

		Assertions.assertEquals(true, result);
		Assertions.assertEquals(0, adnVerificationModel.getCountHumanDna());
		Assertions.assertEquals(1, adnVerificationModel.getCountMutantDna());

	}		

	@Test
	void validateMutantTestHoriVertDiag() {
		/**
		200 : Horizontal 1 + Vertical 1 + Diagonal
		{
		 "adn": ["ACTATA", "AGAGTT", "TAAAGT", "AATGGG", "CCTTTA", "TCACTA"]
		}
		 * 
		 */
		List<String> 	adn = new ArrayList<String>();			
		adn.add("TTCCCC");
		adn.add("AGTGTT");
		adn.add("TATTGT");
		adn.add("AATGTG");
		adn.add("CCTTTA");
		adn.add("TCACTA");	

		MutantModel mutantModel = new MutantModel();
		mutantModel.setAdn(adn);
		mutantModel.setIsMutant(false);

		when(mutantRepository.save(mutantModel)).thenReturn(mutantModel);

		String idMutantVerification = PropertiesConfig.getMutantsVerificationId();
		long countMutantDna = 0;
		long countHumanDna = 0;
		double ratio;				
		if (countHumanDna == 0) {
			ratio = 1;
		} else {
			ratio =   (double) (countMutantDna/countHumanDna);
		}		

		AdnVerificationModel adnVerificationModel = new AdnVerificationModel(idMutantVerification, countMutantDna, countHumanDna, ratio);

		Optional<AdnVerificationModel> AdnVerificationOpt = Optional.of(adnVerificationModel);
		when(adnVerificationRepository.findById(adnVerificationModel.getId())).thenReturn(AdnVerificationOpt);
		when(adnVerificationRepository.save(adnVerificationModel)).thenReturn(adnVerificationModel);		

		boolean result = mutantService.validateMutant(mutantModel);

		if (result) {
			adnVerificationModel.setCountMutantDna(countMutantDna+1);
		}else {
			adnVerificationModel.setCountHumanDna(countHumanDna+1);
		}

		Assertions.assertEquals(true, result);
		Assertions.assertEquals(0, adnVerificationModel.getCountHumanDna());
		Assertions.assertEquals(1, adnVerificationModel.getCountMutantDna());

	}		

	@Test
	void validateMutantTestDiagonalRightOne() {
		/**
		403: Diagonal derecho 1 *
		{
		"adn": ["ACTATA", "ACAGTT", "GTCTGT", "AATCGG", "CCTTTC", "TCACTA"]
		}
	 * 
	 */
		List<String> 	adn = new ArrayList<String>();			
		adn.add("ACTATA");
		adn.add("ACAGTT");
		adn.add("GTCTGT");
		adn.add("AATCGG");
		adn.add("CCTTTC");
		adn.add("TCACTA");

		MutantModel mutantModel = new MutantModel();
		mutantModel.setAdn(adn);
		mutantModel.setIsMutant(false);

		when(mutantRepository.save(mutantModel)).thenReturn(mutantModel);

		String idMutantVerification = PropertiesConfig.getMutantsVerificationId();
		long countMutantDna = 0;
		long countHumanDna = 0;
		double ratio;				
		if (countHumanDna == 0) {
			ratio = 1;
		} else {
			ratio =   (double) (countMutantDna/countHumanDna);
		}		

		AdnVerificationModel adnVerificationModel = new AdnVerificationModel(idMutantVerification, countMutantDna, countHumanDna, ratio);

		Optional<AdnVerificationModel> AdnVerificationOpt = Optional.of(adnVerificationModel);
		when(adnVerificationRepository.findById(adnVerificationModel.getId())).thenReturn(AdnVerificationOpt);
		when(adnVerificationRepository.save(adnVerificationModel)).thenReturn(adnVerificationModel);		

		boolean result = mutantService.validateMutant(mutantModel);

		if (result) {
			adnVerificationModel.setCountMutantDna(countMutantDna+1);
		}else {
			adnVerificationModel.setCountHumanDna(countHumanDna+1);
		}

		Assertions.assertEquals(false, result);
		Assertions.assertEquals(1, adnVerificationModel.getCountHumanDna());
		Assertions.assertEquals(0, adnVerificationModel.getCountMutantDna());

	}

	@Test
	void validateMutantTestHorizontalOne() {
		/**
		403 : Horizontal 1
		{
		"adn": ["ACTATA", "AGAGTT", "AAAAGT", "TATGGG", "CCTTTA", "TCACTA"]
		}
		 * 
		 */
		List<String> 	adn = new ArrayList<String>();			
		adn.add("ACTATA");
		adn.add("AGAGTT");
		adn.add("AAAAGT");
		adn.add("TATGGG");
		adn.add("CCTTTA");
		adn.add("TCACTA");

		MutantModel mutantModel = new MutantModel();
		mutantModel.setAdn(adn);
		mutantModel.setIsMutant(false);

		when(mutantRepository.save(mutantModel)).thenReturn(mutantModel);

		String idMutantVerification = PropertiesConfig.getMutantsVerificationId();
		long countMutantDna = 0;
		long countHumanDna = 0;
		double ratio;				
		if (countHumanDna == 0) {
			ratio = 1;
		} else {
			ratio =   (double) (countMutantDna/countHumanDna);
		}		

		AdnVerificationModel adnVerificationModel = new AdnVerificationModel(idMutantVerification, countMutantDna, countHumanDna, ratio);

		Optional<AdnVerificationModel> AdnVerificationOpt = Optional.of(adnVerificationModel);
		when(adnVerificationRepository.findById(adnVerificationModel.getId())).thenReturn(AdnVerificationOpt);
		when(adnVerificationRepository.save(adnVerificationModel)).thenReturn(adnVerificationModel);		

		boolean result = mutantService.validateMutant(mutantModel);

		if (result) {
			adnVerificationModel.setCountMutantDna(countMutantDna+1);
		}else {
			adnVerificationModel.setCountHumanDna(countHumanDna+1);
		}

		Assertions.assertEquals(false, result);
		Assertions.assertEquals(1, adnVerificationModel.getCountHumanDna());
		Assertions.assertEquals(0, adnVerificationModel.getCountMutantDna());

	}
	
	@Test
	void validateMutantTestVOne() {
		/**
		403 : Vertical 1
		{
		"adn": ["ACTATA", "AGCGTT", "GTCTGT", "AACGGG", "CCCTTA", "TCACGA"]
		}
		 * 
		 */
		List<String> 	adn = new ArrayList<String>();			
		adn.add("ACTATA");
		adn.add("AGCGTT");
		adn.add("GTCTGT");
		adn.add("AACGGG");
		adn.add("CCCTTA");
		adn.add("TCACGA");

		MutantModel mutantModel = new MutantModel();
		mutantModel.setAdn(adn);
		mutantModel.setIsMutant(false);

		when(mutantRepository.save(mutantModel)).thenReturn(mutantModel);

		String idMutantVerification = PropertiesConfig.getMutantsVerificationId();
		long countMutantDna = 0;
		long countHumanDna = 0;
		double ratio;				
		if (countHumanDna == 0) {
			ratio = 1;
		} else {
			ratio =   (double) (countMutantDna/countHumanDna);
		}		

		AdnVerificationModel adnVerificationModel = new AdnVerificationModel(idMutantVerification, countMutantDna, countHumanDna, ratio);

		Optional<AdnVerificationModel> AdnVerificationOpt = Optional.of(adnVerificationModel);
		when(adnVerificationRepository.findById(adnVerificationModel.getId())).thenReturn(AdnVerificationOpt);
		when(adnVerificationRepository.save(adnVerificationModel)).thenReturn(adnVerificationModel);		

		boolean result = mutantService.validateMutant(mutantModel);

		if (result) {
			adnVerificationModel.setCountMutantDna(countMutantDna+1);
		}else {
			adnVerificationModel.setCountHumanDna(countHumanDna+1);
		}

		Assertions.assertEquals(false, result);
		Assertions.assertEquals(1, adnVerificationModel.getCountHumanDna());
		Assertions.assertEquals(0, adnVerificationModel.getCountMutantDna());

	}
	
	
	@Test
	void getMutantAllTest() {

		List<String> 	adn1 = new ArrayList<String>();			
		adn1.add("ACTATA");
		adn1.add("AGCGTT");
		adn1.add("GTCTGT");
		adn1.add("AACGGG");
		adn1.add("CCCTTA");
		adn1.add("TCACGA");

		MutantModel mutantModel1 = new MutantModel();
		mutantModel1.setAdn(adn1);
		mutantModel1.setIsMutant(false);

		when(mutantRepository.save(mutantModel1)).thenReturn(mutantModel1);
		
		
		List<String> 	adn2 = new ArrayList<String>();			
		adn2.add("TTCCCC");
		adn2.add("AGTGTT");
		adn2.add("TATTGT");
		adn2.add("AATGTG");
		adn2.add("CCTTTA");
		adn2.add("TCACTA");	

		MutantModel mutantModel2 = new MutantModel();
		mutantModel2.setAdn(adn2);
		mutantModel2.setIsMutant(false);
		
		
		List<MutantModel> mutantList = new ArrayList<>();
		
		mutantList.add(mutantModel1);
		mutantList.add(mutantModel2);
		
		when(mutantRepository.findAll()).thenReturn(mutantList);
		
		List<MutantModel> result = new ArrayList<>();

		result = (List<MutantModel>) mutantService.getMutantAll();

		Assertions.assertEquals(mutantList.size(), result.size());
		Assertions.assertEquals(mutantList.get(1), result.get(1));

	}	
	
	
	@Test
	void invalidAdnNitrogenousTest() throws Exception {

		String request = "{\n" + 
				"	\"adn\": [\"DCTATA\",\n" + 
				"	        \"ACAGTT\",\n" + 
				"	        \"GTCTGT\",\n" + 
				"	        \"ACCTACC\",\n" + 
				"	        \"AATCGG\",\n" + 
				"	        \"CCTTTC\",\n" + 
				"	        \"TCACTA\"]\n" + 
				"}\n" + 
				""; 
		
		ResultActions result = mockMvc.perform(
				post("/mercadolibre/challenge/mutants")
					.contentType(MediaType.APPLICATION_JSON)
					.content(request.getBytes()))
				.andExpect(status().isBadRequest());
		String response = result.andReturn().getResponse().getContentAsString();
		response.contains("SECUENCIA_ADN_INVALIDA");
		response.contains("Tamaño de la cadena de adn que ingreso es invalida");		
	}	
	
	@Test
	void invalidSizeAdnTest() throws Exception {

		String request = "{\n" + 
				"	\"adn\": [\"TCTATA\",\n" + 
				"	        \"ACAGTT\",\n" + 
				"	        \"GTCTGT\",\n" + 
				"	        \"ACCC\",\n" + 
				"	        \"AA\",\n" + 
				"	        \"CCTTTC\",\n" + 
				"	        \"TCACTA\"]\n" + 
				"}\n" + 
				""; 
		
		ResultActions result = mockMvc.perform(
				post("/mercadolibre/challenge/mutants")
					.contentType(MediaType.APPLICATION_JSON)
					.content(request.getBytes()))
				.andExpect(status().isBadRequest());
		String response = result.andReturn().getResponse().getContentAsString();
		response.contains("SECUENCIA_ADN_INVALIDA");		
	}	
	
	@Test
	void invalidSizeAdnTest2() throws Exception {

		String request = "{\n" + 
				"	\"adn\": [\"TCTATA\",\n" + 
				"	        \"ACAGTT\",\n" + 
				"	        \"GTCTGT\",\n" + 
				"	        \"ACCC\",\n" + 
				"	        \"AA\",\n" + 
				"	        \"CCTTTC\",\n" +  
				"}\n" + 
				""; 
		
		ResultActions result = mockMvc.perform(
				post("/mercadolibre/challenge/mutants")
					.contentType(MediaType.APPLICATION_JSON)
					.content(request.getBytes()))
				.andExpect(status().isBadRequest());
		String response = result.andReturn().getResponse().getContentAsString();
		response.contains("SECUENCIA_ADN_INVALIDA");		
	}		
	
}
