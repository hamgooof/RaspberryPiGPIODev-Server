package com.hamgooof.gpio.board;

import com.hamgooof.gpio.board.pins.PinHelper;
import com.hamgooof.gpio.board.server.ServerManager;
import com.hamgooof.helpers.Logger;
import com.hamgooof.helpers.PrimitiveHelper;
import com.pi4j.io.gpio.*;
import com.pi4j.platform.Platform;

public class Main {
    private static Platform platform;
    private static String platformName;

    public static void main(String[] args) throws Exception {

        //Initialize classes
        BoardHelper.Initialize();
        int port = 8070;
        Logger.getLogger().blankln(5);

        Logger.getLogger().writeln("####################################");
        Logger.getLogger().writeln("# Starting RaspberryPiGPIODevTools #");
        Logger.getLogger().writeln(String.format("%-35s", "# Board: " + BoardHelper.getPlatformName()) + "#");
        Logger.getLogger().writeln("####################################");
        Logger.getLogger().blankln();

        ServerManager serverManager = new ServerManager(new Main(), port);
        serverManager.run();
    }

    public void parseCommand(String[] inputs) {
        switch (inputs[0]) {
            case "setpin":
                if (!checkInputLength(inputs, 3))
                    return;
                SetPin(inputs);
                return;
            case "getall":
                GetPin();
                return;
            default:
                Logger.getLogger().writeln("Unexpected command: " + inputs[0]);
        }
    }

    private void GetPin() {
        Pin[] pins = BoardHelper.GetAllBoardPins();
        Logger.getLogger().writeln(String.format("All pins: %s", pins.length));
        for (Pin pin : pins) {
            Logger.getLogger().writeln(String.format("Pin %s %s %s", pin.getAddress(), pin.getName(), pin.getProvider()));
        }
    }

    private void SetPin(String[] inputs) {
        if (!PrimitiveHelper.isInt(inputs[1])) {
            Logger.getLogger().writeln(String.format("Pin number is not an integer: " + inputs[1]));
            return;
        }
        int pin = Integer.parseInt(inputs[1]);
        switch (inputs[2].toLowerCase()) {
            case "off":
                if (PinHelper.IsProvisioned(pin))
                    PinHelper.UnprovisionPin(pin);
                else
                    Logger.getLogger().writeln("Pin " + pin + " isn't provisioned");
                break;
            case "on":
                if (PinHelper.IsProvisioned(pin) && !PinHelper.IsProvisionedDigitalPin(pin))
                    PinHelper.UnprovisionPin(pin);
                PinHelper.GetDigitalPin(pin).setState(PinState.HIGH);
                Logger.getLogger().writeln("Pin " + pin + " set to high");

                break;
            case "pwm":
                if (PinHelper.IsProvisioned(pin) && !PinHelper.IsProvisionedPWMPin(pin))
                    PinHelper.UnprovisionPin(pin);
                int duty = Integer.parseInt(inputs[3]);
                PinHelper.GetPWMPin(pin).setPwm(duty);
                Logger.getLogger().writeln("SetPin to PWM " + duty);

                break;
            default:
                Logger.getLogger().writeln("Unknown SetPin option " + inputs[2]);
                break;
        }
    }


    private boolean checkInputLength(String[] inputs, int argsLen) {
        if (inputs.length < argsLen) {
            Logger.getLogger().writeln(String.format("Received %s args for command %s, expected at least %s", inputs.length, inputs[0], argsLen));
            return false;
        }
        return true;
    }
}
