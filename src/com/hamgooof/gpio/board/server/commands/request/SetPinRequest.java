package com.hamgooof.gpio.board.server.commands.request;

import com.hamgooof.gpio.board.pins.PinHelper;
import com.hamgooof.gpio.board.pins.PinStatus;
import com.hamgooof.gpio.board.server.CommandManager;
import com.hamgooof.gpio.board.server.commands.CommandRequest;
import com.hamgooof.gpio.board.server.commands.CommandResponse;
import com.hamgooof.helpers.Logger;
import com.pi4j.io.gpio.PinState;

public class SetPinRequest extends CommandRequest {

    private final int _pin;
    private final int _pwm;
    private final PinStatus _pinStatus;

    public SetPinRequest(int pin, String newStatus) {
        this(pin, newStatus, 0);
    }

    public SetPinRequest(int pin, String newStatus, int pwm) {
        PinStatus pinStatus = PinStatus.GetByName(newStatus);
        //TODO Add excepton / return invalid command parameters response

        _pin = pin;
        _pinStatus = pinStatus;
        _pwm = pwm;
    }

    @Override
    public CommandResponse process(CommandManager manager) {
        int pin = _pin;
        switch (_pinStatus) {
            case Off:
                if (PinHelper.IsProvisioned(pin))
                    PinHelper.UnprovisionPin(pin);
                else
                    Logger.getLogger().writeln("Pin " + pin + " isn't provisioned");
                break;
            case On:
                if (PinHelper.IsProvisioned(pin) && !PinHelper.IsProvisionedDigitalPin(pin))
                    PinHelper.UnprovisionPin(pin);
                PinHelper.GetDigitalPin(pin).setState(PinState.HIGH);
                Logger.getLogger().writeln("Pin " + pin + " set to high");

                break;
            case PWM:
                if (PinHelper.IsProvisioned(pin) && !PinHelper.IsProvisionedPWMPin(pin))
                    PinHelper.UnprovisionPin(pin);

                PinHelper.GetPWMPin(pin).setPwm(_pwm);
                Logger.getLogger().writeln("SetPin to PWM " + _pwm);

                break;
            default:
                Logger.getLogger().writeln("Unknown SetPin option ");
                break;
        }
        return null;
        // TODO Auto-generated method stub

    }


}
