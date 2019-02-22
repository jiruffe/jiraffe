package com.chakilo.jiraffe.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 2019.02.20
 *
 * @author Chakilo
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
