package com.chakilo.m;

import com.chakilo.JElement;
import org.apache.commons.collections4.iterators.SingletonIterator;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * 2018.10.23
 *
 * 值类型
 *
 * @author konar
 */
public final class JValue extends JElement {

    private String _value;

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
