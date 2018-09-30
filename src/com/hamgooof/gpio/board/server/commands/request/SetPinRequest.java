package com.hamgooof.gpio.board.server.commands.request;

import com.hamgooof.gpio.board.server.CommandManager;
import com.hamgooof.gpio.board.server.commands.CommandRequest;
import com.hamgooof.gpio.board.server.commands.CommandResponse;

public class SetPinRequest extends CommandRequest {

	public SetPinRequest(int pin, String newStatus) {
		System.out.println("2 param called");
	}

	public SetPinRequest(int pin, String newStatus, int pwm) {
		System.out.println("3 param called");
	}

	@Override
	public CommandResponse process(CommandManager manager) {
		return null;
		// TODO Auto-generated method stub

	}

}
