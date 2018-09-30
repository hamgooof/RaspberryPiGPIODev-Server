package com.hamgooof.gpio.board.server.commands;

import com.hamgooof.gpio.board.server.CommandManager;

public abstract class CommandRequest implements Command {
	public abstract CommandResponse process(CommandManager manager);
}
