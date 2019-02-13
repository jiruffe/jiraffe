package com.chakilo.utils;

/**
 * 2019.02.13
 *
 * @author Chakilo
 */
public abstract class StringUtil {

    public static boolean isRealNumber(String s) {

        if (s == null || "".equals(s))
            return false;

        int index = s.indexOf(".");

        if (index < 0) {

            return isNumeric(s);

        } else {

            String num1 = s.substring(0, index);
            String num2 = s.substring(index + 1);

            return isNumeric(num1) && isNumeric(num2);

        }

    }

    public static boolean isNumeric(String s) {

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ('0' > c || '9' < c) {
                return false;
            }
        }

        return true;

    }

    public static String toString(Object v) {
        return v.toString();
    }

}
