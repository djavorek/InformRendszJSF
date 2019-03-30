package me.iit.javorek2.model;

public class Worker {
	private String name;
	private Job currentJob;
	
	public Worker() {
		//
	}
	
	public Worker(String name) {
		this(name, null);
	}
	
	public Worker(String name, Job currentJob) {
		this.name = name;
		this.currentJob = currentJob;
	}

	public String getName() {
		return name;
	}
	
	public Job getCurrentJob() {
		return currentJob;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCurrentJob(Job currentJob) {
		this.currentJob = currentJob;
	}
}
