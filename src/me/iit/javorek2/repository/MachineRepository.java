package me.iit.javorek2.repository;

import me.iit.javorek2.model.Machine;
import me.iit.javorek2.model.exception.RepositoyException;

public interface MachineRepository {

	Machine getMachineByName(String machineName) throws RepositoyException;

}