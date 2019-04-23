/*
 *    Copyright 2018 Jiruffe
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

package com.jiruffe.jiraffe.model;

import java.util.*;
import java.util.function.Consumer;

/**
 * JSON <code>null</code>, <code>undefined</code> or <code>NaN</code>
 *
 * @author Jiruffe
 * 2019.02.20
 */
final class JSONVoid extends JSONElement {

    /**
     * The singleton instance of {@link JSONVoid}.
     */
    static final JSONVoid INSTANCE = new JSONVoid();

    private JSONVoid() {
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Iterator<Entry> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public void forEach(Consumer<? super Entry> action) {
        Objects.requireNonNull(action);
    }

    @Override
    public Spliterator<Entry> spliterator() {
        return Spliterators.emptySpliterator();
    }

}
