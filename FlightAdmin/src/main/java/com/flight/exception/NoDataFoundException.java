package com.flight.exception;

public class NoDataFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoDataFoundException() {
	}
	
	public NoDataFoundException(String message) {
		super(message);
	}
}