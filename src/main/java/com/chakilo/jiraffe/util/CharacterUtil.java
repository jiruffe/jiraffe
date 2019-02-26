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
 * 2019.02.18
 *
 * @author Chakilo
 */
public abstract class CharacterUtil {

    public final static int EOF = -1;
    public final static char NUL = 0;
    public final static char[] DIGIT_TO_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public final static int[] CHAR_TO_DIGIT = new int[(int) 'f' + 1];
    public final static char[] ESCAPE_CHARACTER = new char[93];
    public final static char[] UNESCAPE_CHARACTER = new char[120];

    static {

        for (int i = '0'; i <= '9'; ++i) {
            CHAR_TO_DIGIT[i] = i - '0';
        }

        for (int i = 'a'; i <= 'f'; ++i) {
            CHAR_TO_DIGIT[i] = (i - 'a') + 10;
        }
        for (int i = 'A'; i <= 'F'; ++i) {
            CHAR_TO_DIGIT[i] = (i - 'A') + 10;
        }

        ESCAPE_CHARACTER['\0'] = '0';
        ESCAPE_CHARACTER['\1'] = '1';
        ESCAPE_CHARACTER['\2'] = '2';
        ESCAPE_CHARACTER['\3'] = '3';
        ESCAPE_CHARACTER['\4'] = '4';
        ESCAPE_CHARACTER['\5'] = '5';
        ESCAPE_CHARACTER['\6'] = '6';
        ESCAPE_CHARACTER['\7'] = '7';
        ESCAPE_CHARACTER['\b'] = 'b'; // 8
        ESCAPE_CHARACTER['\t'] = 't'; // 9
        ESCAPE_CHARACTER['\n'] = 'n'; // 10
        ESCAPE_CHARACTER['\u000B'] = 'v'; // 11
        ESCAPE_CHARACTER['\f'] = 'f'; // 12
        ESCAPE_CHARACTER['\r'] = 'r'; // 13
        ESCAPE_CHARACTER['\"'] = '"'; // 34
        ESCAPE_CHARACTER['\''] = '\''; // 39
        ESCAPE_CHARACTER['/'] = '/'; // 47
        ESCAPE_CHARACTER['\\'] = '\\'; // 92

        UNESCAPE_CHARACTER['0'] = '\0'; //48
        UNESCAPE_CHARACTER['1'] = '\1';
        UNESCAPE_CHARACTER['2'] = '\2';
        UNESCAPE_CHARACTER['3'] = '\3';
        UNESCAPE_CHARACTER['4'] = '\4';
        UNESCAPE_CHARACTER['5'] = '\5';
        UNESCAPE_CHARACTER['6'] = '\6';
        UNESCAPE_CHARACTER['7'] = '\7'; //55
        UNESCAPE_CHARACTER['b'] = '\b'; // 98
        UNESCAPE_CHARACTER['t'] = '\t'; // 116
        UNESCAPE_CHARACTER['n'] = '\n'; // 110
        UNESCAPE_CHARACTER['v'] = '\u000B'; // 76
        UNESCAPE_CHARACTER['f'] = '\f'; // 102
        UNESCAPE_CHARACTER['r'] = '\r'; // 114
        UNESCAPE_CHARACTER['"'] = '\"'; // 34
        UNESCAPE_CHARACTER['\''] = '\''; // 39
        UNESCAPE_CHARACTER['/'] = '/'; // 47
        UNESCAPE_CHARACTER['\\'] = '\\'; // 92

    }

    public static boolean isVisible(char c) {
        return !isInvisible(c);
    }

    public static boolean isVisibleAndNotSpace(char c) {
        return !isInvisibleOrSpace(c);
    }

    public static boolean isInvisible(char c) {
        return c < 32 || c == 127;
    }

    public static boolean isInvisibleOrSpace(char c) {
        return c <= 32 || c == 127;
    }

    public static boolean isLeftBrackets(char c) {
        return '[' == c || '{' == c;
    }

    public static boolean isRightBrackets(char c) {
        return ']' == c || '}' == c;
    }

    public static boolean isBrackets(char c) {
        return isLeftBrackets(c) || isRightBrackets(c);
    }

    public static boolean isComma(char c) {
        return ',' == c;
    }

    public static boolean isColon(char c) {
        return ':' == c;
    }

    public static boolean isQuote(char c) {
        return '\'' == c || '"' == c;
    }

}
