package com.chakilo;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 2018.10.23
 *
 * 元素
 *
 * @author Chakilo
 */
public abstract class JElement implements Serializable, Iterable<JElement> {

    /**
     * 原始字符串
     */
    protected String __original_string;

}
