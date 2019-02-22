package com.chakilo.jiraffe.model;

import java.util.*;
import java.util.function.Consumer;

/**
 * 2019.02.20
 *
 * null, undefined, NaN
 *
 * @author Chakilo
 */
public class JSONVoid extends JSONElement {

    public static final JSONVoid VOID = new JSONVoid();

    private JSONVoid() {
    }

    @Override
    public boolean isVoid() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isArray() {
        return false;
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
        return JSONElementType.VOID;
    }

    @Override
    public Iterator<JSONElement> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public void forEach(Consumer<? super JSONElement> action) {
        Objects.requireNonNull(action);
    }

    @Override
    public Spliterator<JSONElement> spliterator() {
        return Spliterators.emptySpliterator();
    }

}
