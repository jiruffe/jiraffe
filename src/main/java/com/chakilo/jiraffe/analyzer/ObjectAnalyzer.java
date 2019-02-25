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
            if (JSONElement.class.isAssignableFrom((Class) target)) {
                return (T) element;
            }
        } else if (target instanceof ParameterizedType) {

        } else if (target instanceof GenericArrayType) {

        } else if (target instanceof WildcardType) {

        } else if (target instanceof TypeVariable) {

        }

        return null;

    }

}
