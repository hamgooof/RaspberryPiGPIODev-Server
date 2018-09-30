package com.hamgooof.gpio.board.server;

import com.hamgooof.gpio.board.server.commands.Command;
import com.hamgooof.gpio.board.server.commands.CommandRequest;
import com.hamgooof.gpio.board.server.commands.CommandResponse;
import com.hamgooof.gpio.board.server.commands.request.SetPinRequest;
import com.hamgooof.gpio.board.server.commands.responses.InvalidCommandArgumentsResponse;
import com.hamgooof.gpio.board.server.commands.responses.UnknownCommandResponse;
import com.hamgooof.helpers.PrimitiveHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private Map<String, Class<? extends CommandRequest>> requestMap = new HashMap<>();

    public CommandManager() {
        setupDefaultCommands();
    }

    private void setupDefaultCommands() {
        registerCommandRequest("setpin", SetPinRequest.class);
    }

    public CommandResponse processCommandFromNetwork(String command) {
        return processCommand(command, ",");
    }

    public CommandResponse processCommandFromCommandLine(String command) {
        return processCommand(command, "\\s");
    }

    private CommandResponse processCommand(String command, String regexSplit) {
        String[] commandSplit = command.split(regexSplit);
        String cmd = commandSplit[0];
        String[] args = java.util.Arrays.copyOfRange(commandSplit, 1, commandSplit.length);

        if (!requestMap.containsKey(cmd.toLowerCase()))
            return new UnknownCommandResponse(cmd);
        Command request = createCommand(cmd.toLowerCase(), args);
        if (request instanceof CommandResponse)
            return (CommandResponse) request;
        return ((CommandRequest) request).process(this);
    }

    private Command createCommand(String cmd, String[] args) {
        Class<? extends CommandRequest> commandRequestClass = requestMap.get(cmd);

        Class<?>[] argClasses = PrimitiveHelper.getTypes(args);
        try {
            Constructor<?> constructorToInvoke = commandRequestClass.getDeclaredConstructor(argClasses);

            Object[] argObjs = PrimitiveHelper.parseAndConvertTypes(args);

            return (Command) constructorToInvoke.newInstance(argObjs);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return new InvalidCommandArgumentsResponse(cmd, argClasses);
        }
    }

    public <T extends CommandRequest> boolean registerCommandRequest(String key, Class<T> clazz) {
        if (requestMap.containsKey(key.toLowerCase()))
            return false;

        requestMap.put(key.toLowerCase(), clazz);
        return true;
    }
}
