package com.hamgooof.gpio.board.pins;

public enum PinStatus {
    Off, On, PWM;

    public static PinStatus GetByName(String name) {
        for (PinStatus ps : values())
            if (ps.toString().toLowerCase() == name.toLowerCase())
                return ps;
        return null;
    }
}
