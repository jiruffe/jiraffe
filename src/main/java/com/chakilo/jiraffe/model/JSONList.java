package com.chakilo.jiraffe.model;

import com.chakilo.jiraffe.util.TypeUtil;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;
import java.util.function.Consumer;

/**
 * 2018.10.23
 *
 * JS array []
 *
 * @author Chakilo
 */
public final class JSONList extends JSONElement {

    private List<JSONElement> _sub_elements;

    JSONList() {
        this(new ArrayList<>());
    }

    JSONList(List<JSONElement> sub_elements) {
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
        return true;
    }

    @Override
    public boolean isObject() {
        return false;
    }

    @Override
    public boolean isValue() {
        return false;
    }

    @Override
    public JSONElementType getType() {
        return JSONElementType.LIST;
    }

    @Override
    public Set<Object> keys() {
        if (null != _sub_elements) {
            Set<Object> s = new HashSet<>();
            for (int i = 0; i < _sub_elements.size(); i++) {
                s.add(i);
            }
            return s;
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public JSONElement peek(Object k) throws InvalidArgumentException {
        if (TypeUtil.couldCastToInteger(k)) {
            JSONElement v = _sub_elements.get(TypeUtil.castToInteger(k));
            return null == v ? JSONElement.Void() : v;
        } else {
            throw new InvalidArgumentException(new String[]{"k"});
        }
    }

    @Override
    public JSONElement offer(Object v) {
        if (null == v) {
            _sub_elements.add(JSONElement.Void());
        } else if (v instanceof JSONElement) {
            _sub_elements.add((JSONElement) v);
        } else {
            _sub_elements.add(JSONElement.newValue(v));
        }
        return this;
    }

    @Override
    public Iterator<JSONElement> iterator() {
        return null != _sub_elements ? _sub_elements.iterator() : Collections.emptyIterator();
    }

    @Override
    public void forEach(Consumer<? super JSONElement> action) {
        if (null != _sub_elements) {
            _sub_elements.forEach(action);
        }
    }

    @Override
    public Spliterator<JSONElement> spliterator() {
        return null != _sub_elements ? _sub_elements.spliterator() : Spliterators.emptySpliterator();
    }

}
