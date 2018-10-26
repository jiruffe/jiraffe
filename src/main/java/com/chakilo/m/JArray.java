package com.chakilo.m;

import com.chakilo.JElement;

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

    public JArray(Collection<? extends JElement> sub_elements) {
        _sub_elements = Collections.synchronizedList(new ArrayList<>(sub_elements));
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
}
