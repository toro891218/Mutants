package com.mercadolibre.mutants.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.mutants.config.PropertiesConfig;
import com.mercadolibre.mutants.model.AdnVerificationModel;
import com.mercadolibre.mutants.repository.AdnVerificationRepository;

@Service
public class AdnVerificationService {

	@Autowired
	AdnVerificationRepository adnVerificationRepository;

	@Autowired
	PropertiesConfig propertiesConfig;
	
	String ID_MUTANT_VERIFICATION = PropertiesConfig.getMutantsVerificationId();

	
	public Optional<AdnVerificationModel> getAdnVerificationModel() {
		return adnVerificationRepository.findById(ID_MUTANT_VERIFICATION);
	}
	
	public void addContHuman() {		
		AdnVerificationModel adnVerificationModel = new AdnVerificationModel();
		Optional<AdnVerificationModel> mutantStates = adnVerificationRepository.findById(ID_MUTANT_VERIFICATION);
		
		adnVerificationModel.setId(mutantStates.get().getId());
		adnVerificationModel.setCountHumanDna(mutantStates.get().getCountHumanDna());
		adnVerificationModel.setCountMutantDna(mutantStates.get().getCountMutantDna());
				
		adnVerificationModel.setCountHumanDna(adnVerificationModel.getCountHumanDna()+1);
		adnVerificationModel.setRatio(setRatio(adnVerificationModel));
		
		adnVerificationRepository.save(adnVerificationModel);
	}
		
	public void addContMutant() {
		
		AdnVerificationModel adnVerificationModel = new AdnVerificationModel();
		Optional<AdnVerificationModel> mutantStates = adnVerificationRepository.findById(ID_MUTANT_VERIFICATION);

		adnVerificationModel.setId(mutantStates.get().getId());
		adnVerificationModel.setCountMutantDna(mutantStates.get().getCountMutantDna());
		adnVerificationModel.setCountHumanDna(mutantStates.get().getCountHumanDna());
		adnVerificationModel.setCountMutantDna(adnVerificationModel.getCountMutantDna()+1);
		adnVerificationModel.setRatio(setRatio(adnVerificationModel));

		adnVerificationRepository.save(adnVerificationModel);
		
	}
	
	
		
	public double setRatio(AdnVerificationModel adnVerificationModel) {
		double ratio = 0;
		if (adnVerificationModel.getCountMutantDna() != 0) {
			if (adnVerificationModel.getCountHumanDna() == 0) {
				ratio = 1;
			} else {
				ratio = (double)adnVerificationModel.getCountMutantDna()/adnVerificationModel.getCountHumanDna();
			}
		}
		return ratio;
	}	
	
	

}
