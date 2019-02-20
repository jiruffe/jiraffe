package com.chakilo.jiraffe.analyzer;

import com.chakilo.jiraffe.model.JSONArray;
import com.chakilo.jiraffe.model.JSONObject;
import com.chakilo.jiraffe.model.JSONValue;
import com.chakilo.jiraffe.model.base.JSONElement;
import com.chakilo.jiraffe.util.CharacterUtil;
import com.chakilo.jiraffe.util.StringUtil;
import com.chakilo.jiraffe.util.TypeUtil;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * 2018.10.25
 *
 * string <=> JSONElement 互转
 *
 * @author Chakilo
 */
public abstract class StringAnalyzer {

    public static JSONElement analyze(String json) throws Exception {

        // 忽略前后空白
        json = json.trim();

        // 不是{}/[], 就当作普通值
        if (!json.startsWith("{") && !json.startsWith("[")) {
            boolean force_set_string = false;
            if ((json.startsWith("\"") && json.endsWith("\"")) || (json.startsWith("'") && json.endsWith("'"))) {
                force_set_string = true;
                json = json.substring(1, json.length() - 1);
            }
            return parseValue(json, force_set_string);
        }

        // 存放上级对象/数组的栈
        Queue<JSONElement> bases = Collections.asLifoQueue(new ArrayDeque<>());
        // 存放键名的栈
        Queue<String> keys = Collections.asLifoQueue(new ArrayDeque<>());
        // 当前读到的元素
        StringBuilder sb = new StringBuilder();
        // 当前读取的字符
        StringReader sr = new StringReader(json);
        char c;
        int ci;
        // 强制视作字符串
        boolean force_set_string = false;
        // 上一个关键字
        char last_token = 0;

        // 遍历字符
        while ((ci = sr.read()) != -1) {

            c = (char) ci;

            switch (c) {

                case '"':
                case '\'':
                    readString(sr, sb, c);
                    force_set_string = true;
                    last_token = c;
                    break;

                case '{':
                    bases.offer(new JSONObject());
                    last_token = c;
                    break;

                case '[':
                    bases.offer(new JSONArray());
                    last_token = c;
                    break;

                case ':':
                    // 新的键名
                    keys.offer(sb.toString());
                    sb.setLength(0);
                    last_token = c;
                    break;

                case ',':
                    if (sb.length() > 0) {
                        // 上一个值加到现在的元素内
                        JSONElement self = bases.peek();
                        assert null != self;
                        if (self.isArray()) {
                            self.offer(parseValue(sb.toString(), force_set_string));
                        } else if (self.isObject()) {
                            self.offer(keys.poll(), parseValue(sb.toString(), force_set_string));
                        }
                        sb.setLength(0);
                    } else if (!CharacterUtil.isRightBrackets(last_token)) {
                        if (!bases.isEmpty()) {
                            // 加入一个空值
                            JSONElement self = bases.peek();
                            if (self.isArray()) {
                                self.offer(JSONElement.VOID);
                            } else if (self.isObject()) {
                                self.offer(keys.poll(), JSONElement.VOID);
                            }
                        }
                    }
                    last_token = c;
                    break;

                case '}':
                    // 这是它自己
                    JSONElement self_object = bases.poll();
                    assert null != self_object;
                    assert self_object.isObject();
                    // 把最后一个元素加进去
                    if (sb.length() > 0) {
                        assert !keys.isEmpty();
                        self_object.offer(keys.poll(), parseValue(sb.toString(), force_set_string));
                        sb.setLength(0);
                    } else if (CharacterUtil.isColon(last_token)) {
                        self_object.offer(keys.poll(), JSONElement.VOID);
                    }
                    // 如果没有父级, 已经解析结束, 若是还有剩余的, 都是多余字符, 忽略即可
                    if (isSelfTheFinalElement(bases, keys, self_object)) return self_object;
                    last_token = c;
                    break;

                case ']':
                    // 这是它自己
                    JSONElement self_array = bases.poll();
                    assert null != self_array;
                    assert self_array.isArray();
                    // 把最后一个元素加进去
                    if (sb.length() > 0) {
                        self_array.offer(parseValue(sb.toString(), force_set_string));
                        sb.setLength(0);
                    } else if (CharacterUtil.isComma(last_token)) {
                        self_array.offer(JSONElement.VOID);
                    }
                    // 如果没有父级, 已经解析结束, 若是还有剩余的, 都是多余字符, 忽略即可
                    if (isSelfTheFinalElement(bases, keys, self_array)) return self_array;
                    last_token = c;
                    break;

                default:
                    if (sb.length() > 0) {
                        last_token = 0;
                        sb.append(c);
                    } else if (CharacterUtil.isVisibleAndNotSpace(c)) {
                        last_token = 0;
                        force_set_string = false;
                        sb.append(c);
                    }
                    break;

            }

        }

        return null;

    }

    private static boolean isSelfTheFinalElement(Queue<JSONElement> bases, Queue<String> keys, JSONElement self) throws Exception {
        // 还有上一级, 那就把自身加到上一级去
        if (!bases.isEmpty()) {
            JSONElement base = bases.peek();
            assert null != base;
            assert !base.isValue();
            if (base.isArray()) {
                base.offer(self);
            } else if (base.isObject()) {
                assert !keys.isEmpty();
                base.offer(keys.poll(), self);
            }
            return false;
        } else {
            // 没有上一级了
            return true;
        }
    }

    private static JSONElement parseValue(String s, boolean force_set_string) {

        if (force_set_string) {
            return new JSONValue(s);
        } else if ("true".equalsIgnoreCase(s)) {
            return new JSONValue(Boolean.TRUE);
        } else if ("false".equalsIgnoreCase(s)) {
            return new JSONValue(Boolean.FALSE);
        } else if ("null".equalsIgnoreCase(s)) {
            return JSONElement.VOID;
        } else if ("NaN".equals(s)) {
            return JSONElement.VOID;
        } else if ("undefined".equalsIgnoreCase(s)) {
            return JSONElement.VOID;
        } else if (TypeUtil.couldCastToNumber(s)) {
            return new JSONValue(TypeUtil.castToNumber(s));
        } else {
            return new JSONValue(s);
        }

    }

    private static void readString(StringReader sr, StringBuilder sb, char quote) throws IOException {
        char c;
        int ci;
        while ((ci = sr.read()) != -1) {
            c = (char) ci;
            if (quote == c) {
                break;
            } else if ('\\' == c) {
                ci = sr.read();
                assert -1 != ci;
                c = (char) ci;
                if ('b' == c || 't' == c || 'n' == c || 'v' == c || 'f' == c || 'r' == c || '\\' == c || '/' == c || '"' == c || '\'' == c || (c >= '0' && c <= '7')) {
                    sb.append(CharacterUtil.CHARS_MARK_REV[(int) c]);
                } else if ('x' == c) {
                    int d = 0;
                    ci = sr.read();
                    assert -1 != ci;
                    d = CharacterUtil.DIGITS_MARK[ci];
                    ci = sr.read();
                    assert -1 != ci;
                    d = d << 4 + CharacterUtil.DIGITS_MARK[ci];
                    sb.append((char) d);
                } else if ('u' == c) {
                    int d = 0;
                    ci = sr.read();
                    assert -1 != ci;
                    d = CharacterUtil.DIGITS_MARK[ci];
                    ci = sr.read();
                    assert -1 != ci;
                    d = d << 4 + CharacterUtil.DIGITS_MARK[ci];
                    ci = sr.read();
                    assert -1 != ci;
                    d = d << 4 + CharacterUtil.DIGITS_MARK[ci];
                    ci = sr.read();
                    assert -1 != ci;
                    d = d << 4 + CharacterUtil.DIGITS_MARK[ci];
                    sb.append((char) d);
                } else {
                    sb.append('\\');
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
        }
    }

    public static String analyze(JSONElement element) throws Exception {

        StringBuilder sb = new StringBuilder();

        switch (element.getType()) {

            case ARRAY:
                sb.append('[');
                Iterator<JSONElement> iterator = element.iterator();
                while (iterator.hasNext()) {
                    sb.append(analyze(iterator.next()));
                    if (iterator.hasNext()) {
                        sb.append(',');
                    }
                }
                sb.append(']');
                break;

            case OBJECT:
                sb.append('{');
                Set<Object> ks = element.keys();
                if (null != ks) {
                    Iterator<Object> iterator1 = ks.iterator();
                    while (iterator1.hasNext()) {
                        Object k = iterator1.next();
                        sb.append('"');
                        sb.append(StringUtil.toString(k));
                        sb.append('"');
                        sb.append(':');
                        sb.append(analyze(element.peek(k)));
                        if (iterator1.hasNext()) {
                            sb.append(',');
                        }
                    }
                    sb.append('}');
                }
                break;

            case VALUE:
                Object v = element.getValue();
                if (v instanceof String) {
                    sb.append('"');
                    sb.append((String) v);
                    sb.append('"');
                } else {
                    sb.append(StringUtil.toString(v));
                }
                break;

            case VOID:
                sb.append("null");
                break;

            default:
                break;

        }

        return sb.toString();

    }

}
