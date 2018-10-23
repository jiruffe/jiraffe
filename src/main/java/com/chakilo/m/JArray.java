package com.chakilo.m;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 2018.10.23
 *
 * @author konar
 */
public final class JArray extends JElement implements Iterable<JArray> {
    public Iterator<JArray> iterator() {
        return null;
    }

    public void forEach(Consumer<? super JArray> action) {

    }

    public Spliterator<JArray> spliterator() {
        return null;
    }
}
