package com.hamgooof.helpers;

public class NumberHelper {
    public static boolean isInt(String str) {
        try {
            int i = Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDouble(String str) {
        try {
            double i = Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
