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
        return null;
    }

}
