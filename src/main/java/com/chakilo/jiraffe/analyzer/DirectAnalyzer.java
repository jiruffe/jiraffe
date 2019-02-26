/*
 *    Copyright 2018 Chakilo
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

package com.chakilo.jiraffe.analyzer;

import com.chakilo.jiraffe.model.JSONElement;

import java.lang.reflect.Type;

/**
 * object &lt;=&gt; json string,
 * not using {@link JSONElement} class during conversion.
 *
 * @author Chakilo
 * 2019.02.18
 */
public abstract class DirectAnalyzer {

    /**
     * object =&gt; json string
     *
     * @param o the object to be converted.
     * @return the json string converted.
     * @throws Exception if error occurred while analyzing object.
     */
    public static String analyze(Object o) throws Exception {
        return null;
    }

    /**
     * json string =&gt; object
     *
     * @param json   the json string to be converted.
     * @param target the target type.
     * @param <T>    the target type.
     * @return the target java object.
     * @throws Exception if error occurred while analyzing type.
     */
    public static <T> T analyze(String json, Type target) throws Exception {
        return null;
    }

}
