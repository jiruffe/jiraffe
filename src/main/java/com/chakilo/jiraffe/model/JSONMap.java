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

import com.chakilo.jiraffe.util.Defaults;

import java.util.*;
import java.util.function.Consumer;

/**
 * JSON map {}
 *
 * @author Chakilo
 * 2018.10.23
 */
final class JSONMap extends JSONElement {

    private Map<Object, JSONElement> _sub_elements;

    JSONMap() {
        this(Defaults.map());
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
    public boolean isPrimitive() {
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
    public Collection<Entry> entries() {
        if (null == _sub_elements) {
            return Collections.emptyList();
        } else {
            Collection<Entry> entries = Defaults.list();
            for (Object k : _sub_elements.keySet()) {
                entries.add(new Entry(k, _sub_elements.get(k)));
            }
            return entries;
        }
    }

    @Override
    public Collection<Object> keys() {
        return null != _sub_elements ? _sub_elements.keySet() : Collections.emptySet();
    }

    @Override
    public Collection<JSONElement> values() throws Exception {
        return null != _sub_elements ? _sub_elements.values() : Collections.emptyList();
    }

    @Override
    public JSONElement peek(Object k) {
        JSONElement v = _sub_elements.get(k);
        return null != v ? v : JSONElement.Void();
    }

    @Override
    public JSONElement offer(Object k, Object v) {
        if (null == v) {
            _sub_elements.put(k, JSONElement.Void());
        } else if (v instanceof JSONElement) {
            _sub_elements.put(k, (JSONElement) v);
        } else {
            _sub_elements.put(k, JSONElement.newPrimitive(v));
        }
        return this;
    }

    @Override
    public boolean containsKey(Object k) throws Exception {
        if (null == k || null == _sub_elements) {
            return false;
        } else {
            return _sub_elements.containsKey(k);
        }
    }

    @Override
    public boolean containsValue(Object v) throws Exception {
        if (this == v) {
            return true;
        } else if (null == v || null == _sub_elements) {
            return false;
        } else {
            JSONElement ev = JSONElement.newInstance(v);
            return _sub_elements.containsValue(ev) || equals(ev);
        }
    }

    @Override
    public Map<Object, JSONElement> asMap() throws Exception {
        return _sub_elements;
    }

    @Override
    public Iterator<Entry> iterator() {
        return null != _sub_elements ? entries().iterator() : Collections.emptyIterator();
    }

    @Override
    public void forEach(Consumer<? super Entry> action) {
        if (null != _sub_elements) {
            entries().forEach(action);
        }
    }

    @Override
    public Spliterator<Entry> spliterator() {
        return null != _sub_elements ? entries().spliterator() : Spliterators.emptySpliterator();
    }

}
