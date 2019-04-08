package me.iit.javorek2.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Worker.
 */
public class Worker {
	
	/**
	 * The Enum WorkerStatus.
	 */
	public enum WorkerStatus {
		
		/** The onleave. */
		ONLEAVE("onleave"), 
		/** The working. */
		WORKING("working"), 
		/** The free. */
		FREE("free");
		
		/** The code. */
		private final String code;
		
		/**
		 * Instantiates a new worker status.
		 *
		 * @param code the code
		 */
		private WorkerStatus(String code) {
			this.code = code;
		}
		
		/**
		 * Gets the code.
		 *
		 * @return the code
		 */
		public String getCode() {
	        return code;
	    }
	}
	
	/** The name. */
	private String name;
	
	/** The qualification. */
	private String qualification;
	
	/** The hourly wage. */
	private int hourlyWage;
	
	/** The current job. */
	private Job currentJob;
	
	/** The status. */
	private WorkerStatus status;
	
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
	 * @param qualification the qualification
	 * @param hourlyWage the hourly wage
	 * @param currentJob the current job
	 * @param status the status
	 */
	public Worker(String name, String qualification, int hourlyWage, Job currentJob, WorkerStatus status) {
		this.name = name;
		this.qualification = qualification;
		this.hourlyWage = hourlyWage;
		this.currentJob = currentJob;
		this.status = status;
	}
	
	/**
	 * Instantiates a new worker.
	 *
	 * @param name the name
	 * @param qualification the qualification
	 * @param hourlyWage the hourly wage
	 */
	public Worker(String name, String qualification, int hourlyWage) {
		this(name, qualification, hourlyWage, null, WorkerStatus.FREE);
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
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the qualification.
	 *
	 * @return the qualification
	 */
	public String getQualification() {
		return qualification;
	}

	/**
	 * Sets the qualification.
	 *
	 * @param qualification the new qualification
	 */
	public void setQualification(String qualification) {
		this.qualification = qualification;
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

	/**
	 * Gets the current job.
	 *
	 * @return the current job
	 */
	public Job getCurrentJob() {
		return currentJob;
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
	 * Gets the status.
	 *
	 * @return the status
	 */
	public WorkerStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(WorkerStatus status) {
		this.status = status;
	}
}
