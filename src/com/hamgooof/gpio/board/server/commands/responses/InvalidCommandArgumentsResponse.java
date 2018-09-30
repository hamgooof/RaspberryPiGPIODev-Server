package com.hamgooof.gpio.board.server.commands.responses;

import com.hamgooof.gpio.board.server.commands.CommandResponse;

public class InvalidCommandArgumentsResponse extends CommandResponse {
	private String nonexistingCommand;
	private Class<?>[] receivedTypes;
	//private Class<?> expectedTypes;

	public InvalidCommandArgumentsResponse(String nonexistingCommand, Class<?>[] receivedTypes) {
		this.nonexistingCommand = nonexistingCommand;
		this.receivedTypes = receivedTypes;
		//this.expectedTypes = expectedTypes;
	}

	@Override
	public String[] responseArgs() {
		return new String[] { "Unknown Command2", nonexistingCommand };
	}

	@Override
	public boolean sendToAllClients() {
		return false;
	}
}
