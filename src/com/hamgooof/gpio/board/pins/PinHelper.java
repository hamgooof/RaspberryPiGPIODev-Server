package com.hamgooof.gpio.board.pins;

import com.hamgooof.helpers.Logger;
import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;

import java.util.Optional;

public class PinHelper {


    //region IsProvisioned
    public static boolean IsProvisionedDigitalPin(int wiringPiPin) {
        Optional<GpioPin> o = GpioFactory.getInstance().getProvisionedPins().stream().filter(gpioPin -> gpioPin.getPin().getAddress() == wiringPiPin).findFirst();
        if (!o.isPresent())
            return false;
        return o.get() instanceof GpioPinDigitalOutput;
    }

    public static boolean IsProvisioned(int wiringPiPin) {
        Optional<GpioPin> o = GpioFactory.getInstance().getProvisionedPins().stream().filter(gpioPin -> gpioPin.getPin().getAddress() == wiringPiPin).findFirst();
        return o.isPresent();
    }

    public static boolean IsProvisionedPWMPin(int wiringPiPin) {
        Optional<GpioPin> o = GpioFactory.getInstance().getProvisionedPins().stream().filter(gpioPin -> gpioPin.getPin().getAddress() == wiringPiPin).findFirst();
        if (!o.isPresent())
            return false;
        return o.get() instanceof GpioPinPwmOutput;
    }
    //endregion

    //region GetPins
    public static GpioPinDigitalOutput GetDigitalPin(int wiringPiPin) {
        Optional<GpioPin> o = GpioFactory.getInstance().getProvisionedPins().stream().filter(gpioPin -> gpioPin.getPin().getAddress() == wiringPiPin).findFirst();
        GpioPinDigitalOutput pin;

        if (o.isPresent())
            pin = (GpioPinDigitalOutput) o.get();
        else
            pin = GpioFactory.getInstance().provisionDigitalOutputPin(GetPin(wiringPiPin));

        return pin;
    }

    public static GpioPinPwmOutput GetPWMPin(int wiringPiPin) {
        Optional<GpioPin> o = GpioFactory.getInstance().getProvisionedPins().stream().filter(gpioPin -> gpioPin.getPin().getAddress() == wiringPiPin).findFirst();
        GpioPinPwmOutput pin;

        if (o.isPresent())
            pin = (GpioPinPwmOutput) o.get();
        else
            pin = GpioFactory.getInstance().provisionSoftPwmOutputPin(GetPin(wiringPiPin));

        return pin;
    }

    public static Pin GetPin(int pin) {
        return CommandArgumentParser.getPin(
                RaspiPin.class,    // pin provider class to obtain pin instance from
                RaspiPin.getPinByAddress(pin)  // default pin if no pin argument found
        );
    }
    //endregion

    //region Un-provisioning
    public static void UnprovisionPin(int wiringPiPin) {
        Optional<GpioPin> o = GpioFactory.getInstance().getProvisionedPins().stream().filter(gpioPin -> gpioPin.getPin().getAddress() == wiringPiPin).findFirst();
        if (!o.isPresent())
            return;

        GpioPin pin = o.get();
        GpioFactory.getInstance().unprovisionPin(pin);
        log("Unprovisioned pin " + pin);
    }

    //endregion
    //region Helpers
    private static void log(Object o) {
        Logger.getLogger().writeln(o);
    }


    //endregion
}
