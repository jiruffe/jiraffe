package com.chakilo.jiraffe.analyser;

/**
 * 2019.02.18
 *
 * 直接实现 object <=> string 互转,
 * 不经过中间对象JSONElement
 *
 * @author Chakilo
 */
public abstract class ForceAnalyser {

    public static String analyse(Object o) {
        return null;
    }

    public static <T> T analyse(String json, Class<T> target) {
        return null;
    }

}
