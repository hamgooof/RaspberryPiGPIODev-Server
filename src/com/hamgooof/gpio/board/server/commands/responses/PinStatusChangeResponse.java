package com.hamgooof.gpio.board.server.commands.responses;

import com.hamgooof.gpio.board.pins.PinStatus;
import com.hamgooof.gpio.board.server.commands.CommandResponse;

import java.util.ArrayList;
import java.util.List;

public class PinStatusChangeResponse extends CommandResponse {
    private final int _pin;
    private final int _pwm;
    private final PinStatus _pinStatus;

    public PinStatusChangeResponse(int pin, String newStatus) {
        this(pin, newStatus, 0);
    }

    public PinStatusChangeResponse(int pin, String newStatus, int pwm) {
        PinStatus pinStatus = PinStatus.GetByName(newStatus);
        //TODO Add excepton / return invalid command parameters response

        _pin = pin;
        _pinStatus = pinStatus;
        _pwm = pwm;
    }

    @Override
    public String[] responseArgs() {
        List<String> responseArgs = new ArrayList<String>();
        responseArgs.add(String.valueOf(_pin));
        responseArgs.add(_pinStatus.toString());
        if (_pinStatus == PinStatus.PWM)
            responseArgs.add(String.valueOf(_pwm));

        return responseArgs.toArray(new String[responseArgs.size()]);
    }

    @Override
    public boolean sendToAllClients() {
        return true;
    }

    @Override
    public String toString() {
        if (_pinStatus == PinStatus.PWM)
            return format("Pin %s has been set to PWM with duty cycle of %s", _pin, _pwm);
        else
            return format("Pin %s has been set to mode %s", _pin, _pinStatus);
    }
}
