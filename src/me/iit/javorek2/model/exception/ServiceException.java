package me.iit.javorek2.model.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class ServiceException.
 */
public class ServiceException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1652793834225590392L;
	
	/** The Constant MESSAGE. */
	private static final String MESSAGE = "Operation failed in Service layer.";

	/**
	 * Instantiates a new service exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ServiceException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
	}

	/**
	 * Instantiates a new service exception.
	 *
	 * @param message the message
	 */
	public ServiceException(String message) {
		super(MESSAGE + " " + message);
	}

	/**
	 * Instantiates a new service exception.
	 */
	public ServiceException() {
		super(MESSAGE);
	}

	/**
	 * Instantiates a new service exception.
	 *
	 * @param cause the cause
	 */
	public ServiceException(Throwable cause) {
		super(MESSAGE, cause);
	}
}
