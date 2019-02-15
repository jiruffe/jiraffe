package com.chakilo.jiraffe.lib;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 2019.02.13
 *
 * @author Chakilo
 */
public class SingletonIterator<E> implements Iterator<E> {
    private final boolean removeAllowed;
    private boolean beforeFirst;
    private boolean removed;
    private E object;

    public SingletonIterator(E object) {
        this(object, true);
    }

    public SingletonIterator(E object, boolean removeAllowed) {
        this.beforeFirst = true;
        this.removed = false;
        this.object = object;
        this.removeAllowed = removeAllowed;
    }

    public boolean hasNext() {
        return this.beforeFirst && !this.removed;
    }

    public E next() {
        if (this.beforeFirst && !this.removed) {
            this.beforeFirst = false;
            return this.object;
        } else {
            throw new NoSuchElementException();
        }
    }

    public void remove() {
        if (this.removeAllowed) {
            if (!this.removed && !this.beforeFirst) {
                this.object = null;
                this.removed = true;
            } else {
                throw new IllegalStateException();
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
