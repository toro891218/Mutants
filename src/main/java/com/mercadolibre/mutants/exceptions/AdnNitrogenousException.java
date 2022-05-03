package com.mercadolibre.mutants.exceptions;

import org.springframework.http.HttpStatus;



public class AdnNitrogenousException extends AdnException{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1089274710108827471L;

	/**
	 * error code
	 * <p>
	 * The only valid characters are A, T, C e G.
	 */
	private static final String error = "BASE_NITROGENDA_INVALIDA";

	/**
	 * default args
	 * 
	 * @param dnaRow
	 */
	public AdnNitrogenousException(String adnRow) {
		super(error, HttpStatus.BAD_REQUEST,
				"La Base nitrogenda de ADN solo puede estar compuesta por [A, T, C, G]. error en cadena: " + adnRow);
	}
		

}
