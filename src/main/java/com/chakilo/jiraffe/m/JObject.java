package com.chakilo.jiraffe.m;

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

    protected Map<Object, JElement> _sub_elements;

    public JObject() {
        this(new HashMap<>());
    }

    public JObject(Map<Object, JElement> sub_elements) {
        _sub_elements = sub_elements;
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

    @Override
    public JElement peek(Object k) {
        return _sub_elements.get(k);
    }

    @Override
    public JElement offer(Object k, Object v) {
        if (v instanceof JElement) {
            _sub_elements.put(k, (JElement) v);
        } else {
            _sub_elements.put(k, new JValue(v));
        }
        return this;
    }

}
