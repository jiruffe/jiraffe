package com.chakilo.jiraffe.model;

import com.chakilo.jiraffe.model.base.JSONElement;

import java.util.*;
import java.util.function.Consumer;

/**
 * 2018.10.23
 *
 * 对象型 {}
 *
 * @author Chakilo
 */
public final class JSONObject extends JSONElement {

    protected Map<Object, JSONElement> _sub_elements;

    public JSONObject() {
        this(new HashMap<>());
    }

    public JSONObject(Map<Object, JSONElement> sub_elements) {
        _sub_elements = sub_elements;
    }

    public Set<Object> keySet() {
        return null != _sub_elements ? _sub_elements.keySet() : null;
    }

    @Override
    public Iterator<JSONElement> iterator() {
        return null != _sub_elements ? _sub_elements.values().iterator() : null;
    }

    @Override
    public void forEach(Consumer<? super JSONElement> action) {
        if (null != _sub_elements) {
            _sub_elements.values().forEach(action);
        }
    }

    @Override
    public Spliterator<JSONElement> spliterator() {
        return null != _sub_elements ? _sub_elements.values().spliterator() : null;
    }

    @Override
    public JSONElement peek(Object k) {
        return _sub_elements.get(k);
    }

    @Override
    public JSONElement offer(Object k, Object v) {
        if (v instanceof JSONElement) {
            _sub_elements.put(k, (JSONElement) v);
        } else {
            _sub_elements.put(k, new JSONValue(v));
        }
        return this;
    }

}
