package me.iit.javorek2.controller.dto;

import me.iit.javorek2.model.Machine;

public class MachineWrapper {
	private Machine machineData;
	private boolean checked;
	
	public MachineWrapper(Machine machineData) {
		this(machineData, false);
	}
	
	public MachineWrapper(Machine machineData, boolean checked) {
		this.machineData = machineData;
		this.checked = checked;
	}

	public Machine getMachineData() {
		return machineData;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setMachineData(Machine machineData) {
		this.machineData = machineData;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
