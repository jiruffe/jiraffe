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
package com.chakilo.jiraffe.analyzer;

import com.chakilo.jiraffe.model.JSONElement;
import com.chakilo.jiraffe.util.CharacterUtil;
import com.chakilo.jiraffe.util.StringUtil;
import com.chakilo.jiraffe.util.TypeUtil;

import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;

/**
 * 2018.10.25
 *
 * string <=> <code>JSONElement</code> conversion.
 *
 * @author Chakilo
 */
public abstract class StringAnalyzer {

    /**
     * string => <code>JSONElement</code>.
     *
     * @param json the json string.
     * @return converted <code>JSONElement</code>.
     * @throws Exception if error occurred while reading json string.
     */
    public static JSONElement analyze(String json) throws Exception {

        // ignore spaces
        json = json.trim();

        // not object or list, return a value
        if (!json.startsWith("{") && !json.startsWith("[")) {
            boolean force_set_string = false;
            if ((json.startsWith("\"") && json.endsWith("\"")) || (json.startsWith("'") && json.endsWith("'"))) {
                force_set_string = true;
                json = json.substring(1, json.length() - 1);
            }
            return parseValue(json, force_set_string);
        }

        // stack to store upper element
        Queue<JSONElement> bases = Collections.asLifoQueue(new ArrayDeque<>());
        // stack to store keys
        Queue<String> keys = Collections.asLifoQueue(new ArrayDeque<>());
        // value read
        StringBuilder sb = new StringBuilder();
        // reader
        StringReader sr = new StringReader(json);
        char c;
        int ci;
        // should set value as string
        boolean force_set_string = false;
        // the last token
        char last_token = 0;

        // traversal of json string
        while ((ci = sr.read()) != -1) {

            c = (char) ci;

            if (CharacterUtil.isQuote(last_token)) {
                if (last_token == c) {
                    last_token = 0;
                } else {
                    sb.append(c);
                }
                continue;
            }

            switch (c) {

                case '"':
                case '\'':
                    force_set_string = true;
                    last_token = c;
                    break;

                case '{':
                    bases.offer(JSONElement.newObject());
                    last_token = c;
                    break;

                case '[':
                    bases.offer(JSONElement.newList());
                    last_token = c;
                    break;

                case ':':
                    // new key
                    keys.offer(StringUtil.unescape(sb.toString()));
                    sb.setLength(0);
                    last_token = c;
                    break;

                case ',':
                    if (sb.length() > 0 || force_set_string) {
                        // set the value to this element
                        JSONElement self = bases.peek();
                        assert null != self;
                        if (self.isList()) {
                            self.offer(parseValue(sb.toString(), force_set_string));
                        } else if (self.isObject()) {
                            self.offer(keys.poll(), parseValue(sb.toString(), force_set_string));
                        }
                        sb.setLength(0);
                        force_set_string = false;
                    } else if (!CharacterUtil.isRightBrackets(last_token)) {
                        if (!bases.isEmpty()) {
                            // set void
                            JSONElement self = bases.peek();
                            if (self.isList()) {
                                self.offer(JSONElement.Void());
                            } else if (self.isObject()) {
                                self.offer(keys.poll(), JSONElement.Void());
                            }
                        }
                    }
                    last_token = c;
                    break;

                case '}':
                    // current object
                    JSONElement self_object = bases.poll();
                    assert null != self_object;
                    assert self_object.isObject();
                    // set the last value
                    if (sb.length() > 0 || force_set_string) {
                        assert !keys.isEmpty();
                        self_object.offer(keys.poll(), parseValue(sb.toString(), force_set_string));
                        sb.setLength(0);
                        force_set_string = false;
                    } else if (CharacterUtil.isColon(last_token)) {
                        self_object.offer(keys.poll(), JSONElement.Void());
                    }
                    // if there was no upper element, conversion is finished, ignore extra chars
                    if (isSelfTheFinalElement(bases, keys, self_object)) return self_object;
                    last_token = c;
                    break;

                case ']':
                    // current list
                    JSONElement self_list = bases.poll();
                    assert null != self_list;
                    assert self_list.isList();
                    // set the last value
                    if (sb.length() > 0 || force_set_string) {
                        self_list.offer(parseValue(sb.toString(), force_set_string));
                        sb.setLength(0);
                        force_set_string = false;
                    } else if (CharacterUtil.isComma(last_token)) {
                        self_list.offer(JSONElement.Void());
                    }
                    // if there was no upper element, conversion is finished, ignore extra chars
                    if (isSelfTheFinalElement(bases, keys, self_list)) return self_list;
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

        // upper element exists, set self to it
        if (!bases.isEmpty()) {
            JSONElement base = bases.peek();
            assert null != base;
            assert !base.isValue();
            if (base.isList()) {
                base.offer(self);
            } else if (base.isObject()) {
                assert !keys.isEmpty();
                base.offer(keys.poll(), self);
            }
            return false;
        } else {
            // no upper elements
            return true;
        }

    }

    private static JSONElement parseValue(String s, boolean force_set_string) {

        if (force_set_string) {
            return JSONElement.newValue(StringUtil.unescape(s));
        } else if ("true".equalsIgnoreCase(s)) {
            return JSONElement.newValue(Boolean.TRUE);
        } else if ("false".equalsIgnoreCase(s)) {
            return JSONElement.newValue(Boolean.FALSE);
        } else if ("null".equalsIgnoreCase(s)) {
            return JSONElement.Void();
        } else if ("NaN".equals(s)) {
            return JSONElement.Void();
        } else if ("undefined".equalsIgnoreCase(s)) {
            return JSONElement.Void();
        } else if (TypeUtil.couldCastToNumber(s)) {
            return JSONElement.newValue(TypeUtil.castToNumber(s));
        } else {
            return JSONElement.newValue(StringUtil.unescape(s));
        }

    }

    /**
     * <code>JSONElement</code> => string.
     *
     * @param element the element to be converted.
     * @return a json string converted.
     * @throws Exception if error occurred analyzing element.
     */
    public static String analyze(JSONElement element) throws Exception {

        StringBuilder sb = new StringBuilder();

        switch (element.getType()) {

            case LIST:
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
                Iterator<Object> iterator1 = element.keys().iterator();
                while (iterator1.hasNext()) {
                    Object k = iterator1.next();
                    sb.append('"');
                    sb.append(StringUtil.escape(StringUtil.toString(k)));
                    sb.append('"');
                    sb.append(':');
                    sb.append(analyze(element.peek(k)));
                    if (iterator1.hasNext()) {
                        sb.append(',');
                    }
                }
                sb.append('}');
                break;

            case VALUE:
                Object v = element.asValue();
                if (v instanceof String) {
                    sb.append('"');
                    sb.append(StringUtil.escape((String) v));
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
