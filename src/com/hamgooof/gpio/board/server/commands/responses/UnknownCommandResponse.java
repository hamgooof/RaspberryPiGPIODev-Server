package com.hamgooof.gpio.board.server.commands.responses;

import com.hamgooof.gpio.board.server.commands.CommandResponse;

public class UnknownCommandResponse extends CommandResponse {
    private String nonexistingCommand;

    public UnknownCommandResponse(String nonexistingCommand) {
        this.nonexistingCommand = nonexistingCommand;
    }

    @Override
    public String[] responseArgs() {
        return new String[]{"Unknown Command", nonexistingCommand};
    }

    @Override
    public boolean sendToAllClients() {
        return false;
    }

    @Override
    public String toString() {
        return format("Unknown command %s", nonexistingCommand);
    }
}
