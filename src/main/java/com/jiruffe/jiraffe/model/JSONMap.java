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

import com.jiruffe.jiraffe.util.Defaults;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * JSON map {}
 *
 * @author Jiruffe
 * 2018.10.23
 */
final class JSONMap extends JSONElement {

    private final Map<Object, JSONElement> _sub_elements;

    JSONMap() {
        this(Defaults.map());
    }

    JSONMap(Map<?, JSONElement> sub_elements) {
        _sub_elements = (Map<Object, JSONElement>) sub_elements;
    }

    @Override
    public boolean isEmpty() {
        return _sub_elements.isEmpty();
    }

    @Override
    public int size() {
        return _sub_elements.size();
    }

    @Override
    public Collection<Entry> entries() {
        Collection<Entry> entries = Defaults.collection();
        for (Map.Entry<Object, JSONElement> e : _sub_elements.entrySet()) {
            entries.add(new Entry(e.getKey(), e.getValue()));
        }
        return entries;
    }

    @Override
    public Collection<Object> keys() {
        return _sub_elements.keySet();
    }

    @Override
    public Collection<JSONElement> values() {
        return _sub_elements.values();
    }

    @Override
    public JSONElement peek(Object k) {
        JSONElement v = _sub_elements.get(k);
        return null != v ? v : JSONElement.theVoid();
    }

    @Override
    public JSONElement poll(Object k) {
        JSONElement v = _sub_elements.remove(k);
        return null != v ? v : JSONElement.theVoid();
    }

    @Override
    public JSONElement offer(Object v) {
        if (v instanceof Entry) {
            offer(((Entry) v).getKey(), ((Entry) v).getElement());
        } else {
            super.offer(v);
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
    public JSONElement merge(JSONElement e) {
        if (e instanceof JSONMap) {
            _sub_elements.putAll(e.asMap());
        } else {
            super.merge(e);
        }
        return this;
    }

    @Override
    public boolean containsKey(Object k) {
        if (null == k) {
            return false;
        } else {
            return _sub_elements.containsKey(k);
        }
    }

    @Override
    public boolean containsValue(Object v) {
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
    public Map<Object, JSONElement> asMap() {
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
