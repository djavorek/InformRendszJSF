package me.iit.javorek2.model.exception;

public class RepositoryException extends Exception {
	
	private static final long serialVersionUID = 2210343318516486492L;
	private static final String MESSAGE = "Operation failed in Repository layer.";

	public RepositoryException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
	}

	public RepositoryException(String message) {
		super(MESSAGE + " " + message);
	}

	public RepositoryException() {
		super(MESSAGE);
	}

	public RepositoryException(Throwable cause) {
		super(MESSAGE, cause);
	}
}
