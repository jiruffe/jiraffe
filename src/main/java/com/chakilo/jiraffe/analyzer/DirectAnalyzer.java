package com.chakilo.jiraffe.analyzer;

/**
 * 2019.02.18
 *
 * object <=> json string,
 * not using <code>JSONElement</code> class during conversion.
 *
 * @author Chakilo
 */
public abstract class DirectAnalyzer {

    /**
     * object => json string
     *
     * @param o the object to be converted.
     * @return the json string converted.
     * @throws Exception if error occurred while analyzing object.
     */
    public static String analyze(Object o) throws Exception {
        return null;
    }

    /**
     * json string => object
     *
     * @param json the json string to be converted.
     * @param target the target type class.
     * @param <T> the target type.
     * @return the target java object.
     * @throws Exception if error occurred while analyzing class.
     */
    public static <T> T analyze(String json, Class<T> target) throws Exception {
        return null;
    }

}
