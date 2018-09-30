package com.hamgooof.gpio.board.server.commands;

public abstract class CommandResponse implements Command {

    public abstract String[] responseArgs();

    public abstract boolean sendToAllClients();
}
