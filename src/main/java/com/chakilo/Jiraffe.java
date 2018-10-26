package com.chakilo;

import java.io.Serializable;

/******************************************************************************
 *
 * jiraffe
 * https://github.com/chakilo/jiraffe
 *
 * 2018.10.23
 * @author Chakilo
 *
 ******************************************************************************/
public final class Jiraffe {

    public static String serialize(JElement element) {
        return "";
    }

    public static String serialize(Object o) {
        return "";
    }

    public static JElement deserialize(String json) {
        return null;
    }

    public static <T> T deserialize(String json, Class<T> t) throws IllegalAccessException, InstantiationException {

        T rst = t.newInstance();



        return rst;

    }

}
