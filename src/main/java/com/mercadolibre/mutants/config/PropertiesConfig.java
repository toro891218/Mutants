package com.mercadolibre.mutants.config;

import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mutant")
public class PropertiesConfig {

	
	//min size list<string> adn 	
	private static final int ADN_LIST_SIZE_MIN = 4;
	
	private static final Pattern NITROGENOUS_BASE_DNA = Pattern.compile("[ATCG]+", Pattern.CASE_INSENSITIVE);
	
	private static final int ADN_CHAR_CONDITION = 4;	
	
	private static final int ADN_SEQUENCE_CONDITION = 2;		
	
	private static final String MUTANTS_VERIFICATION_ID = "3592f17f-bc93-44e3-8592-635bab997f9e";

	
	
	public static int getAdnCharCondition() {
		return ADN_CHAR_CONDITION;
	}


	public static int getAdnSequenceCondition() {
		return ADN_SEQUENCE_CONDITION;
	}


	public int getAdnListSizeMin() {
		return ADN_LIST_SIZE_MIN;
	}


	public static Pattern getNitrogenousBaseAdn() {
		return NITROGENOUS_BASE_DNA;
	}


	public static String getMutantsVerificationId() {
		return MUTANTS_VERIFICATION_ID;
	}
	
	
	
	
	

}
