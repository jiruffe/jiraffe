/*
 *    Copyright 2018 Chakilo
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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
final class JSONVoid extends JSONElement {

    static final JSONVoid VOID = new JSONVoid();

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
    public boolean isList() {
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
