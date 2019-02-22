package com.chakilo.jiraffe.analyzer;

import com.chakilo.jiraffe.model.JSONElement;
import com.chakilo.jiraffe.util.TypeUtil;

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
     * @return a JSONElement converted.
     * @throws Exception if error occurred while analyzing object.
     */
    public static JSONElement analyze(Object o) throws Exception {

        if (null == o) {
            return JSONElement.Void();
        }

        if (o instanceof String) {
            return JSONElement.newValue(o);
        } else if (TypeUtil.isPrimitive(o)) {
            return JSONElement.newValue(o);
        } else if (o.getClass().isArray()) {

        }

        return null;
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
