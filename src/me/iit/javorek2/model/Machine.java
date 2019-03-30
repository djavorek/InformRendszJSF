package me.iit.javorek2.model;

public class Machine {
	public enum MachineType {
		CNC_MILLING, CNC_LATHE, MEASURING 
	};
	
	private String name;
	private MachineType type;
	private boolean working;
	
	public Machine() {
		//
	}
	
	public Machine(String name, MachineType type) {
		this(name, type, false);
	}
	
	public Machine(String name, MachineType type, boolean working) {
		this.name = name;
		this.type = type;
		this.working = working;
	}

	public String getName() {
		return name;
	}

	public MachineType getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(MachineType type) {
		this.type = type;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}
}
