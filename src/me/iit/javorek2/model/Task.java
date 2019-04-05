package me.iit.javorek2.model;

public class Task {
	private String name;
	private String requiredMachineType;
	private int duration;
	
	public Task() {
		//
	}
	
	public Task(String name, String requiredMachineType, int duration) {
		this.name = name;
		this.requiredMachineType = requiredMachineType;
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public String getRequiredMachineType() {
		return requiredMachineType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRequiredMachineType(String requiredMachineType) {
		this.requiredMachineType = requiredMachineType;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	
}
