package com.chakilo.jiraffe.utils;

/**
 * 2019.02.13
 *
 * @author Chakilo
 */
public abstract class TypeUtil {

    public static boolean couldCastToInteger(Object o) {

        return o instanceof Byte ||
                o instanceof Short ||
                o instanceof Integer ||
                o instanceof Long ||
                o instanceof Float ||
                o instanceof Double ||
                o instanceof Boolean ||
                o instanceof Character ||
                (o instanceof String && StringUtil.isNumeric((String) o));

    }

    public static int castToInteger(Object o) {

        if (o instanceof Byte) {
            return ((Byte) o).intValue();
        } else if (o instanceof Short) {
            return ((Short) o).intValue();
        } else if (o instanceof Integer) {
            return (Integer) o;
        } else if (o instanceof Long) {
            return ((Long) o).intValue();
        } else if (o instanceof Float) {
            return ((Float) o).intValue();
        } else if (o instanceof Double) {
            return ((Double) o).intValue();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? 1 : 0;
        } else if (o instanceof Character) {
            return (int) o;
        } else if (o instanceof String) {
            return Integer.parseInt((String) o);
        } else {
            return 0;
        }

    }

}
