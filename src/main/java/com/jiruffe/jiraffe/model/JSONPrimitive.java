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
import com.jiruffe.jiraffe.util.StringUtil;
import com.jiruffe.jiraffe.util.TypeUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * JSON primitive value such as integer, string...
 *
 * @author Jiruffe
 * 2018.10.23
 */
final class JSONPrimitive extends JSONElement {

    private final Object _value;

    JSONPrimitive() {
        this(Defaults.primitive());
    }

    JSONPrimitive(Object v) {
        _value = v;
    }

    @Override
    public boolean isEmpty() {
        return StringUtil.EMPTY.equals(TypeUtil.castToString(_value));
    }

    @Override
    public Object asValue() {
        return _value;
    }

    @Override
    public String asString() {
        return TypeUtil.castToString(_value);
    }

    @Override
    public byte asByte() {
        return TypeUtil.castToByte(_value);
    }

    @Override
    public short asShort() {
        return TypeUtil.castToShort(_value);
    }

    @Override
    public int asInt() {
        return TypeUtil.castToInteger(_value);
    }

    @Override
    public long asLong() {
        return TypeUtil.castToLong(_value);
    }

    @Override
    public float asFloat() {
        return TypeUtil.castToFloat(_value);
    }

    @Override
    public double asDouble() {
        return TypeUtil.castToDouble(_value);
    }

    @Override
    public boolean asBoolean() {
        return TypeUtil.castToBoolean(_value);
    }

    @Override
    public char asChar() {
        return TypeUtil.castToCharacter(_value);
    }

    @Override
    public Number asNumber() {
        return TypeUtil.castToNumber(_value);
    }

    @Override
    public BigInteger asBigInteger() {
        return TypeUtil.castToBigInteger(_value);
    }

    @Override
    public BigDecimal asBigDecimal() {
        return TypeUtil.castToBigDecimal(_value);
    }

    @Override
    public Iterator<Entry> iterator() {
        return Collections.singleton(new Entry(null, this)).iterator();
    }

    @Override
    public void forEach(Consumer<? super Entry> action) {
        action.accept(new Entry(null, this));
    }

    @Override
    public Spliterator<Entry> spliterator() {
        return Collections.singleton(new Entry(null, this)).spliterator();
    }

}
