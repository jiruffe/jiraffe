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
import com.chakilo.jiraffe.util.*;

import javax.lang.model.type.NullType;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * {@link Object} &lt;=&gt; JSON {@link String},
 * not using {@link JSONElement} during conversion.
 *
 * @author Chakilo
 * 2019.02.18
 */
public abstract class DirectAnalyzer {

    /**
     * Java {@link Object} =&gt; JSON {@link String}.
     *
     * @param o the {@link Object} to be converted.
     * @return the JSON {@link String} converted.
     * @throws Exception if error occurred while analyzing {@link Object}.
     */
    public static String analyze(Object o) throws Exception {

        if (null == o) {
            return StringUtil.NULL;
        }

        if (o instanceof JSONElement) {
            return StringAnalyzer.analyze((JSONElement) o);
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
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Object k = iterator.next();
                sb.append('"');
                sb.append(StringUtil.toString(k));
                sb.append('"');
                sb.append(':');
                sb.append(analyze(map.get(k)));
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
                sb.append(analyze(f.get(o)));
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
     * @throws Exception if error occurred while analyzing {@link Type}.
     */
    public static <T> T analyze(String json, Type target) throws Exception {

        if (null == json || null == target || NullType.class == target || Object.class == target) {
            return null;
        }

        Class<?> target_class = null;
        ParameterizedType parameterized_type = null;
        Type[] actual_type_arguments = null;

        if (target instanceof Class) {
            target_class = (Class) target;
        } else if (target instanceof ParameterizedType) {
            parameterized_type = (ParameterizedType) target;
            target_class = (Class) parameterized_type.getRawType();
            actual_type_arguments = parameterized_type.getActualTypeArguments();
        } else {
            throw new InstantiationException("Could not create " + target.getTypeName());
        }

        // stack to store upper type
        Queue<Type> types = Collections.asLifoQueue(new ArrayDeque<>());
        // stack to store upper object
        Queue<Object> bases = Collections.asLifoQueue(new ArrayDeque<>());
        // stack to store keys
        Queue<String> keys = Collections.asLifoQueue(new ArrayDeque<>());
        // value read
        StringBuilder sb = new StringBuilder();
        // reader
        StringReader sr = new StringReader(json);
        char c;
        int ci;
        // the last token
        char last_token = CharacterUtil.NUL;

        // traversal of json string
        while ((ci = sr.read()) != CharacterUtil.EOF) {

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
                    if (Map.class.isAssignableFrom(target_class)) {
                        Type k_type = actual_type_arguments[0];
                        Type v_type = actual_type_arguments[1];
                        Map map = null;
                        try {
                            // using the default constructor first
                            map = (Map) target_class.newInstance();
                        } catch (InstantiationException | IllegalAccessException e) {
                            // considering known derived classes
                            try {
                                if (EnumMap.class.isAssignableFrom(target_class)) {
                                    map = new EnumMap((Class) k_type);
                                } else {
                                    map = Defaults.map();
                                }
                            } catch (Exception ignored) {

                            }
                        }
                        // construction failure
                        if (null == map) {
                            throw new InstantiationException("Could not create " + target.getTypeName());
                        }
                        bases.offer(map);
                        types.offer(target);
                        target = v_type;
                        if (target instanceof Class) {
                            target_class = (Class) target;
                        } else if (target instanceof ParameterizedType) {
                            parameterized_type = (ParameterizedType) target;
                            target_class = (Class) parameterized_type.getRawType();
                            actual_type_arguments = parameterized_type.getActualTypeArguments();
                        } else {
                            throw new InstantiationException("Could not create " + target.getTypeName());
                        }
                    } else if (Dictionary.class.isAssignableFrom(target_class)) {
                        Type k_type = actual_type_arguments[0];
                        Type v_type = actual_type_arguments[1];
                        Dictionary dictionary = null;
                        try {
                            // using the default constructor first
                            dictionary = (Dictionary) target_class.newInstance();
                        } catch (InstantiationException | IllegalAccessException e) {
                            // considering known derived classes
                            try {
                                dictionary = Defaults.dictionary();
                            } catch (Exception ignored) {

                            }
                        }
                        // construction failure
                        if (null == dictionary) {
                            throw new InstantiationException("Could not create " + target.getTypeName());
                        }
                        bases.offer(dictionary);
                        types.offer(target);
                        target = v_type;
                        if (target instanceof Class) {
                            target_class = (Class) target;
                        } else if (target instanceof ParameterizedType) {
                            parameterized_type = (ParameterizedType) target;
                            target_class = (Class) parameterized_type.getRawType();
                            actual_type_arguments = parameterized_type.getActualTypeArguments();
                        } else {
                            throw new InstantiationException("Could not create " + target.getTypeName());
                        }
                    } else {
                        bases.offer(target_class.newInstance());
                        types.offer(target);
                    }
                    last_token = c;
                    break;

                case '[':
                    if (target_class.isArray()) {
                        // size unknown, use list instead
                        bases.offer(Defaults.list());
                        types.offer(target);
                        target_class = target_class.getComponentType();
                        target = target_class;
                    } else if (Collection.class.isAssignableFrom(target_class)) {
                        Type v_type = actual_type_arguments[0];
                        Collection collection = null;
                        try {
                            // using the default constructor first
                            collection = (Collection) target_class.newInstance();
                        } catch (InstantiationException | IllegalAccessException e) {
                            // considering known derived classes
                            try {
                                if (List.class.isAssignableFrom(target_class)) {
                                    collection = Defaults.list();
                                } else if (Set.class.isAssignableFrom(target_class)) {
                                    if (EnumSet.class.isAssignableFrom(target_class)) {
                                        collection = EnumSet.noneOf((Class<Enum>) v_type);
                                    } else if (SortedSet.class.isAssignableFrom(target_class)) {
                                        collection = Defaults.sortedSet();
                                    } else {
                                        collection = Defaults.set();
                                    }
                                }
                            } catch (Exception ignored) {

                            }
                        }
                        // construction failure
                        if (null == collection) {
                            throw new InstantiationException("Could not create " + target.getTypeName());
                        }
                        bases.offer(collection);
                        types.offer(target);
                        target = v_type;
                        if (target instanceof Class) {
                            target_class = (Class) target;
                        } else if (target instanceof ParameterizedType) {
                            parameterized_type = (ParameterizedType) target;
                            target_class = (Class) parameterized_type.getRawType();
                            actual_type_arguments = parameterized_type.getActualTypeArguments();
                        } else {
                            throw new InstantiationException("Could not create " + target.getTypeName());
                        }
                    } else {
                        throw new ClassCastException("Expect " + target.getTypeName() + " but found syntax '['.");
                    }
                    last_token = c;
                    break;

                case ':':
                    // new key
                    String nk = sb.toString();
                    StringUtil.clear(sb);
                    keys.offer(StringUtil.unescape(nk));
                    Object sf = bases.peek();
                    if (!sf.getClass().isArray() && !(sf instanceof Collection) && !(sf instanceof Map) && !(sf instanceof Dictionary)) {
                        Field f = sf.getClass().getField(nk);
                        types.offer(target);
                        target_class = f.getType();
                        target = target_class;
                    }
                    last_token = c;
                    break;

                case ',':
                    if (StringUtil.isNotEmpty(sb)) {
                        // set the value to this element
                        Object self = bases.peek();
                        if (self.getClass().isArray()) {
                            // won't happen
                        } else if (self instanceof Collection) {
                            ((Collection) self).add(TypeUtil.castFromString(sb.toString(), target_class));
                        } else if (self instanceof Map) {
                            ((Map) self).put(keys.poll(), TypeUtil.castFromString(sb.toString(), target_class));
                        } else if (self instanceof Dictionary) {
                            ((Dictionary) self).put(keys.poll(), TypeUtil.castFromString(sb.toString(), target_class));
                        } else {
                            if (!CharacterUtil.isRightBrackets(last_token)) {
                                String k = keys.poll();
                                Field f = self.getClass().getField(k);
                                f.set(self, TypeUtil.castFromString(sb.toString(), target_class));
                                target = types.poll();
                                if (target instanceof Class) {
                                    target_class = (Class) target;
                                } else if (target instanceof ParameterizedType) {
                                    parameterized_type = (ParameterizedType) target;
                                    target_class = (Class) parameterized_type.getRawType();
                                    actual_type_arguments = parameterized_type.getActualTypeArguments();
                                } else {
                                    throw new InstantiationException("Could not create " + target.getTypeName());
                                }
                            }
                        }
                        StringUtil.clear(sb);
                    } else if (!CharacterUtil.isRightBrackets(last_token)) {
                        if (!bases.isEmpty()) {
                            Object self = bases.peek();
                            if (!self.getClass().isArray() && !(self instanceof Collection)) {
                                keys.poll();
                            }
                        }
                    }
                    last_token = c;
                    break;

                case '}':
                    // current object
                    Object current_object = bases.poll();
                    // set the last value
                    if (StringUtil.isNotEmpty(sb)) {
                        String v = sb.toString();
                        if (current_object instanceof Map) {
                            ((Map) current_object).put(keys.poll(), TypeUtil.castFromString(v, target_class));
                        } else if (current_object instanceof Dictionary) {
                            ((Dictionary) current_object).put(keys.poll(), TypeUtil.castFromString(v, target_class));
                        } else {
                            String k = keys.poll();
                            Field f = current_object.getClass().getField(k);
                            f.set(current_object, TypeUtil.castFromString(v, target_class));
                        }
                        StringUtil.clear(sb);
                    } else if (!CharacterUtil.isRightBrackets(last_token)) {
                        if (!bases.isEmpty()) {
                            Object self = bases.peek();
                            if (!self.getClass().isArray() && !(self instanceof Collection)) {
                                keys.poll();
                            }
                        }
                    }
                    target = types.poll();
                    if (target instanceof Class) {
                        target_class = (Class) target;
                    } else if (target instanceof ParameterizedType) {
                        parameterized_type = (ParameterizedType) target;
                        target_class = (Class) parameterized_type.getRawType();
                        actual_type_arguments = parameterized_type.getActualTypeArguments();
                    } else {

                    }
                    if (bases.isEmpty()) {
                        return (T) current_object;
                    } else {
                        Object base = bases.peek();
                        if (base.getClass().isArray()) {
                            // won't happen
                        } else if (base instanceof Collection) {
                            ((Collection) base).add(current_object);
                        } else if (base instanceof Map) {
                            ((Map) base).put(keys.poll(), current_object);
                        } else if (base instanceof Dictionary) {
                            ((Dictionary) base).put(keys.poll(), current_object);
                        } else {
                            String k = keys.poll();
                            Field f = base.getClass().getField(k);
                            f.set(base, current_object);
                        }
                    }
                    last_token = c;
                    break;

                case ']':
                    // current array
                    Object current_array = bases.poll();
                    // set the last value
                    if (StringUtil.isNotEmpty(sb)) {
                        String v = sb.toString();
                        if (current_array.getClass().isArray()) {
                            // won't happen
                        } else if (current_array instanceof Collection) {
                            ((Collection) current_array).add(TypeUtil.castFromString(v, target_class));
                        } else {
                            throw new ClassCastException("Expect " + target.getTypeName() + " but found syntax ']'.");
                        }
                    }
                    target = types.poll();
                    if (target instanceof Class) {
                        target_class = (Class) target;
                    } else if (target instanceof ParameterizedType) {
                        parameterized_type = (ParameterizedType) target;
                        target_class = (Class) parameterized_type.getRawType();
                        actual_type_arguments = parameterized_type.getActualTypeArguments();
                    } else {
                        Object base = bases.peek();
                        if (base.getClass().isArray()) {
                            // won't happen
                        } else if (base instanceof Collection) {
                            ((Collection) base).add(current_array);
                        } else if (base instanceof Map) {
                            ((Map) base).put(keys.poll(), current_array);
                        } else if (base instanceof Dictionary) {
                            ((Dictionary) base).put(keys.poll(), current_array);
                        } else {
                            String k = keys.poll();
                            Field f = base.getClass().getField(k);
                            if (f.getType().isArray()) {
                                f.set(base, ((Collection) current_array).toArray());
                            } else {
                                f.set(base, current_array);
                            }
                        }
                    }
                    if (bases.isEmpty()) {
                        return (T) current_array;
                    } else {

                    }
                    last_token = c;
                    break;

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
