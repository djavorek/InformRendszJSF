package me.iit.javorek2.model;

public class Task {
	private String name;
	private String requiredMachineType;
	private Machine executor;
	
	public Task() {
		//
	}
	
	public Task(String name, String requiredMachineType) {
		this(name, requiredMachineType, null);
	}
	
	public Task(String name, String requiredMachineType, Machine executor) {
		this.name = name;
		this.requiredMachineType = requiredMachineType;
		this.executor = executor;
	}

	public String getName() {
		return name;
	}

	public String getRequiredMachineType() {
		return requiredMachineType;
	}

	public Machine getExecutor() {
		return executor;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRequiredMachineType(String requiredMachineType) {
		this.requiredMachineType = requiredMachineType;
	}

	public void setExecutor(Machine executor) {
		this.executor = executor;
	}
}
