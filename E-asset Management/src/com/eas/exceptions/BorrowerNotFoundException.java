package com.eas.exceptions;


public class BorrowerNotFoundException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BorrowerNotFoundException(String message) {
        super(message);
    }

    public BorrowerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
