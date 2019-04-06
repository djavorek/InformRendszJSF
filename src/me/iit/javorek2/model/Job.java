package me.iit.javorek2.model;

import java.util.Collection;
import java.util.Collections;

// TODO: Auto-generated Javadoc
/**
 * The Class Job.
 */
public class Job {
	
	/** The name. */
	private String name;
	
	/** The tasks. */
	private Collection<Task> tasks;
	
	/**
	 * Instantiates a new job.
	 */
	public Job() {
		//
	}
		
	/**
	 * Instantiates a new job.
	 *
	 * @param name the name
	 */
	public Job(String name) {
		this.name = name;
		this.tasks = Collections.emptyList();
	}

	/**
	 * Instantiates a new job.
	 *
	 * @param name the name
	 * @param tasks the tasks
	 */
	public Job(String name, Collection<Task> tasks) {
		this.name = name;
		this.tasks = tasks;
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
	 * Gets the tasks.
	 *
	 * @return the tasks
	 */
	public Collection<Task> getTasks() {
		return tasks;
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
	 * Sets the tasks.
	 *
	 * @param tasks the new tasks
	 */
	public void setTasks(Collection<Task> tasks) {
		this.tasks = tasks;
	}
}
