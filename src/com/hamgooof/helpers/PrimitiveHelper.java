package com.hamgooof.helpers;

import java.util.ArrayList;
import java.util.List;

public class PrimitiveHelper {
    // region isType
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

    public static boolean isBoolean(String str) {
        try {
            String toUpper = str.toUpperCase();
            return toUpper == "TRUE" || toUpper == "FALSE";
        } catch (Exception e) {
            return false;
        }
    }
    // endregion

    // region toType
    public static boolean toBoolean(String str) {
        return Boolean.parseBoolean(str);
    }

    public static int toInt(String str) {
        return Integer.parseInt(str);
    }

    public static double toDouble(String str) {
        return Double.parseDouble(str);
    }
    // endregion

    //region parseAndConvert
    public static Object parseAndConvertType(String str) {
        Class type = getType(str);
        switch (type.getName()) {
            case "boolean":
                return toBoolean(str);
            case "int":
                return toInt(str);
            case "double":
                return toDouble(str);
            case "java.lang.String":
                return str;
            default:
                return null;
        }
    }

    public static Object[] parseAndConvertTypes(String[] str) {
        List<Object> objects = new ArrayList<Object>();

        for (String data : str) {
            objects.add(parseAndConvertType(data));
        }
        return objects.toArray(new Object[objects.size()]);

    }
    //endregion

    //region getTypes
    public static Class getType(String str) {
        if (isInt(str))
            return Integer.TYPE;
        if (isDouble(str))
            return Double.TYPE;
        if (isBoolean(str))
            return Boolean.TYPE;
        return String.class;
    }

    public static Class<?>[] getTypes(String[] args) {
        List<Class<?>> types = new ArrayList<Class<?>>();

        for (String str : args)
            types.add(getType(str));

        return types.toArray(new Class<?>[types.size()]);
    }
    //endregion

}
