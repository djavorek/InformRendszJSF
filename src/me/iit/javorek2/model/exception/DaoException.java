package me.iit.javorek2.model.exception;

public class DaoException extends Exception {
	
	private static final long serialVersionUID = 7687386989920996020L;
	private static final String MESSAGE = "Operation failed in DAO layer.";

	public DaoException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
	}

	public DaoException(String message) {
		super(MESSAGE + " " + message);
	}

	public DaoException() {
		super(MESSAGE);
	}

	public DaoException(Throwable cause) {
		super(MESSAGE, cause);
	}
}
