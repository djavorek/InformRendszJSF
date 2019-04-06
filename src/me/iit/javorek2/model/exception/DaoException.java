package me.iit.javorek2.model.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class DaoException.
 */
public class DaoException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7687386989920996020L;
	
	/** The Constant MESSAGE. */
	private static final String MESSAGE = "Operation failed in DAO layer.";

	/**
	 * Instantiates a new dao exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public DaoException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
	}

	/**
	 * Instantiates a new dao exception.
	 *
	 * @param message the message
	 */
	public DaoException(String message) {
		super(MESSAGE + " " + message);
	}

	/**
	 * Instantiates a new dao exception.
	 */
	public DaoException() {
		super(MESSAGE);
	}

	/**
	 * Instantiates a new dao exception.
	 *
	 * @param cause the cause
	 */
	public DaoException(Throwable cause) {
		super(MESSAGE, cause);
	}
}
