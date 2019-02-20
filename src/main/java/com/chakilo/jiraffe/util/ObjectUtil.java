package com.chakilo.jiraffe.util;

/**
 * 2019.02.20
 *
 * @author cjl
 */
public abstract class ObjectUtil {

    public static String getCanonicalName(Object o) {
        return (null == o ? "null" : o.getClass().getCanonicalName());
    }

    public static String getSimpleName(Object o) {
        return (null == o ? "null" : o.getClass().getSimpleName());
    }

    public static String getName(Object o) {
        return (null == o ? "null" : o.getClass().getName());
    }

}
