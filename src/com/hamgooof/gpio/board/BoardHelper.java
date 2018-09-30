package com.hamgooof.gpio.board;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.exception.UnsupportedBoardType;
import com.pi4j.platform.Platform;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BoardHelper {

    private static String platformName;
    private static Platform platform;
    private static boolean isInitialized = false;

    public static void Initialize() throws Exception {
        if (isInitialized)
            throw new Exception("BoardHelper is already initialized");

        Process p = Runtime.getRuntime().exec("uname -n");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(p.getInputStream()));

        platformName = in.readLine();
        platform = Platform.fromId(platformName);

        isInitialized = true;
    }

    public static String getPlatformName() {
        return platformName;
    }

    public static Platform getPlatform() {
        return platform;
    }

    public static Pin[] GetAllBoardPins() {
        switch (platform) {
            case RASPBERRYPI:
                return RaspiPin.allPins();
            case BANANAPI:
                return BananaPiPin.allPins();
            case BANANAPRO:
                return BananaProPin.allPins();
            case BPI:
                return BpiPin.allPins();
            case ODROID:
                throw new NotImplementedException();
            case ORANGEPI:
                return OrangePiPin.allPins();
            case NANOPI:
                return NanoPiPin.allPins();
            case SIMULATED:
                throw new NotImplementedException();
            default:
                throw new UnsupportedBoardType();

        }
    }
}

