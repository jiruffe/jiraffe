package com.chakilo.m;

import com.chakilo.utils.TypeUtil;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;
import java.util.function.Consumer;

/**
 * 2018.10.23
 *
 * 数组型 []
 *
 * @author Chakilo
 */
public final class JArray extends JElement {

    protected List<JElement> _sub_elements;

    public JArray() {
        this(new ArrayList<>());
    }

    public JArray(List<JElement> sub_elements) {
        _sub_elements = sub_elements;
    }

    @Override
    public Iterator<JElement> iterator() {
        return null != _sub_elements ? _sub_elements.iterator() : null;
    }

    @Override
    public void forEach(Consumer<? super JElement> action) {
        if (null != _sub_elements) {
            _sub_elements.forEach(action);
        }
    }

    @Override
    public Spliterator<JElement> spliterator() {
        return null != _sub_elements ? _sub_elements.spliterator() : null;
    }

    @Override
    public JElement peek(Object k) throws InvalidArgumentException {
        if (TypeUtil.couldCastToInteger(k)) {
            return _sub_elements.get(TypeUtil.castToInteger(k));
        } else {
            throw new InvalidArgumentException(new String[]{"k"});
        }
    }

    @Override
    public JElement offer(Object v) {
        if (v instanceof JElement) {
            _sub_elements.add((JElement) v);
        } else {
            _sub_elements.add(new JValue(v));
        }
        return this;
    }

}
