package me.iit.javorek2.model.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class RepositoryException.
 */
public class RepositoryException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2210343318516486492L;
	
	/** The Constant MESSAGE. */
	private static final String MESSAGE = "Operation failed in Repository layer.";

	/**
	 * Instantiates a new repository exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RepositoryException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
	}

	/**
	 * Instantiates a new repository exception.
	 *
	 * @param message the message
	 */
	public RepositoryException(String message) {
		super(MESSAGE + " " + message);
	}

	/**
	 * Instantiates a new repository exception.
	 */
	public RepositoryException() {
		super(MESSAGE);
	}

	/**
	 * Instantiates a new repository exception.
	 *
	 * @param cause the cause
	 */
	public RepositoryException(Throwable cause) {
		super(MESSAGE, cause);
	}
}
