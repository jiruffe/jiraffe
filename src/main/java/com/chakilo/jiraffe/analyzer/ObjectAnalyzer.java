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
import com.chakilo.jiraffe.util.ObjectUtil;
import com.chakilo.jiraffe.util.StringUtil;
import com.chakilo.jiraffe.util.TypeUtil;

import java.lang.reflect.*;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

/**
 * 2019.02.18
 *
 * object &lt;=&gt; <code>JSONElement</code> conversion.
 *
 * @author Chakilo
 */
public abstract class ObjectAnalyzer {

    /**
     * object =&gt; <code>JSONElement</code>
     *
     * @param o the object to be converted.
     * @return a <code>JSONElement</code> converted.
     * @throws Exception if error occurred while analyzing object.
     */
    public static JSONElement analyze(Object o) throws Exception {

        if (null == o) {
            return JSONElement.Void();
        }

        if (o instanceof JSONElement) {
            return (JSONElement) o;
        }

        Class<?> cls = o.getClass();

        if (o instanceof String) {
            return JSONElement.newValue(o);
        } else if (o instanceof Number) {
            return JSONElement.newValue(o);
        } else if (TypeUtil.isPrimitive(o)) {
            return JSONElement.newValue(o);
        } else if (cls.isEnum()) {
            return JSONElement.newValue(((Enum) o).ordinal());
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
                map.offer(f.getName(), analyze(f.get(o)));
            }
            return map;
        }

    }

    /**
     * <code>JSONElement</code> =&gt; object
     *
     * @param element the <code>JSONElement</code> to be converted.
     * @param target  the target type.
     * @param <T>     the target type.
     * @return the target java object.
     * @throws Exception if error occurred while analyzing type.
     */
    @SuppressWarnings("unchecked")
    public static <T> T analyze(JSONElement element, Type target) throws Exception {

        if (null == element || element.isVoid()) {
            return null;
        }

        if (target instanceof Class) {

            Class<T> target_class = (Class) target;
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
                T rst = target_class.newInstance();
                for (Field f : ObjectUtil.getFields(target_class)) {
                    f.set(rst, analyze(element.peek(f.getName()), f.getGenericType()));
                }
                return rst;
            }

        } else if (target instanceof ParameterizedType) {

            ParameterizedType parameterized_type = (ParameterizedType) target;
            Class<T> target_class = (Class) parameterized_type.getRawType();

            if (Iterable.class.isAssignableFrom(target_class)) {

            } else if (Enumeration.class.isAssignableFrom(target_class)) {

            } else if (Map.class.isAssignableFrom(target_class)) {

            } else if (Dictionary.class.isAssignableFrom(target_class)) {

            } else {

            }

        } else if (target instanceof GenericArrayType) {

        } else if (target instanceof WildcardType) {

        } else if (target instanceof TypeVariable) {

        }

        return null;

    }

}
