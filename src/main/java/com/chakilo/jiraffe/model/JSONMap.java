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
    public boolean isEmpty() {
        return _sub_elements.isEmpty();
    }

    @Override
    public int size() throws Exception {
        return _sub_elements.size();
    }

    @Override
    public Collection<Entry> entries() {
        Collection<Entry> entries = Defaults.list();
        for (Object k : _sub_elements.keySet()) {
            entries.add(new Entry(k, _sub_elements.get(k)));
        }
        return entries;
    }

    @Override
    public Collection<Object> keys() {
        return _sub_elements.keySet();
    }

    @Override
    public Collection<JSONElement> values() throws Exception {
        return _sub_elements.values();
    }

    @Override
    public JSONElement peek(Object k) {
        JSONElement v = _sub_elements.get(k);
        return null != v ? v : JSONElement.theVoid();
    }

    @Override
    public JSONElement poll(Object k) throws Exception {
        JSONElement v = _sub_elements.remove(k);
        return null != v ? v : JSONElement.theVoid();
    }

    @Override
    public JSONElement offer(Entry e) throws Exception {
        if (null == e) {
            throw new IllegalArgumentException("Argument e must not be null");
        } else {
            offer(e.getKey(), e.getElement());
        }
        return this;
    }

    @Override
    public JSONElement offer(Object k, Object v) {
        if (null == v) {
            _sub_elements.put(k, JSONElement.theVoid());
        } else if (v instanceof JSONElement) {
            _sub_elements.put(k, (JSONElement) v);
        } else {
            _sub_elements.put(k, JSONElement.newPrimitive(v));
        }
        return this;
    }

    @Override
    public JSONElement merge(JSONElement e) throws Exception {
        if (e instanceof JSONMap) {
            _sub_elements.putAll(e.asMap());
        } else {
            throw new IllegalArgumentException("Argument e must be instance of JSONMap");
        }
        return this;
    }

    @Override
    public boolean containsKey(Object k) throws Exception {
        if (null == k) {
            return false;
        } else {
            return _sub_elements.containsKey(k);
        }
    }

    @Override
    public boolean containsValue(Object v) throws Exception {
        if (this == v) {
            return true;
        } else if (null == v) {
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
        return entries().iterator();
    }

    @Override
    public void forEach(Consumer<? super Entry> action) {
        entries().forEach(action);
    }

    @Override
    public Spliterator<Entry> spliterator() {
        return entries().spliterator();
    }

}
