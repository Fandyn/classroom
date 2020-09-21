package com.util;

public class BaseUtil {
    public static boolean isNotNull(Object obj) {
        if (obj != null && !obj.equals("") && !obj.equals("null") && !obj.equals("undefined")) {
            return true;
        } else {
            return false;
        }
    }

    public static int transObjectToInt(Object obj) {
        if (obj != null && !obj.equals("") && !obj.equals("null") && !obj.equals("undefined")) {
            return Integer.parseInt(obj.toString());
        } else {
            return -1;
        }
    }
}
