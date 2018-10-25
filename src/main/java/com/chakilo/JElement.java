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
 * @author konar
 */
public abstract class JElement implements Serializable, Iterable<JElement> {

    protected String __original_string;

}
