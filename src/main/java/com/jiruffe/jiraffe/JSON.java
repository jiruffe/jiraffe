/*
 *    Copyright 2018 Jiruffe
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

package com.jiruffe.jiraffe;

import com.jiruffe.jiraffe.analyzer.DirectAnalyzer;
import com.jiruffe.jiraffe.analyzer.ObjectAnalyzer;
import com.jiruffe.jiraffe.analyzer.StringAnalyzer;
import com.jiruffe.jiraffe.model.JSONElement;

import java.lang.reflect.Type;

/******************************************************************************
 *
 * jiraffe
 * A Java library for JSON conversion.
 *
 * https://github.com/jiruffe/jiraffe
 *
 * @author Jiruffe
 * 2018.10.23
 *
 ******************************************************************************/
public abstract class JSON {

    /**
     * Serializes Java {@link Object} to {@link JSONElement}.
     *
     * @param o the {@link Object} to be serialized.
     * @return the {@link JSONElement} serialized.
     */
    public static JSONElement serialize(Object o) {
        if (o instanceof JSONElement) {
            return (JSONElement) o;
        } else {
            return ObjectAnalyzer.analyze(o);
        }
    }

    /**
     * Deserializes JSON {@link String} to {@link JSONElement}.
     *
     * @param json the JSON {@link String} to be deserialized.
     * @return the {@link JSONElement} deserialized.
     */
    public static JSONElement deserialize(String json) {
        return StringAnalyzer.analyze(json);
    }

    /**
     * Directly serializes Java {@link Object} to JSON {@link String}.
     *
     * @param o the {@link Object} to be serialized.
     * @return the JSON {@link String} serialized.
     */
    public static String stringify(Object o) {
        if (o instanceof JSONElement) {
            return StringAnalyzer.analyze((JSONElement) o);
        } else {
            return DirectAnalyzer.analyze(o);
        }
    }

    /**
     * Directly deserializes JSON {@link String} to Java {@link Object}.
     *
     * @param json   the JSON {@link String} to be deserialized.
     * @param target the target {@link Type}.
     * @param <T>    the target {@link Type}.
     * @return the Java {@link Object} deserialized.
     */
    public static <T> T parse(String json, Type target) {
        if (target instanceof Class && JSONElement.class.isAssignableFrom((Class) target)) {
            return (T) StringAnalyzer.analyze(json);
        } else {
            return DirectAnalyzer.analyze(json, target);
        }
    }

}
