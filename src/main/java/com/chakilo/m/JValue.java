package com.chakilo.m;

import com.chakilo.lib.SingletonIterator;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * 2018.10.23
 *
 * 值类型
 *
 * @author Chakilo
 */
public final class JValue extends JElement {

    private Object _value;

    public JValue() {
        this(null);
    }

    public JValue(Object v) {
        _value = v;
    }

    @Override
    public Iterator<JElement> iterator() {
        return new SingletonIterator<>(this, false);
    }

    @Override
    public void forEach(Consumer<? super JElement> action) {
        action.accept(this);
    }

    @Override
    public Spliterator<JElement> spliterator() {
        return Spliterators.spliterator(iterator(), 1, Spliterator.CONCURRENT);
    }

}
