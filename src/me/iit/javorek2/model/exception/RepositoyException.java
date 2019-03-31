package me.iit.javorek2.model.exception;

public class RepositoyException extends Exception {
	
	private static final long serialVersionUID = 2210343318516486492L;
	private static final String MESSAGE = "Operation failed in Repository layer.";

	public RepositoyException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
	}

	public RepositoyException(String message) {
		super(MESSAGE + " " + message);
	}

	public RepositoyException() {
		super(MESSAGE);
	}

	public RepositoyException(Throwable cause) {
		super(MESSAGE, cause);
	}
}
