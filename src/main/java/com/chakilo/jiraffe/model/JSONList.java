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

import com.chakilo.jiraffe.util.TypeUtil;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;
import java.util.function.Consumer;

/**
 * 2018.10.23
 *
 * JS list []
 *
 * @author Chakilo
 */
final class JSONList extends JSONElement {

    private List<JSONElement> _sub_elements;

    JSONList() {
        this(new ArrayList<>());
    }

    JSONList(List<JSONElement> sub_elements) {
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
        return true;
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
        return JSONElementType.LIST;
    }

    @Override
    public Set<Object> keys() {
        if (null != _sub_elements) {
            Set<Object> s = new HashSet<>();
            for (int i = 0; i < _sub_elements.size(); i++) {
                s.add(i);
            }
            return s;
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public JSONElement peek(Object k) throws InvalidArgumentException {
        if (TypeUtil.couldCastToInteger(k)) {
            JSONElement v = _sub_elements.get(TypeUtil.castToInteger(k));
            return null == v ? JSONElement.Void() : v;
        } else {
            throw new InvalidArgumentException(new String[]{"k"});
        }
    }

    @Override
    public JSONElement offer(Object v) {
        if (null == v) {
            _sub_elements.add(JSONElement.Void());
        } else if (v instanceof JSONElement) {
            _sub_elements.add((JSONElement) v);
        } else {
            _sub_elements.add(JSONElement.newValue(v));
        }
        return this;
    }

    @Override
    public Iterator<JSONElement> iterator() {
        return null != _sub_elements ? _sub_elements.iterator() : Collections.emptyIterator();
    }

    @Override
    public void forEach(Consumer<? super JSONElement> action) {
        if (null != _sub_elements) {
            _sub_elements.forEach(action);
        }
    }

    @Override
    public Spliterator<JSONElement> spliterator() {
        return null != _sub_elements ? _sub_elements.spliterator() : Spliterators.emptySpliterator();
    }

}
