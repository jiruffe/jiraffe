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
 * {@link Object} &lt;=&gt; JSON {@link String},
 * not using {@link JSONElement} during conversion.
 *
 * @author Chakilo
 * 2019.02.18
 */
public abstract class DirectAnalyzer {

    /**
     * Java {@link Object} =&gt; JSON {@link String}.
     *
     * @param o the {@link Object} to be converted.
     * @return the JSON {@link String} converted.
     * @throws Exception if error occurred while analyzing {@link Object}.
     */
    public static String analyze(Object o) throws Exception {
        return null;
    }

    /**
     * JSON {@link String} =&gt; Java {@link Object}
     *
     * @param json   the JSON {@link String} to be converted.
     * @param target the target {@link Type}.
     * @param <T>    the target {@link Type}.
     * @return the target Java {@link Object}.
     * @throws Exception if error occurred while analyzing {@link Type}.
     */
    public static <T> T analyze(String json, Type target) throws Exception {
        return null;
    }

}
