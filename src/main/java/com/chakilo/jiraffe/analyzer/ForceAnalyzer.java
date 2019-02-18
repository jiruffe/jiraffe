package com.chakilo.jiraffe.analyzer;

/**
 * 2019.02.18
 *
 * 直接实现 object <=> string 互转,
 * 不经过中间对象JSONElement
 *
 * @author Chakilo
 */
public abstract class ForceAnalyzer {

    public static String analyze(Object o) {
        return null;
    }

    public static <T> T analyze(String json, Class<T> target) {
        return null;
    }

}
