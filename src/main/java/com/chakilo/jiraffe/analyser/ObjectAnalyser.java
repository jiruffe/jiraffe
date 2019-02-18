package com.chakilo.jiraffe.analyser;

import com.chakilo.jiraffe.model.base.JSONElement;

/**
 * 2019.02.18
 *
 * object <=> JSONElement 互转
 *
 * @author Chakilo
 */
public abstract class ObjectAnalyser {

    public static JSONElement analyse(Object o) {
        return null;
    }

    public static <T> T analyse(JSONElement element, Class<T> target) {
        return null;
    }

}
