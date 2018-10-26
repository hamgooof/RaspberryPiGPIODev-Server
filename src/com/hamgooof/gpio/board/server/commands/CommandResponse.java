package com.hamgooof.gpio.board.server.commands;

public abstract class CommandResponse implements Command {

    public abstract String[] responseArgs();

    public abstract boolean sendToAllClients();

    @Override
    public abstract String toString();

    protected String format(String str, Object... objs) {
        return String.format(str, objs);
    }
}
