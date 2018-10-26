package com.chakilo.m;

import com.chakilo.JElement;

import java.util.*;
import java.util.function.Consumer;

/**
 * 2018.10.23
 *
 * 对象型 {}
 *
 * @author Chakilo
 */
public final class JObject extends JElement {

    protected Hashtable<String, JElement> _sub_elements;

    public JObject(Map<String, ? extends JElement> sub_elements) {
        _sub_elements = new Hashtable<>(sub_elements);
    }

    @Override
    public Iterator<JElement> iterator() {
        return null != _sub_elements ? _sub_elements.values().iterator() : null;
    }

    @Override
    public void forEach(Consumer<? super JElement> action) {
        if (null != _sub_elements) {
            _sub_elements.values().forEach(action);
        }
    }

    @Override
    public Spliterator<JElement> spliterator() {
        return null != _sub_elements ? _sub_elements.values().spliterator() : null;
    }
}
