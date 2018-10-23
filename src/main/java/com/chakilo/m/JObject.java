package com.chakilo.m;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 2018.10.23
 *
 * @author konar
 */
public final class JObject extends JElement implements Iterable<JObject> {
    @Override
    public Iterator<JObject> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super JObject> action) {

    }

    @Override
    public Spliterator<JObject> spliterator() {
        return null;
    }
}
