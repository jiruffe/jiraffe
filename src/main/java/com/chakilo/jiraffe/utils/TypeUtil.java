package com.chakilo.jiraffe.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

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

        return couldCastToBigInteger(o);

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

    public static boolean couldCastToFloat(Object o) {
        return couldCastToDouble(o);
    }

    public static float castToFloat(Object o) {

        if (!couldCastToDouble(o)) {
            throw new ClassCastException("Could not cast " + o.getClass().getCanonicalName() + " to java.lang.Float");
        }

        return new Double(castToDouble(o)).floatValue();

    }

    public static boolean couldCastToDouble(Object o) {
        return couldCastToBigDecimal(o);
    }

    public static double castToDouble(Object o) {

        if (!couldCastToDouble(o)) {
            throw new ClassCastException("Could not cast " + o.getClass().getCanonicalName() + " to java.lang.Double");
        }

        if (o instanceof Byte) {
            return ((Byte) o).doubleValue();
        } else if (o instanceof Short) {
            return ((Short) o).doubleValue();
        } else if (o instanceof Integer) {
            return ((Integer) o).doubleValue();
        } else if (o instanceof Long) {
            return ((Long) o).doubleValue();
        } else if (o instanceof Float) {
            return ((Float) o).doubleValue();
        } else if (o instanceof Double) {
            return (Double) o;
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? 1d : 0d;
        } else if (o instanceof Character) {
            return (double) o;
        } else if (o instanceof String) {
            String s = (String) o;
            if (StringUtil.isRealNumber(s)) {
                return Double.parseDouble(s);
            } else {
                return 0d;
            }
        } else {
            return 0d;
        }

    }

    public static boolean couldCastToBigInteger(Object o) {

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

    public static BigInteger castToBigInteger(Object o) {

        if (!couldCastToBigInteger(o)) {
            throw new ClassCastException("Could not cast " + o.getClass().getCanonicalName() + " to java.math.BigInteger");
        }

        if (o instanceof Byte) {
            return BigInteger.valueOf(((Byte) o).longValue());
        } else if (o instanceof Short) {
            return BigInteger.valueOf(((Short) o).longValue());
        } else if (o instanceof Integer) {
            return BigInteger.valueOf(((Integer) o).longValue());
        } else if (o instanceof Long) {
            return BigInteger.valueOf((Long) o);
        } else if (o instanceof Float) {
            return BigInteger.valueOf(((Float) o).longValue());
        } else if (o instanceof Double) {
            return BigInteger.valueOf(((Double) o).longValue());
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? BigInteger.ONE : BigInteger.ZERO;
        } else if (o instanceof Character) {
            return BigInteger.valueOf((long) o);
        } else if (o instanceof String) {
            String s = (String) o;
            if (StringUtil.isNumeric(s)) {
                return new BigInteger(s, 10);
            } else if (StringUtil.isBCPLStyleNumeric(s)) {
                if (s.startsWith("0x")) {
                    return new BigInteger(s.substring(2), 16);
                } else if (s.startsWith("0b")) {
                    return new BigInteger(s.substring(2), 2);
                } else {
                    return new BigInteger(s.substring(1), 8);
                }
            } else {
                return BigInteger.ZERO;
            }
        } else {
            return BigInteger.ZERO;
        }

    }

    public static boolean couldCastToBigDecimal(Object o) {

        return o instanceof Byte ||
                o instanceof Short ||
                o instanceof Integer ||
                o instanceof Long ||
                o instanceof Float ||
                o instanceof Double ||
                o instanceof Boolean ||
                o instanceof Character ||
                (o instanceof String && StringUtil.isRealNumber((String) o));

    }

    public static BigDecimal castToBigDecimal(Object o) {

        if (!couldCastToBigDecimal(o)) {
            throw new ClassCastException("Could not cast " + o.getClass().getCanonicalName() + " to java.math.BigDecimal");
        }

        if (o instanceof Byte) {
            return BigDecimal.valueOf(((Byte) o).longValue());
        } else if (o instanceof Short) {
            return BigDecimal.valueOf(((Short) o).longValue());
        } else if (o instanceof Integer) {
            return BigDecimal.valueOf(((Integer) o).longValue());
        } else if (o instanceof Long) {
            return BigDecimal.valueOf((Long) o);
        } else if (o instanceof Float) {
            return BigDecimal.valueOf((Float) o);
        } else if (o instanceof Double) {
            return BigDecimal.valueOf((Double) o);
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? BigDecimal.ONE : BigDecimal.ZERO;
        } else if (o instanceof Character) {
            return BigDecimal.valueOf((long) o);
        } else if (o instanceof String) {
            String s = (String) o;
            if (StringUtil.isRealNumber(s)) {
                return new BigDecimal(s);
            } else {
                return BigDecimal.ZERO;
            }
        } else {
            return BigDecimal.ZERO;
        }

    }

}
