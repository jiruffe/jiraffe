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

package com.jiruffe.jiraffe.analyzer;

import com.jiruffe.jiraffe.util.*;

import javax.lang.model.type.NullType;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Java {@link Object} &lt;=&gt; JSON {@link String} conversion.
 *
 * @author Jiruffe
 * 2019.02.18
 */
public abstract class DirectAnalyzer {

    /**
     * Java {@link Object} =&gt; JSON {@link String}.
     *
     * @param o the {@link Object} to be converted.
     * @return the JSON {@link String} converted.
     */
    public static String analyze(Object o) {

        if (null == o) {
            return StringUtil.NULL;
        }

        StringBuilder sb = new StringBuilder();

        Class<?> cls = o.getClass();

        if (o instanceof String) {
            sb.append('"');
            sb.append((String) o);
            sb.append('"');
        } else if (o instanceof Number) {
            sb.append(StringUtil.toString(o));
        } else if (TypeUtil.isPrimitive(o)) {
            sb.append(StringUtil.toString(o));
        } else if (cls.isEnum()) {
            sb.append(StringUtil.toString(((Enum) o).ordinal()));
        } else if (cls.isArray()) {
            sb.append('[');
            int len = Array.getLength(o);
            for (int i = 0; i < len; i++) {
                sb.append(analyze(Array.get(o, i)));
                if (i != len - 1) {
                    sb.append(',');
                }
            }
            sb.append(']');
        } else if (o instanceof Iterable) {
            sb.append('[');
            Iterator iterator = ((Iterable) o).iterator();
            while (iterator.hasNext()) {
                sb.append(analyze(iterator.next()));
                if (iterator.hasNext()) {
                    sb.append(',');
                }
            }
            sb.append(']');
        } else if (o instanceof Enumeration) {
            sb.append('[');
            Enumeration enumeration = (Enumeration) o;
            while (enumeration.hasMoreElements()) {
                sb.append(analyze(enumeration.nextElement()));
                if (enumeration.hasMoreElements()) {
                    sb.append(',');
                }
            }
            sb.append(']');
        } else if (o instanceof Map) {
            sb.append('{');
            Map map = (Map) o;
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Object e = iterator.next();
                sb.append('"');
                sb.append(StringUtil.toString(((Map.Entry) e).getKey()));
                sb.append('"');
                sb.append(':');
                sb.append(analyze(((Map.Entry) e).getValue()));
                if (iterator.hasNext()) {
                    sb.append(',');
                }
            }
            sb.append('}');
        } else if (o instanceof Dictionary) {
            sb.append('{');
            Dictionary map = (Dictionary) o;
            Enumeration enumeration = map.keys();
            while (enumeration.hasMoreElements()) {
                Object k = enumeration.nextElement();
                sb.append('"');
                sb.append(StringUtil.toString(k));
                sb.append('"');
                sb.append(':');
                sb.append(analyze(map.get(k)));
                if (enumeration.hasMoreElements()) {
                    sb.append(',');
                }
            }
            sb.append('}');
        } else {
            sb.append('{');
            Iterator<Field> iterator = ObjectUtil.getFields(cls).iterator();
            while (iterator.hasNext()) {
                Field f = iterator.next();
                sb.append('"');
                sb.append(f.getName());
                sb.append('"');
                sb.append(':');
                try {
                    sb.append(analyze(f.get(o)));
                } catch (IllegalAccessException ignored) {

                }
                if (iterator.hasNext()) {
                    sb.append(',');
                }
            }
            sb.append('}');
        }

        return sb.toString();

    }

    /**
     * JSON {@link String} =&gt; Java {@link Object}
     *
     * @param json   the JSON {@link String} to be converted.
     * @param target the target {@link Type}.
     * @param <T>    the target {@link Type}.
     * @return the target Java {@link Object}.
     */
    public static <T> T analyze(String json, Type target) {

        if (null == json) {
            return null;
        }

        // ignore spaces
        String trimmed_json = json.trim();

        // ignore first bracket
        if (trimmed_json.startsWith("[") || trimmed_json.startsWith("{")) {
            trimmed_json = trimmed_json.substring(1);
        }

        return analyze(new StringReader(trimmed_json), target);

    }

    private static <T> T analyze(StringReader sr, Type target) {

        if (null == target || NullType.class == target || Object.class == target) {
            return null;
        }

        // target type
        Class<?> target_class = null;
        ParameterizedType parameterized_type = null;
        Type[] actual_type_arguments = null;
        Type k_type = null;
        Type v_type = null;

        if (target instanceof Class) {
            target_class = (Class) target;
        } else if (target instanceof ParameterizedType) {
            parameterized_type = (ParameterizedType) target;
            target_class = (Class) parameterized_type.getRawType();
            actual_type_arguments = parameterized_type.getActualTypeArguments();
        } else {
            return null;
        }

        Object rst = null;
        if (target_class.isArray()) {
            // use list instead of array to dynamically add sub-elements
            rst = Defaults.list();
            v_type = target_class.getComponentType();
        } else {
            try {
                rst = target_class.newInstance();
            } catch (InstantiationException | IllegalAccessException ignored) {
                if (Collection.class.isAssignableFrom(target_class)) {
                    v_type = actual_type_arguments[0];
                    // considering known derived classes
                    try {
                        if (List.class.isAssignableFrom(target_class)) {
                            rst = Defaults.list();
                        } else if (Set.class.isAssignableFrom(target_class)) {
                            if (EnumSet.class.isAssignableFrom(target_class)) {
                                rst = EnumSet.noneOf((Class<Enum>) v_type);
                            } else if (SortedSet.class.isAssignableFrom(target_class)) {
                                rst = Defaults.sortedSet();
                            } else {
                                rst = Defaults.set();
                            }
                        }
                    } catch (Exception ignored1) {

                    }
                } else if (Map.class.isAssignableFrom(target_class)) {
                    k_type = actual_type_arguments[0];
                    v_type = actual_type_arguments[1];
                    // considering known derived classes
                    try {
                        if (EnumMap.class.isAssignableFrom(target_class)) {
                            rst = new EnumMap((Class) k_type);
                        } else {
                            rst = Defaults.map();
                        }
                    } catch (Exception ignored1) {

                    }
                } else if (Dictionary.class.isAssignableFrom(target_class)) {
                    k_type = actual_type_arguments[0];
                    v_type = actual_type_arguments[1];
                    // considering known derived classes
                    try {
                        rst = Defaults.dictionary();
                    } catch (Exception ignored1) {

                    }
                } else {
                    // unknown interface/abstract class/class without default constructor
                    return null;
                }
            }
        }
        if (null == rst) {
            // instantiation failure
            return null;
        }

        // key
        String now_key = null;
        // value read
        StringBuilder sb = new StringBuilder();
        char c;
        int ci = 0;
        // the last token
        char last_token = CharacterUtil.NUL;

        // traversal of json string
        while (true) {

            try {
                if ((ci = sr.read()) == CharacterUtil.EOF) break;
            } catch (IOException ignored) {

            }

            c = (char) ci;

            if (CharacterUtil.isQuote(last_token)) {
                if (last_token == c) {
                    last_token = CharacterUtil.NUL;
                } else {
                    sb.append(c);
                }
                continue;
            }

            switch (c) {

                case '"':
                case '\'':
                    last_token = c;
                    break;

                case '{':
                case '[':
                    if (rst instanceof Collection) {
                        ((Collection) rst).add(analyze(sr, v_type));
                    } else if (rst instanceof Map) {
                        ((Map) rst).put(now_key, analyze(sr, v_type));
                    } else if (rst instanceof Dictionary) {
                        ((Dictionary) rst).put(now_key, analyze(sr, v_type));
                    } else {
                        Field f = null;
                        try {
                            f = target_class.getField(now_key);
                        } catch (NoSuchFieldException ignored) {

                        }
                        try {
                            f.set(rst, analyze(sr, f.getGenericType()));
                        } catch (IllegalAccessException ignored) {

                        }
                    }
                    last_token = c;
                    break;

                case ':':
                    now_key = sb.toString();
                    StringUtil.clear(sb);
                    break;

                case ',':
                    if (StringUtil.isNotEmpty(sb)) {
                        // set the value to this element
                        if (rst instanceof Collection) {
                            ((Collection) rst).add(TypeUtil.castFromString(sb.toString(), (Class) v_type));
                        } else if (rst instanceof Map) {
                            ((Map) rst).put(now_key, TypeUtil.castFromString(sb.toString(), (Class) v_type));
                        } else if (rst instanceof Dictionary) {
                            ((Dictionary) rst).put(now_key, TypeUtil.castFromString(sb.toString(), (Class) v_type));
                        } else {
                            Field f = null;
                            try {
                                f = target_class.getField(now_key);
                                f.set(rst, TypeUtil.castFromString(sb.toString(), f.getType()));
                            } catch (NoSuchFieldException | IllegalAccessException ignored) {

                            }
                        }
                        StringUtil.clear(sb);
                    }
                    last_token = c;
                    break;

                case '}':
                    // set the last value
                    if (StringUtil.isNotEmpty(sb)) {
                        // set the value to this element
                        if (rst instanceof Map) {
                            ((Map) rst).put(now_key, TypeUtil.castFromString(sb.toString(), (Class) v_type));
                        } else if (rst instanceof Dictionary) {
                            ((Dictionary) rst).put(now_key, TypeUtil.castFromString(sb.toString(), (Class) v_type));
                        } else {
                            Field f = null;
                            try {
                                f = target_class.getField(now_key);
                                f.set(rst, TypeUtil.castFromString(sb.toString(), f.getType()));
                            } catch (NoSuchFieldException | IllegalAccessException ignored) {

                            }

                        }
                        StringUtil.clear(sb);
                    }
                    return (T) rst;

                case ']':
                    if (StringUtil.isNotEmpty(sb)) {
                        // set the value to this element
                        if (rst instanceof Collection) {
                            ((Collection) rst).add(TypeUtil.castFromString(sb.toString(), (Class) v_type));
                        }
                        StringUtil.clear(sb);
                    }
                    if (target_class.isArray()) {
                        // list to array
                        List lst = (List) rst;
                        int len = lst.size();
                        Object array = Array.newInstance((Class) v_type, len);
                        for (int i = 0; i < len; i++) {
                            Array.set(array, i, lst.get(i));
                        }
                        return (T) array;
                    } else {
                        return (T) rst;
                    }

                default:
                    if (StringUtil.isNotEmpty(sb)) {
                        last_token = CharacterUtil.NUL;
                        sb.append(c);
                    } else if (CharacterUtil.isVisibleAndNotSpace(c)) {
                        last_token = CharacterUtil.NUL;
                        sb.append(c);
                    }
                    break;

            }

        }

        return null;

    }

}
