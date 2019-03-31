package me.iit.javorek2.model.exception;

public class ServiceException extends Exception {
	
	private static final long serialVersionUID = 1652793834225590392L;
	private static final String MESSAGE = "Operation failed in Service layer.";

	public ServiceException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
	}

	public ServiceException(String message) {
		super(MESSAGE + " " + message);
	}

	public ServiceException() {
		super(MESSAGE);
	}

	public ServiceException(Throwable cause) {
		super(MESSAGE, cause);
	}
}
