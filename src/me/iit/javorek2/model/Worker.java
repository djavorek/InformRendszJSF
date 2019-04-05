package me.iit.javorek2.model;

public class Worker {
	private String name;
	private int hourlyWage;
	private Job currentJob;
	
	public Worker() {
		//
	}
	
	public Worker(String name, int hourlyWage) {
		this(name, hourlyWage, null);
	}
	
	public Worker(String name, int hourlyWage, Job currentJob) {
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

	public int getHourlyWage() {
		return hourlyWage;
	}

	public void setHourlyWage(int hourlyWage) {
		this.hourlyWage = hourlyWage;
	}
}
