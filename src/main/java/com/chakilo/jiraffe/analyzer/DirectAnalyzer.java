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
