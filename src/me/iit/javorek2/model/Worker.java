package me.iit.javorek2.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Worker.
 */
public class Worker {
	
	/** The name. */
	private String name;
	
	/** The hourly wage. */
	private int hourlyWage;
	
	/** The current job. */
	private Job currentJob;
	
	/**
	 * Instantiates a new worker.
	 */
	public Worker() {
		//
	}
	
	/**
	 * Instantiates a new worker.
	 *
	 * @param name the name
	 * @param hourlyWage the hourly wage
	 */
	public Worker(String name, int hourlyWage) {
		this(name, hourlyWage, null);
	}
	
	/**
	 * Instantiates a new worker.
	 *
	 * @param name the name
	 * @param hourlyWage the hourly wage
	 * @param currentJob the current job
	 */
	public Worker(String name, int hourlyWage, Job currentJob) {
		this.name = name;
		this.currentJob = currentJob;
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
	 * Gets the current job.
	 *
	 * @return the current job
	 */
	public Job getCurrentJob() {
		return currentJob;
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
	 * Sets the current job.
	 *
	 * @param currentJob the new current job
	 */
	public void setCurrentJob(Job currentJob) {
		this.currentJob = currentJob;
	}

	/**
	 * Gets the hourly wage.
	 *
	 * @return the hourly wage
	 */
	public int getHourlyWage() {
		return hourlyWage;
	}

	/**
	 * Sets the hourly wage.
	 *
	 * @param hourlyWage the new hourly wage
	 */
	public void setHourlyWage(int hourlyWage) {
		this.hourlyWage = hourlyWage;
	}
}
