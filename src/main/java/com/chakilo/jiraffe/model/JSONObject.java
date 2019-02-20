package com.chakilo.jiraffe.model;

import com.chakilo.jiraffe.model.base.JSONElement;
import com.chakilo.jiraffe.model.base.JSONElementType;

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

    @Override
    public boolean isVoid() {
        return null == _sub_elements;
    }

    @Override
    public boolean isEmpty() {
        return isVoid() || _sub_elements.isEmpty();
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public boolean isValue() {
        return false;
    }

    @Override
    public JSONElementType getType() {
        return JSONElementType.OBJECT;
    }

    @Override
    public Set<Object> keys() {
        return null != _sub_elements ? _sub_elements.keySet() : Collections.emptySet();
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

    @Override
    public Iterator<JSONElement> iterator() {
        return null != _sub_elements ? _sub_elements.values().iterator() : Collections.emptyIterator();
    }

    @Override
    public void forEach(Consumer<? super JSONElement> action) {
        if (null != _sub_elements) {
            _sub_elements.values().forEach(action);
        }
    }

    @Override
    public Spliterator<JSONElement> spliterator() {
        return null != _sub_elements ? _sub_elements.values().spliterator() : Spliterators.emptySpliterator();
    }

}
