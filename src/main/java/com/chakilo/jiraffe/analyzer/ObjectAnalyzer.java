package com.chakilo.jiraffe.analyzer;

import com.chakilo.jiraffe.model.base.JSONElement;

/**
 * 2019.02.18
 *
 * object <=> JSONElement 互转
 *
 * @author Chakilo
 */
public abstract class ObjectAnalyzer {

    public static JSONElement analyze(Object o) {
        return null;
    }

    public static <T> T analyze(JSONElement element, Class<T> target) {
        return null;
    }

}
