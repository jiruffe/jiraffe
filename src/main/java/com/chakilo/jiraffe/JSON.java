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

import com.chakilo.jiraffe.analyser.ForceAnalyser;
import com.chakilo.jiraffe.analyser.ObjectAnalyser;
import com.chakilo.jiraffe.model.base.JSONElement;
import com.chakilo.jiraffe.analyser.StringAnalyser;

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
        return ObjectAnalyser.analyse(o);
    }

    public static JSONElement deserialize(String json) {
        return StringAnalyser.analyse(json);
    }

    public static String forceSerialize(Object o) {
        return ForceAnalyser.analyse(o);
    }

    public static <T> T forceDeserialize(String json, Class<T> target) {
        return ForceAnalyser.analyse(json, target);
    }

}
