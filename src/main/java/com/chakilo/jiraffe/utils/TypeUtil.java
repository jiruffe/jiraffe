package com.chakilo.jiraffe.utils;

/**
 * 2019.02.13
 *
 * @author Chakilo
 */
public abstract class TypeUtil {

    public static boolean couldCastToByte(Object o) {

        if (o instanceof Byte) {
            return true;
        } else if (couldCastToInteger(o)) {
            int d = castToInteger(o);
            return d >= 0 && d <= 0xff;
        } else {
            return false;
        }

    }

    public static byte castToByte(Object o) {

        if (!couldCastToByte(o)) {
            throw new ClassCastException("Could not cast " + o.getClass().getCanonicalName() + " to java.lang.Byte");
        }

        if (o instanceof Byte) {
            return (Byte) o;
        } else {
            return new Integer(castToInteger(o)).byteValue();
        }

    }

    public static boolean couldCastToInteger(Object o) {

        return couldCastToLong(o);

    }

    public static int castToInteger(Object o) {

        if (!couldCastToInteger(o)) {
            throw new ClassCastException("Could not cast " + o.getClass().getCanonicalName() + " to java.lang.Integer");
        }

        return new Long(castToLong(o)).intValue();

    }

    public static boolean couldCastToLong(Object o) {

        return o instanceof Byte ||
                o instanceof Short ||
                o instanceof Integer ||
                o instanceof Long ||
                o instanceof Float ||
                o instanceof Double ||
                o instanceof Boolean ||
                o instanceof Character ||
                (o instanceof String && (StringUtil.isNumeric((String) o) || StringUtil.isBCPLStyleNumeric((String) o)));

    }

    public static long castToLong(Object o) {

        if (!couldCastToLong(o)) {
            throw new ClassCastException("Could not cast " + o.getClass().getCanonicalName() + " to java.lang.Long");
        }

        if (o instanceof Byte) {
            return ((Byte) o).longValue();
        } else if (o instanceof Short) {
            return ((Short) o).longValue();
        } else if (o instanceof Integer) {
            return ((Integer) o).longValue();
        } else if (o instanceof Long) {
            return (Long) o;
        } else if (o instanceof Float) {
            return ((Float) o).longValue();
        } else if (o instanceof Double) {
            return ((Double) o).longValue();
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? 1L : 0L;
        } else if (o instanceof Character) {
            return (long) o;
        } else if (o instanceof String) {
            String s = (String) o;
            if (StringUtil.isNumeric(s)) {
                return Long.parseLong(s, 10);
            } else if (StringUtil.isBCPLStyleNumeric(s)) {
                if (s.startsWith("0x")) {
                    return Long.parseLong(s.substring(2), 16);
                } else if (s.startsWith("0b")) {
                    return Long.parseLong(s.substring(2), 2);
                } else {
                    return Long.parseLong(s.substring(1), 8);
                }
            } else {
                return 0L;
            }
        } else {
            return 0L;
        }

    }

}
