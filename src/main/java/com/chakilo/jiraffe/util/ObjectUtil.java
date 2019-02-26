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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Util for <code>Object</code> or <code>Class</code>.
 *
 * @author Chakilo
 * 2019.02.20
 */
public abstract class ObjectUtil {

    public static String getCanonicalName(Object o) {
        return null == o ? "null" : o.getClass().getCanonicalName();
    }

    public static String getSimpleName(Object o) {
        return null == o ? "null" : o.getClass().getSimpleName();
    }

    public static String getName(Object o) {
        return null == o ? "null" : o.getClass().getName();
    }

    public static List<Field> getFields(Class<?> cls) {

        if (null == cls || Object.class == cls) {
            return Collections.emptyList();
        }

        List<Field> rst = new ArrayList<>(getFields(cls.getSuperclass()));

        for (Field f : cls.getDeclaredFields()) {
            if (!Modifier.isTransient(f.getModifiers())) {
                f.setAccessible(true);
                rst.add(f);
            }
        }

        return rst;

    }

}
