package com.chakilo.jiraffe.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 2019.02.13
 *
 * @author Chakilo
 */
public abstract class TypeUtil {

    public static boolean isPrimitive(Object o) {
        return o instanceof Byte ||
                o instanceof Short ||
                o instanceof Integer ||
                o instanceof Long ||
                o instanceof Float ||
                o instanceof Double ||
                o instanceof Boolean ||
                o instanceof Character;
    }

    public static boolean isNumber(Object o) {
        return o instanceof Number;
    }

    public static boolean couldCastToByte(Object o) {

        if (o instanceof Byte) {
            return true;
        } else if (couldCastToInteger(o)) {
            int d = castToInteger(o);
            return d >= Byte.MIN_VALUE && d <= Byte.MAX_VALUE;
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

    public static boolean couldCastToShort(Object o) {

        if (o instanceof Short) {
            return true;
        } else if (couldCastToInteger(o)) {
            int d = castToInteger(o);
            return d >= Short.MIN_VALUE && d <= Short.MAX_VALUE;
        } else {
            return false;
        }

    }

    public static short castToShort(Object o) {

        if (!couldCastToByte(o)) {
            throw new ClassCastException("Could not cast " + o.getClass().getCanonicalName() + " to java.lang.Short");
        }

        if (o instanceof Short) {
            return (Short) o;
        } else {
            return new Integer(castToInteger(o)).shortValue();
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

        return isPrimitive(o) ||
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

        return isPrimitive(o) ||
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

    public static boolean couldCastToBoolean(Object o) {
        return isPrimitive(o) ||
                (o instanceof String && (StringUtil.isNumeric((String) o) || "true".equalsIgnoreCase((String) o) || "false".equalsIgnoreCase((String) o)));
    }

    public static boolean castToBoolean(Object o) {

        if (!couldCastToBoolean(o)) {
            throw new ClassCastException("Could not cast " + o.getClass().getCanonicalName() + " to java.lang.Boolean");
        }

        if (o instanceof Byte) {
            return ((Byte) o).intValue() != 0;
        } else if (o instanceof Short) {
            return ((Short) o).intValue() != 0;
        } else if (o instanceof Integer) {
            return (Integer) o != 0;
        } else if (o instanceof Long) {
            return ((Long) o).intValue() != 0;
        } else if (o instanceof Float) {
            return ((Float) o).intValue() != 0;
        } else if (o instanceof Double) {
            return ((Double) o).intValue() != 0;
        } else if (o instanceof Boolean) {
            return (Boolean) o;
        } else if (o instanceof Character) {
            return ((int) o) != 0;
        } else if (o instanceof String) {
            String s = (String) o;
            if (StringUtil.isNumeric(s)) {
                return Long.parseLong(s, 10) != 0;
            } else {
                return "true".equalsIgnoreCase(s);
            }
        } else {
            return false;
        }

    }

    public static boolean couldCastToCharacter(Object o) {
        return isPrimitive(o) ||
                (o instanceof String && ((String) o).length() > 0);
    }

    public static char castToCharacter(Object o) {

        if (!couldCastToCharacter(o)) {
            throw new ClassCastException("Could not cast " + o.getClass().getCanonicalName() + " to java.lang.Character");
        }

        if (o instanceof Byte) {
            return (char) ((Byte) o).intValue();
        } else if (o instanceof Short) {
            return (char) ((Short) o).intValue();
        } else if (o instanceof Integer) {
            return (char) o;
        } else if (o instanceof Long) {
            return (char) ((Long) o).intValue();
        } else if (o instanceof Float) {
            return (char) ((Float) o).intValue();
        } else if (o instanceof Double) {
            return (char) ((Double) o).intValue();
        } else if (o instanceof Boolean) {
            return (Boolean) o ? 'T' : 'F';
        } else if (o instanceof Character) {
            return (char) o;
        } else if (o instanceof String) {
            String s = (String) o;
            if (s.length() > 0) {
                return s.charAt(0);
            } else {
                return 0;
            }
        } else {
            return 0;
        }

    }

    public static boolean couldCastToNumber(Object o) {
        return isPrimitive(o) ||
                (o instanceof String && (StringUtil.isRealNumber((String) o)));
    }

    public static Number castToNumber(Object o) {

        if (!couldCastToNumber(o)) {
            throw new ClassCastException("Could not cast " + o.getClass().getCanonicalName() + " to java.lang.Number");
        }

        if (isNumber(o)) {
            return (Number) o;
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? 1 : 0;
        } else if (o instanceof Character) {
            return (int) o;
        } else if (o instanceof String) {
            String s = (String) o;
            if (StringUtil.isRealNumber(s)) {
                if (s.length() > 16) {
                    if (StringUtil.isNumeric(s) || StringUtil.isBCPLStyleNumeric(s)) {
                        return castToBigInteger(s);
                    } else {
                        return castToBigDecimal(s);
                    }
                } else {
                    if (StringUtil.isNumeric(s) || StringUtil.isBCPLStyleNumeric(s)) {
                        return castToLong(s);
                    } else {
                        return castToDouble(s);
                    }
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }

    }

    public static boolean couldCastToString(Object o) {
        return true;
    }

    public static String castToString(Object o) {
        return StringUtil.toString(o);
    }

}
