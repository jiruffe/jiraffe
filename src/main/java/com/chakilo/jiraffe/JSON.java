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
package com.chakilo.jiraffe;

import com.chakilo.jiraffe.analyzer.ForceAnalyzer;
import com.chakilo.jiraffe.analyzer.ObjectAnalyzer;
import com.chakilo.jiraffe.analyzer.StringAnalyzer;
import com.chakilo.jiraffe.model.base.JSONElement;

/******************************************************************************
 *
 * jiraffe
 * https://github.com/chakilo/jiraffe
 *
 * 2018.10.23
 * @author Chakilo
 *
 ******************************************************************************/
public abstract class JSON {

    public static JSONElement serialize(Object o) {
        if (o instanceof JSONElement) {
            return (JSONElement) o;
        } else {
            return ObjectAnalyzer.analyze(o);
        }
    }

    public static JSONElement deserialize(String json) throws Exception {
        return StringAnalyzer.analyze(json);
    }

    public static String forceSerialize(Object o) throws Exception {
        if (o instanceof JSONElement) {
            return StringAnalyzer.analyze((JSONElement) o);
        } else {
            return ForceAnalyzer.analyze(o);
        }
    }

    public static <T> T forceDeserialize(String json, Class<T> target) throws Exception {
        if (JSONElement.class.isAssignableFrom(target)) {
            return (T) StringAnalyzer.analyze(json);
        } else {
            return ForceAnalyzer.analyze(json, target);
        }
    }

}
