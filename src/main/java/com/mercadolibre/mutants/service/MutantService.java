package com.mercadolibre.mutants.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mercadolibre.mutants.config.PropertiesConfig;
import com.mercadolibre.mutants.exceptions.AdnLengthException;
import com.mercadolibre.mutants.exceptions.AdnNitrogenousException;
import com.mercadolibre.mutants.model.MutantModel;
import com.mercadolibre.mutants.repository.MutantRepository;

/**
 * @author ASUS
 *
 */
/**
 * @author ASUS
 *
 */
@Service
public class MutantService {
	
	@Autowired
	MutantRepository mutantRepository;
	
	@Autowired
	PropertiesConfig propertiesConfig;
	
	@Autowired
	AdnVerificationService adnVerificationService;

	
	public Boolean validateMutant(MutantModel mutant) {
		//val 1
		validateListStringSizeAdnIn(mutant.getAdn().size());
		//val 2
		char[][] adnMatriz = formaterMatriz(mutant.getAdn());				
		
		if (isMutant(adnMatriz)) {
			mutant.setIsMutant(true);
			addMutant(mutant, mutant.getIsMutant());
			return true;
		} else {
			mutant.setIsMutant(false);
			addMutant(mutant, mutant.getIsMutant());
			return false;
		}		
	}
	
	
	public Boolean isMutant(char[][] dna) {
		
		int cont = 0;
		
		int AdnMutantSecuence = PropertiesConfig.getAdnSequenceCondition();
		
		cont = cont + ValHorizontal(dna, AdnMutantSecuence);
				
		if (cont < AdnMutantSecuence ) {
			cont = cont + ValAndVertical(dna, AdnMutantSecuence);
		}else {
			return true;
		}
		
		if (cont < AdnMutantSecuence ) {		
			cont = cont + ValAndDiagonalRight(dna, AdnMutantSecuence);
		}else {
			return true;
		}
		
		if (cont < AdnMutantSecuence ) {			
			cont = cont + ValAndDiagonalLeft(dna, AdnMutantSecuence); 
		}else {
			return true;
		}

		if (cont >= AdnMutantSecuence ) {
			return true;
		}else {
			return false;
		}
	}
	
	
	// Valida secuencia de ADN Horizontalmente

	/**
	 * @param adn[][]: Matriz con secuencias de ADN
	 * @param conitionMutant : Cantidad de secuencias de ADN que se requieren para identificar a un Mutante
	 * @return int cantidad de secuencias de ADN que cumplen condición de mutante encontradas horizontalmente
	 */
	public int ValHorizontal(char[][] adn, int conitionMutant){
		
		
		int count=0;
		for (int i = 0; i < adn.length; i++) {        	
			for (int j = 0; j < adn.length; j++) {
				/*Validador Horizontal*/
				if (adn.length-j>=4) {
					if (adn[i][j] == adn[i][j+ 1] &&
							adn[i][j] == adn[i][j + 2] &&
							adn[i][j] == adn[i][j + 3]
							) {						
						count++;
						j = j + 3;						
						
						if (count>=conitionMutant){
							return count;
						}
					}
				}

			}
		}
		return count;
	}
	

	public int ValAndVertical(char[][] adn, int conitionMutant){
		int count=0;
		for (int i = 0; i < adn.length; i++) {        	
			for (int j = 0; j < adn.length; j++) {
				/*Validador Vertical*/
				if (adn.length-i>=4) {
					if (adn[i][j] == adn[i+ 1][j] &&
							adn[i][j] == adn[i + 2][j] &&
							adn[i][j] == adn[i + 3][j]
							) {
						count++;
						j = j + 3;                    
						if (count>=conitionMutant){
							return count;
						}
					}
				}	
			}
		}
		return count;
	}	
	

	public int ValAndDiagonalRight(char[][] matrizSupIzq, int conitionMutant) {
		int adnRowSize = matrizSupIzq.length;
		int indiceCharTemp = 0; 		
		int contCadenasAdnTrue = 0;		
		char adnRow[] = new char[adnRowSize];		

		for (int diagonal = 1 - adnRowSize; diagonal <= adnRowSize - 1; diagonal += 1) {       	    		
			for (int i = Math.max(0, diagonal),j = -Math.min(0, diagonal); 
					i < adnRowSize && j < adnRowSize; 
					i += 1, j += 1 ) {
				adnRow[indiceCharTemp]=matrizSupIzq[i][j];
				indiceCharTemp++;					
			} 
			
			indiceCharTemp = 0;
			contCadenasAdnTrue =  contCadenasAdnTrue + ValDiagonalRow(adnRow, conitionMutant);
			adnRow = cleanArrayChar(adnRow);
		}
		
		return contCadenasAdnTrue;

	}
	
	public  int ValAndDiagonalLeft(char[][] matrizSupIzq, int conitionMutant) {
		int adnRowSize = matrizSupIzq.length;
		int indiceCharTemp = 0; 		
		int contCadenasAdnTrue = 0;

		char adnRow[] = new char[adnRowSize];		

		for (int diagonal = 0; diagonal <= (adnRowSize*2-1); diagonal += 1) {
			for (int i, j = Math.min(diagonal,adnRowSize - 1); 		    		
					j >= 0 && (i = diagonal - j) < adnRowSize; 		    		
					j--) {		    	
				adnRow[indiceCharTemp]=matrizSupIzq[i][j];
				indiceCharTemp++;
			}						
			indiceCharTemp = 0;			
			contCadenasAdnTrue =  contCadenasAdnTrue +ValDiagonalRow(adnRow, conitionMutant);						
			adnRow = cleanArrayChar(adnRow);						
		}		
		return contCadenasAdnTrue;
	}	
	
	public static int ValDiagonalRow(char[] adnRow, int conditionMutant){
		int count=0;		        	
		for (int i = 0; i < adnRow.length && adnRow[i] != '\0'; i++) {
			/*Validador Horizontal*/
			if (adnRow.length-i>=4) {
				if (adnRow[i] == adnRow[i+ 1] &&
						adnRow[i] == adnRow[i + 2] &&
						adnRow[i] == adnRow[i + 3]
						) {
					count++;
					i = i + 3;
					if (count>=conditionMutant){
						return count;
					}
				}
			}
		}
		return count;
	}		

	public static char[] cleanArrayChar(char[] charrArray) {
		for (int i = 0; i < charrArray.length; i++) {
			charrArray[i]='\0';
		}

		return charrArray;
	}	
	
	
	
	
	
	
	
	//Valida tamaño de la lista de secuencias de ADN y que corresponda con la cantidad de basesnitrogenadas de cada cadena para formatearlas en una matriz N*N
	public char[][] formaterMatriz(List<String> adn) {
		char[][] adnMatrizFormater = new char[adn.size()][adn.size()];		
		for (int i = 0; i < adn.size(); i++) {			
			String row = adn.get(i);			
			if (row.length() != adn.size()) {
				throw new AdnLengthException(row.length(), adn.size(), this.propertiesConfig.getAdnListSizeMin());
			}else if (!PropertiesConfig.getNitrogenousBaseAdn().matcher(row).matches()) {
				throw new AdnNitrogenousException(row);
			}			
			adnMatrizFormater[i] = row.toUpperCase().toCharArray();
		}					
		return adnMatrizFormater;
	}
	
	//val 1
	//Validar tamaño de la cadena de adn que ingreso en el request 
	public void validateListStringSizeAdnIn(int size) {
		if (size < this.propertiesConfig.getAdnListSizeMin()) {
			throw new AdnLengthException(size, size, this.propertiesConfig.getAdnListSizeMin());	
		}		
	}
	
		
	/**
	 * @param mutant
	 * @param isMutant
	 * @return El mutante o humano procesado. es insertado en base de datos sino existe aun. Al ser ingresado en base de datos actualiza valores de estdisticas  
	 */
	public MutantModel addMutant(MutantModel mutant, Boolean isMutant) {		
		
		if (getMutantByAdn(mutant.getAdn()).isEmpty()) {
			
			if (isMutant) {
				adnVerificationService.addContMutant();				
			}else {				
				adnVerificationService.addContHuman();				
			}
			return this.mutantRepository.save(mutant);

		} else {
			return mutant;
		}
	}
	
	
	
	/**
	 * @param adn Cadena de ADN 
	 * @return Mutante o humano que corresponda de base de datos
	 */
	public Optional<MutantModel> getMutantByAdn(List<String> adn) {				
		//String adnlocal = Arrays.toString(adn);		
		return this.mutantRepository.findByAdn(adn);
	}
	
	
	public Collection<MutantModel> getMutantAll() {				
		return StreamSupport.stream( mutantRepository.findAll().spliterator(), false)
				.collect( Collectors.toList());		
	}		
}
