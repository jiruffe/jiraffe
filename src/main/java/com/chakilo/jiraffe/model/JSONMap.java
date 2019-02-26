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
 * JS map {}
 *
 * @author Chakilo
 * 2018.10.23
 */
final class JSONMap extends JSONElement {

    private Map<Object, JSONElement> _sub_elements;

    JSONMap() {
        this(new LinkedHashMap<>());
    }

    JSONMap(Map<Object, JSONElement> sub_elements) {
        _sub_elements = sub_elements;
    }

    @Override
    public boolean isVoid() {
        return null == _sub_elements;
    }

    @Override
    public boolean isEmpty() {
        return isVoid() || _sub_elements.isEmpty();
    }

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public boolean isMap() {
        return true;
    }

    @Override
    public boolean isValue() {
        return false;
    }

    @Override
    public JSONElementType getType() {
        return JSONElementType.MAP;
    }

    @Override
    public int size() throws Exception {
        return null != _sub_elements ? _sub_elements.size() : 0;
    }

    @Override
    public Set<Object> keys() {
        return null != _sub_elements ? _sub_elements.keySet() : Collections.emptySet();
    }

    @Override
    public JSONElement peek(Object k) {
        JSONElement v = _sub_elements.get(k);
        return null == v ? JSONElement.Void() : v;
    }

    @Override
    public JSONElement offer(Object k, Object v) {
        if (null == v) {
            _sub_elements.put(k, JSONElement.Void());
        } else if (v instanceof JSONElement) {
            _sub_elements.put(k, (JSONElement) v);
        } else {
            _sub_elements.put(k, JSONElement.newValue(v));
        }
        return this;
    }

    @Override
    public Map<Object, JSONElement> asMap() throws Exception {
        return _sub_elements;
    }

    @Override
    public Iterator<JSONElement> iterator() {
        return null != _sub_elements ? _sub_elements.values().iterator() : Collections.emptyIterator();
    }

    @Override
    public void forEach(Consumer<? super JSONElement> action) {
        if (null != _sub_elements) {
            _sub_elements.values().forEach(action);
        }
    }

    @Override
    public Spliterator<JSONElement> spliterator() {
        return null != _sub_elements ? _sub_elements.values().spliterator() : Spliterators.emptySpliterator();
    }

}
