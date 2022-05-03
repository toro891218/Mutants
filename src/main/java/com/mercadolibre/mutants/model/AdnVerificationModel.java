package com.mercadolibre.mutants.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "mutants_verification")
public class AdnVerificationModel {

	private String id;
	private long countMutantDna;
	private long countHumanDna;
	private double ratio;
	
	
	public AdnVerificationModel() {
		// TODO Auto-generated constructor stub
	}

	public AdnVerificationModel(String id, long countMutantDna, long countHumanDna, double ratio) {
		this.id = id;
		this.countMutantDna = countMutantDna;
		this.countHumanDna = countHumanDna;
		this.ratio = ratio;
	}


	@DynamoDBHashKey	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	@DynamoDBAttribute
	public long getCountMutantDna() {
		return countMutantDna;
	}


	public void setCountMutantDna(long countMutantDna) {
		this.countMutantDna = countMutantDna;
	}


	@DynamoDBAttribute
	public long getCountHumanDna() {
		return countHumanDna;
	}


	public void setCountHumanDna(long countHumanDna) {
		this.countHumanDna = countHumanDna;
	}

	@DynamoDBAttribute
	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}


}
