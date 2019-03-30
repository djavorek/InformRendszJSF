package me.iit.javorek2.model;

import me.iit.javorek2.model.Machine.MachineType;

public class Task {
	private String name;
	private MachineType requiredMachineType;
	private Machine executor;
	
	public Task() {
		//
	}
	
	public Task(String name, MachineType requiredMachineType) {
		this(name, requiredMachineType, null);
	}
	
	public Task(String name, MachineType requiredMachineType, Machine executor) {
		this.name = name;
		this.requiredMachineType = requiredMachineType;
		this.executor = executor;
	}

	public String getName() {
		return name;
	}

	public MachineType getRequiredMachineType() {
		return requiredMachineType;
	}

	public Machine getExecutor() {
		return executor;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRequiredMachineType(MachineType requiredMachineType) {
		this.requiredMachineType = requiredMachineType;
	}

	public void setExecutor(Machine executor) {
		this.executor = executor;
	}
}
