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

    public static String escape(String s) {
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < s.length(); i++) {
            
            char c = s.charAt(i);
            
            if ('\b' == c || '\t' == c || '\n' == c || '\u000B' == c || '\f' == c || '\r' == c || '\\' == c || '/' == c || '"' == c || (c >= '\0' && c <= '\7')) {
                sb.append('\\');
                sb.append(CharacterUtil.CHARS_MARK[(int) c]);
            } else if (c < 32) {
                sb.append('\\');
                sb.append('u');
                sb.append('0');
                sb.append('0');
                sb.append(CharacterUtil.DIGITS[(c >>> 4) & 15]);
                sb.append(CharacterUtil.DIGITS[c & 15]);
            } else if (c >= 127) {
                sb.append('\\');
                sb.append('u');
                sb.append(CharacterUtil.DIGITS[(c >>> 12) & 15]);
                sb.append(CharacterUtil.DIGITS[(c >>> 8) & 15]);
                sb.append(CharacterUtil.DIGITS[(c >>> 4) & 15]);
                sb.append(CharacterUtil.DIGITS[c & 15]);
            } else {
                sb.append(c);
            }
            
        }
        
        return sb.toString();
        
    }

    public static String unescape(String s) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if ('\\' == c) {

                c = s.charAt(++i);

                if ('b' == c || 't' == c || 'n' == c || 'v' == c || 'f' == c || 'r' == c || '\\' == c || '/' == c || '"' == c || '\'' == c || (c >= '0' && c <= '7')) {
                    sb.append(CharacterUtil.CHARS_MARK_REV[(int) c]);
                } else if ('x' == c) {
                    sb.append((char) (((CharacterUtil.DIGITS_MARK[(int) s.charAt(++i)]) << 4) + CharacterUtil.DIGITS_MARK[(int) s.charAt(++i)]));
                } else if ('u' == c) {
                    int d = 0;
                    for (int j = 0; j < 4; j++) {
                        d = (d << 4) + CharacterUtil.DIGITS_MARK[(int) s.charAt(++i)];
                    }
                    sb.append((char) d);
                } else {
                    sb.append('\\');
                    sb.append(c);
                }

            } else {
                sb.append(c);
            }

        }

        return sb.toString();

    }

    public static String toString(Object v) {
        return String.valueOf(v);
    }

}
