package me.iit.javorek2.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Machine.
 */
public class Machine {
	
	/** The name. */
	private String name;
	
	/** The type. */
	private String type;
	
	/** The working. */
	private boolean working;
	
	/**
	 * Instantiates a new machine.
	 */
	public Machine() {
		//
	}
	
	/**
	 * Instantiates a new machine.
	 *
	 * @param name the name
	 * @param type the type
	 */
	public Machine(String name, String type) {
		this(name, type, false);
	}
	
	/**
	 * Instantiates a new machine.
	 *
	 * @param name the name
	 * @param type the type
	 * @param working the working
	 */
	public Machine(String name, String type, boolean working) {
		this.name = name;
		this.type = type;
		this.working = working;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Checks if is working.
	 *
	 * @return true, if is working
	 */
	public boolean isWorking() {
		return working;
	}

	/**
	 * Sets the working.
	 *
	 * @param working the new working
	 */
	public void setWorking(boolean working) {
		this.working = working;
	}
}
