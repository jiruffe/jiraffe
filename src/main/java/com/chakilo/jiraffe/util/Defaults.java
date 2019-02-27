package com.chakilo.jiraffe.util;

import java.util.*;

/**
 * Default types.
 *
 * @author cjl
 * 2019.02.27
 */
public abstract class Defaults {

    public static <K, V> Map<K, V> map() {
        return new LinkedHashMap<>();
    }

    public static <K, V> Dictionary<K, V> dictionary() {
        return new Hashtable<>();
    }

    public static <E> List<E> list() {
        return new ArrayList<>();
    }

    public static <E> Set<E> set() {
        return new HashSet<>();
    }

    public static <E> SortedSet<E> sortedSet() {
        return new TreeSet<>();
    }

}
