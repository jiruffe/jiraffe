/*
 *    Copyright 2018 Chakilo
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
package com.chakilo.jiraffe.util;

/**
 * Util for <code>String</code>.
 *
 * @author Chakilo
 * 2019.02.13
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

            if ('\b' == c || '\t' == c || '\n' == c || '\u000B' == c || '\f' == c || '\r' == c || '\\' == c || '"' == c || (c >= '\0' && c <= '\7')) {
                sb.append('\\');
                sb.append(CharacterUtil.ESCAPE_CHARACTER[(int) c]);
            } else if (c < 32) {
                sb.append('\\');
                sb.append('u');
                sb.append('0');
                sb.append('0');
                sb.append(CharacterUtil.DIGIT_TO_CHAR[(c >>> 4) & 0xf]);
                sb.append(CharacterUtil.DIGIT_TO_CHAR[c & 0xf]);
            } else if (c >= 127) {
                sb.append('\\');
                sb.append('u');
                sb.append(CharacterUtil.DIGIT_TO_CHAR[(c >>> 12) & 0xf]);
                sb.append(CharacterUtil.DIGIT_TO_CHAR[(c >>> 8) & 0xf]);
                sb.append(CharacterUtil.DIGIT_TO_CHAR[(c >>> 4) & 0xf]);
                sb.append(CharacterUtil.DIGIT_TO_CHAR[c & 0xf]);
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
                    sb.append(CharacterUtil.UNESCAPE_CHARACTER[(int) c]);
                } else if ('x' == c) {
                    sb.append((char) (((CharacterUtil.CHAR_TO_DIGIT[(int) s.charAt(++i)]) << 4) + CharacterUtil.CHAR_TO_DIGIT[(int) s.charAt(++i)]));
                } else if ('u' == c) {
                    int d = 0;
                    for (int j = 0; j < 4; j++) {
                        d = (d << 4) + CharacterUtil.CHAR_TO_DIGIT[(int) s.charAt(++i)];
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

    public static boolean isNotEmpty(StringBuilder sb) {
        return sb.length() > 0;
    }

    public static boolean isEmpty(StringBuilder sb) {
        return !isNotEmpty(sb);
    }

    public static void clear(StringBuilder sb) {
        sb.setLength(0);
    }

}
