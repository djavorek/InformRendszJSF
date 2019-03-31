package me.iit.javorek2.model;

public class Machine {
	
	private String name;
	private String type;
	private boolean working;
	
	public Machine() {
		//
	}
	
	public Machine(String name, String type) {
		this(name, type, false);
	}
	
	public Machine(String name, String type, boolean working) {
		this.name = name;
		this.type = type;
		this.working = working;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}
}
