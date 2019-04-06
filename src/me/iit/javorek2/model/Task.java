package me.iit.javorek2.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Task.
 */
public class Task {
	
	/** The name. */
	private String name;
	
	/** The required machine type. */
	private String requiredMachineType;
	
	/** The duration. */
	private int duration;
	
	/**
	 * Instantiates a new task.
	 */
	public Task() {
		//
	}
	
	/**
	 * Instantiates a new task.
	 *
	 * @param name the name
	 * @param requiredMachineType the required machine type
	 * @param duration the duration
	 */
	public Task(String name, String requiredMachineType, int duration) {
		this.name = name;
		this.requiredMachineType = requiredMachineType;
		this.duration = duration;
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
	 * Gets the required machine type.
	 *
	 * @return the required machine type
	 */
	public String getRequiredMachineType() {
		return requiredMachineType;
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
	 * Sets the required machine type.
	 *
	 * @param requiredMachineType the new required machine type
	 */
	public void setRequiredMachineType(String requiredMachineType) {
		this.requiredMachineType = requiredMachineType;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	
}
