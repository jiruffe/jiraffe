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
import com.chakilo.jiraffe.util.Defaults;
import com.chakilo.jiraffe.util.ObjectUtil;
import com.chakilo.jiraffe.util.StringUtil;
import com.chakilo.jiraffe.util.TypeUtil;

import javax.lang.model.type.NullType;
import java.lang.reflect.*;
import java.util.*;

/**
 * Java {@link Object} &lt;=&gt; {@link JSONElement} conversion.
 *
 * @author Chakilo
 * 2019.02.18
 */
public abstract class ObjectAnalyzer {

    /**
     * Java {@link Object} =&gt; {@link JSONElement}.
     *
     * @param o the {@link Object} to be converted.
     * @return the {@link JSONElement} converted.
     */
    public static JSONElement analyze(Object o) {

        if (null == o) {
            return JSONElement.theVoid();
        }

        if (o instanceof JSONElement) {
            return (JSONElement) o;
        }

        Class<?> cls = o.getClass();

        if (o instanceof String) {
            return JSONElement.newPrimitive(o);
        } else if (o instanceof Number) {
            return JSONElement.newPrimitive(o);
        } else if (TypeUtil.isPrimitive(o)) {
            return JSONElement.newPrimitive(o);
        } else if (cls.isEnum()) {
            return JSONElement.newPrimitive(((Enum) o).ordinal());
        } else if (cls.isArray()) {
            JSONElement list = JSONElement.newList();
            for (int i = 0; i < Array.getLength(o); i++) {
                list.offer(analyze(Array.get(o, i)));
            }
            return list;
        } else if (o instanceof Iterable) {
            JSONElement list = JSONElement.newList();
            for (Object oo : (Iterable) o) {
                list.offer(analyze(oo));
            }
            return list;
        } else if (o instanceof Enumeration) {
            JSONElement list = JSONElement.newList();
            Enumeration es = (Enumeration) o;
            while (es.hasMoreElements()) {
                list.offer(analyze(es.nextElement()));
            }
            return list;
        } else if (o instanceof Map) {
            JSONElement map = JSONElement.newMap();
            for (Object k : ((Map) o).keySet()) {
                map.offer(StringUtil.toString(k), analyze(((Map) o).get(k)));
            }
            return map;
        } else if (o instanceof Dictionary) {
            JSONElement map = JSONElement.newMap();
            Enumeration keys = ((Dictionary) o).keys();
            while (keys.hasMoreElements()) {
                Object k = keys.nextElement();
                map.offer(StringUtil.toString(k), analyze(((Dictionary) o).get(k)));
            }
            return map;
        } else {
            JSONElement map = JSONElement.newMap();
            for (Field f : ObjectUtil.getFields(cls)) {
                try {
                    map.offer(f.getName(), analyze(f.get(o)));
                } catch (IllegalAccessException ignored) {

                }
            }
            return map;
        }

    }

    /**
     * {@link JSONElement} =&gt; Java {@link Object}.
     *
     * @param element the {@link JSONElement} to be converted.
     * @param target  the target {@link Type}.
     * @param <T>     the target {@link Type}.
     * @return the target Java {@link Object}.
     */
    public static <T> T analyze(JSONElement element, Type target) {

        if (null == element || element.isVoid() || null == target || NullType.class == target || Object.class == target) {
            return null;
        }

        if (target instanceof Class) {
            // original
            return (T) analyze(element, (Class) target);
        } else if (target instanceof ParameterizedType) {
            // generic
            return (T) analyze(element, (ParameterizedType) target);
        } else if (target instanceof GenericArrayType) {
            return null;
        } else if (target instanceof WildcardType) {
            return null;
        } else if (target instanceof TypeVariable) {
            return null;
        } else {
            // won't happen
            return null;
        }

    }

    private static <T> T analyze(JSONElement element, Class<T> target_class) {

        if (JSONElement.class.isAssignableFrom(target_class)) {
            return (T) element;
        } else if (Byte.class.isAssignableFrom(target_class) || Byte.TYPE == target_class) {
            return (T) (Byte) element.asByte();
        } else if (Short.class.isAssignableFrom(target_class) || Short.TYPE == target_class) {
            return (T) (Short) element.asShort();
        } else if (Integer.class.isAssignableFrom(target_class) || Integer.TYPE == target_class) {
            return (T) (Integer) element.asInt();
        } else if (Long.class.isAssignableFrom(target_class) || Long.TYPE == target_class) {
            return (T) (Long) element.asLong();
        } else if (Float.class.isAssignableFrom(target_class) || Float.TYPE == target_class) {
            return (T) (Float) element.asFloat();
        } else if (Double.class.isAssignableFrom(target_class) || Double.TYPE == target_class) {
            return (T) (Double) element.asDouble();
        } else if (Boolean.class.isAssignableFrom(target_class) || Boolean.TYPE == target_class) {
            return (T) (Boolean) element.asBoolean();
        } else if (Character.class.isAssignableFrom(target_class) || Character.TYPE == target_class) {
            return (T) (Character) element.asChar();
        } else if (String.class.isAssignableFrom(target_class)) {
            return (T) element.asString();
        } else if (target_class.isEnum()) {
            return target_class.getEnumConstants()[element.asInt()];
        } else if (target_class.isArray()) {
            Class<?> component_type = target_class.getComponentType();
            int size = element.size();
            Object array = Array.newInstance(component_type, size);
            for (int i = 0; i < size; i++) {
                Array.set(array, i, analyze(element.peek(i), component_type));
            }
            return (T) array;
        } else {
            // bean
            T rst = null;
            try {
                rst = target_class.newInstance();
            } catch (InstantiationException | IllegalAccessException ignored) {

            }
            for (Field f : ObjectUtil.getFields(target_class)) {
                try {
                    f.set(rst, analyze(element.peek(f.getName()), f.getGenericType()));
                } catch (IllegalAccessException ignored) {

                }
            }
            return rst;
        }

    }

    private static <T> T analyze(JSONElement element, ParameterizedType parameterized_type) {

        Type[] actual_type_arguments = parameterized_type.getActualTypeArguments();
        Class<T> target_class = (Class) parameterized_type.getRawType();

        if (Collection.class.isAssignableFrom(target_class)) {
            Type v_type = actual_type_arguments[0];
            Collection collection = null;
            try {
                // using the default constructor first
                collection = (Collection) target_class.newInstance();
            } catch (InstantiationException | IllegalAccessException ignored) {
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
                } catch (Exception ignored1) {

                }
            }
            // construction failure
            if (null == collection) {
                return null;
            }
            // analyze sub-elements
            for (JSONElement.Entry sub : element) {
                collection.add(analyze(sub.getElement(), v_type));
            }
            return (T) collection;
        } else if (Map.class.isAssignableFrom(target_class)) {
            Type k_type = actual_type_arguments[0];
            Type v_type = actual_type_arguments[1];
            Map map = null;
            try {
                // using the default constructor first
                map = (Map) target_class.newInstance();
            } catch (InstantiationException | IllegalAccessException ignored) {
                // considering known derived classes
                try {
                    if (EnumMap.class.isAssignableFrom(target_class)) {
                        map = new EnumMap((Class) k_type);
                    } else {
                        map = Defaults.map();
                    }
                } catch (Exception ignored1) {

                }
            }
            // construction failure
            if (null == map) {
                return null;
            }
            // analyze sub-elements
            for (JSONElement.Entry e : element.entries()) {
                map.put(analyze(JSONElement.newInstance(e.getKey()), k_type), analyze(e.getElement(), v_type));
            }
            return (T) map;
        } else if (Dictionary.class.isAssignableFrom(target_class)) {
            Type k_type = actual_type_arguments[0];
            Type v_type = actual_type_arguments[1];
            Dictionary dictionary = null;
            try {
                // using the default constructor first
                dictionary = (Dictionary) target_class.newInstance();
            } catch (InstantiationException | IllegalAccessException ignored) {
                // considering known derived classes
                try {
                    dictionary = Defaults.dictionary();
                } catch (Exception ignored1) {

                }
            }
            // construction failure
            if (null == dictionary) {
                return null;
            }
            // analyze sub-elements
            for (JSONElement.Entry e : element.entries()) {
                dictionary.put(analyze(JSONElement.newInstance(e.getKey()), k_type), analyze(e.getElement(), v_type));
            }
            return (T) dictionary;
        } else {
            // unknown generic type
            return null;
        }

    }

}
