package com.mercadolibre.mutants.exceptions;

import org.springframework.http.HttpStatus;

public class AdnLengthException extends AdnException{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1089274710108827471L;

	/**
	 * error code
	 * <p>
	 * The only valid characters are A, T, C e G.
	 */
	private static final String error = "SECUENCIA_ADN_INVALIDA";

	/**
	 * default args
	 * 
	 * @param dnaRow
	 */	
	public AdnLengthException(int filas, int columnas, int sizeMin ) {
		super(error, HttpStatus.BAD_REQUEST,
				"Tamaño de la cadena de adn que ingreso es invalida: ["+ filas +"]X[" + columnas + "]");
	}
	
}
