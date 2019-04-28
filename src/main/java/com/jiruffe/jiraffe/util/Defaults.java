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

package com.jiruffe.jiraffe.util;

import java.util.*;

/**
 * Default types.
 *
 * @author cjl
 * 2019.02.27
 */
public abstract class Defaults {

    public static Object primitive() {
        return 0;
    }

    public static <K, V> Map<K, V> map() {
        return new LinkedHashMap<>();
    }

    public static <K, V> Dictionary<K, V> dictionary() {
        return new Hashtable<>();
    }

    public static <E> Collection<E> collection() {
        return list();
    }

    public static <E> List<E> list() {
        return new ArrayList<>();
    }

    public static <E> Set<E> set() {
        return new LinkedHashSet<>();
    }

    public static <E> SortedSet<E> sortedSet() {
        return new TreeSet<>();
    }

}
