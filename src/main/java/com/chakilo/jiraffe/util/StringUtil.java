package com.chakilo.jiraffe.util;

/**
 * 2019.02.13
 *
 * @author Chakilo
 */
public abstract class StringUtil {

    public static final String EMPTY = "";

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

    public static boolean isBCPLStyleNumeric(String s) {

        int len = s.length();

        if (len < 2) {
            return false;
        }

        char c0 = s.charAt(0);
        char c1 = s.charAt(1);

        if (c0 == '0') {
            if (c1 == 'x' && len >= 3) {
                for (int i = 2; i < len; i++) {
                    char c = s.charAt(i);
                    if (!('0' <= c && '9' >= c) && !('a' <= c && 'f' >= c) && !('A' <= c && 'F' >= c)) {
                        return false;
                    }
                }
                return true;
            } else if (c1 == 'b' && len >= 3) {
                for (int i = 2; i < len; i++) {
                    char c = s.charAt(i);
                    if ('0' != c && '1' != c) {
                        return false;
                    }
                }
                return true;
            } else {
                for (int i = 1; i < len; i++) {
                    char c = s.charAt(i);
                    if ('0' > c || '7' < c) {
                        return false;
                    }
                }
                return true;
            }
        }

        return false;

    }

    public static String toString(Object v) {
        return String.valueOf(v);
    }

}
