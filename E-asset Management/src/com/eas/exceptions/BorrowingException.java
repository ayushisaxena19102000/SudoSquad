package com.eas.exceptions;


public class BorrowingException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BorrowingException(String message) {
        super(message);
    }

    public BorrowingException(String message, Throwable cause) {
        super(message, cause);
    }
}
