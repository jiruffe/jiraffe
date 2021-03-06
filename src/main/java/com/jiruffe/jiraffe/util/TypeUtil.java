/*
 *    Copyright 2018 Jiruffe
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.jiruffe.jiraffe.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Util for type conversion.
 *
 * @author Jiruffe
 * 2019.02.13
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

    public static boolean isPrimitive(Class t) {
        return Byte.class.isAssignableFrom(t) ||
                Byte.TYPE == t ||
                Short.class.isAssignableFrom(t) ||
                Short.TYPE == t ||
                Integer.class.isAssignableFrom(t) ||
                Integer.TYPE == t ||
                Long.class.isAssignableFrom(t) ||
                Long.TYPE == t ||
                Float.class.isAssignableFrom(t) ||
                Float.TYPE == t ||
                Double.class.isAssignableFrom(t) ||
                Double.TYPE == t ||
                Boolean.class.isAssignableFrom(t) ||
                Boolean.TYPE == t ||
                Character.class.isAssignableFrom(t) ||
                Character.TYPE == t;
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
            throw new ClassCastException("Could not cast " + ObjectUtil.getCanonicalName(o) + " to java.lang.Byte");
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
            throw new ClassCastException("Could not cast " + ObjectUtil.getCanonicalName(o) + " to java.lang.Short");
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
            throw new ClassCastException("Could not cast " + ObjectUtil.getCanonicalName(o) + " to java.lang.Integer");
        }

        return new Long(castToLong(o)).intValue();

    }

    public static boolean couldCastToLong(Object o) {

        return couldCastToBigInteger(o);

    }

    public static long castToLong(Object o) {

        if (!couldCastToLong(o)) {
            throw new ClassCastException("Could not cast " + ObjectUtil.getCanonicalName(o) + " to java.lang.Long");
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
            return (long) ((int) o >= '0' && (int) o <= '9' ? (int) o - '0' : (int) o);
        } else if (o instanceof Number) {
            return ((Number) o).longValue();
        } else if (o instanceof String) {
            String s = (String) o;
            if (StringUtil.isBCPLStyleNumeric(s)) {
                if (s.startsWith("0x")) {
                    return Long.parseLong(s.substring(2), 16);
                } else if (s.startsWith("0b")) {
                    return Long.parseLong(s.substring(2), 2);
                } else {
                    return Long.parseLong(s.substring(1), 8);
                }
            } else if (StringUtil.isNumeric(s)) {
                return Long.parseLong(s, 10);
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
            throw new ClassCastException("Could not cast " + ObjectUtil.getCanonicalName(o) + " to java.lang.Float");
        }

        return new Double(castToDouble(o)).floatValue();

    }

    public static boolean couldCastToDouble(Object o) {
        return couldCastToBigDecimal(o);
    }

    public static double castToDouble(Object o) {

        if (!couldCastToDouble(o)) {
            throw new ClassCastException("Could not cast " + ObjectUtil.getCanonicalName(o) + " to java.lang.Double");
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
            return (double) ((int) o >= '0' && (int) o <= '9' ? (int) o - '0' : (int) o);
        } else if (o instanceof Number) {
            return ((Number) o).doubleValue();
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

        return isPrimitive(o)
                || isNumber(o)
                || (o instanceof String && (StringUtil.isNumeric((String) o) || StringUtil.isBCPLStyleNumeric((String) o)));

    }

    public static BigInteger castToBigInteger(Object o) {

        if (!couldCastToBigInteger(o)) {
            throw new ClassCastException("Could not cast " + ObjectUtil.getCanonicalName(o) + " to java.math.BigInteger");
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
            return BigInteger.valueOf((int) o >= '0' && (int) o <= '9' ? (int) o - '0' : (int) o);
        } else if (o instanceof Number) {
            return BigInteger.valueOf(((Number) o).longValue());
        } else if (o instanceof String) {
            String s = (String) o;
            if (StringUtil.isBCPLStyleNumeric(s)) {
                if (s.startsWith("0x")) {
                    return new BigInteger(s.substring(2), 16);
                } else if (s.startsWith("0b")) {
                    return new BigInteger(s.substring(2), 2);
                } else {
                    return new BigInteger(s.substring(1), 8);
                }
            } else if (StringUtil.isNumeric(s)) {
                return new BigInteger(s, 10);
            } else {
                return BigInteger.ZERO;
            }
        } else {
            return BigInteger.ZERO;
        }

    }

    public static boolean couldCastToBigDecimal(Object o) {

        return isPrimitive(o)
                || isNumber(o)
                || (o instanceof String && StringUtil.isRealNumber((String) o));

    }

    public static BigDecimal castToBigDecimal(Object o) {

        if (!couldCastToBigDecimal(o)) {
            throw new ClassCastException("Could not cast " + ObjectUtil.getCanonicalName(o) + " to java.math.BigDecimal");
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
            return BigDecimal.valueOf((int) o >= '0' && (int) o <= '9' ? (int) o - '0' : (int) o);
        } else if (o instanceof Number) {
            return BigDecimal.valueOf(((Number) o).doubleValue());
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
        return isPrimitive(o)
                || isNumber(o)
                || (o instanceof String && (StringUtil.isNumeric((String) o) || "true".equalsIgnoreCase((String) o) || "false".equalsIgnoreCase((String) o)));
    }

    public static boolean castToBoolean(Object o) {

        if (!couldCastToBoolean(o)) {
            throw new ClassCastException("Could not cast " + ObjectUtil.getCanonicalName(o) + " to java.lang.Boolean");
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
            return ((int) o) != 0 && ((int) o) != '0';
        } else if (o instanceof Number) {
            return ((Number) o).intValue() != 0;
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
        return isPrimitive(o)
                || isNumber(o)
                || (o instanceof String && ((String) o).length() > 0);
    }

    public static char castToCharacter(Object o) {

        if (!couldCastToCharacter(o)) {
            throw new ClassCastException("Could not cast " + ObjectUtil.getCanonicalName(o) + " to java.lang.Character");
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
        } else if (o instanceof Number) {
            return (char) ((Number) o).intValue();
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
        return isPrimitive(o)
                || isNumber(o)
                || (o instanceof String && (StringUtil.isRealNumber((String) o) || StringUtil.isBCPLStyleNumeric((String) o)));
    }

    public static Number castToNumber(Object o) {

        if (!couldCastToNumber(o)) {
            throw new ClassCastException("Could not cast " + ObjectUtil.getCanonicalName(o) + " to java.lang.Number");
        }

        if (isNumber(o)) {
            return (Number) o;
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? 1 : 0;
        } else if (o instanceof Character) {
            return (int) o >= '0' && (int) o <= '9' ? (int) o - '0' : (int) o;
        } else if (o instanceof String) {
            String s = (String) o;
            if (StringUtil.isRealNumber(s) || StringUtil.isBCPLStyleNumeric(s)) {
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

    public static <T> T castFromString(String s, Class<?> target) {

        if (Byte.class.isAssignableFrom(target) || Byte.TYPE == target) {
            return (T) (Byte) castToByte(s);
        } else if (Short.class.isAssignableFrom(target) || Short.TYPE == target) {
            return (T) (Short) castToShort(s);
        } else if (Integer.class.isAssignableFrom(target) || Integer.TYPE == target) {
            return (T) (Integer) castToInteger(s);
        } else if (Long.class.isAssignableFrom(target) || Long.TYPE == target) {
            return (T) (Long) castToLong(s);
        } else if (Float.class.isAssignableFrom(target) || Float.TYPE == target) {
            return (T) (Float) castToFloat(s);
        } else if (Double.class.isAssignableFrom(target) || Double.TYPE == target) {
            return (T) (Double) castToDouble(s);
        } else if (Boolean.class.isAssignableFrom(target) || Boolean.TYPE == target) {
            return (T) (Boolean) castToBoolean(s);
        } else if (Character.class.isAssignableFrom(target) || Character.TYPE == target) {
            return (T) (Character) castToCharacter(s);
        } else if (BigInteger.class.isAssignableFrom(target)) {
            return (T) castToBigInteger(s);
        } else if (BigDecimal.class.isAssignableFrom(target)) {
            return (T) castToBigDecimal(s);
        } else if (Number.class.isAssignableFrom(target)) {
            return (T) castToNumber(s);
        } else if (String.class.isAssignableFrom(target)) {
            return (T) StringUtil.unescape(s);
        } else {
            throw new ClassCastException("Could not cast java.lang.String to " + target.getCanonicalName());
        }

    }

}
