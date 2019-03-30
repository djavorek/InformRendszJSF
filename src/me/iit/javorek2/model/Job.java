package me.iit.javorek2.model;

import java.util.Collection;
import java.util.Collections;

public class Job {
	private String name;
	private Collection<Task> tasks;
	
	public Job() {
		//
	}
		
	public Job(String name) {
		this.name = name;
		this.tasks = Collections.emptyList();
	}

	public Job(String name, Collection<Task> tasks) {
		this.name = name;
		this.tasks = tasks;
	}

	public String getName() {
		return name;
	}
	
	public Collection<Task> getTasks() {
		return tasks;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTasks(Collection<Task> tasks) {
		this.tasks = tasks;
	}
}
