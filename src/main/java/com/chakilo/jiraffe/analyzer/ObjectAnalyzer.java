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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

/**
 * 2019.02.18
 *
 * object <=> JSONElement conversion.
 *
 * @author Chakilo
 */
public abstract class ObjectAnalyzer {

    /**
     * object => JSONElement
     *
     * @param o the object to be converted.
     * @return a <code>JSONElement</code> converted.
     * @throws Exception if error occurred while analyzing object.
     */
    public static JSONElement analyze(Object o) throws Exception {

        if (null == o) {
            return JSONElement.Void();
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
            JSONElement object = JSONElement.newObject();
            for (Object k : ((Map) o).keySet()) {
                object.offer(StringUtil.toString(k), analyze(((Map) o).get(k)));
            }
            return object;
        } else if (o instanceof Dictionary) {
            JSONElement object = JSONElement.newObject();
            Enumeration keys = ((Dictionary) o).keys();
            while (keys.hasMoreElements()) {
                Object k = keys.nextElement();
                object.offer(StringUtil.toString(k), analyze(((Dictionary) o).get(k)));
            }
            return object;
        } else {
            JSONElement object = JSONElement.newObject();
            for (Field f : ObjectUtil.getFields(cls)) {
                object.offer(f.getName(), analyze(f.get(o)));
            }
            return object;
        }

    }

    /**
     * JSONElement => object
     *
     * @param element the JSONElement to be converted.
     * @param target the target type class.
     * @param <T> the target type.
     * @return the target java object.
     * @throws Exception if error occurred while analyzing class.
     */
    public static <T> T analyze(JSONElement element, Class<T> target) throws Exception {
        return null;
    }

}
