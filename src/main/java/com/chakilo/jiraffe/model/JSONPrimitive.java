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
import com.chakilo.jiraffe.util.StringUtil;
import com.chakilo.jiraffe.util.TypeUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * JSON primitive value such as integer, string...
 *
 * @author Chakilo
 * 2018.10.23
 */
final class JSONPrimitive extends JSONElement {

    private Object _value;

    JSONPrimitive() {
        this(Defaults.primitive());
    }

    JSONPrimitive(Object v) {
        _value = v;
    }

    @Override
    public boolean isVoid() {
        return null == _value;
    }

    @Override
    public boolean isEmpty() {
        return isVoid() || StringUtil.EMPTY.equals(StringUtil.toString(_value));
    }

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public boolean isMap() {
        return false;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public JSONElementType getType() {
        return JSONElementType.PRIMITIVE;
    }

    @Override
    public Object asValue() throws Exception {
        return _value;
    }

    @Override
    public String asString() throws Exception {
        return TypeUtil.castToString(_value);
    }

    @Override
    public byte asByte() throws Exception {
        return TypeUtil.castToByte(_value);
    }

    @Override
    public short asShort() throws Exception {
        return TypeUtil.castToShort(_value);
    }

    @Override
    public int asInt() throws Exception {
        return TypeUtil.castToInteger(_value);
    }

    @Override
    public long asLong() throws Exception {
        return TypeUtil.castToLong(_value);
    }

    @Override
    public float asFloat() throws Exception {
        return TypeUtil.castToFloat(_value);
    }

    @Override
    public double asDouble() throws Exception {
        return TypeUtil.castToDouble(_value);
    }

    @Override
    public boolean asBoolean() throws Exception {
        return TypeUtil.castToBoolean(_value);
    }

    @Override
    public char asChar() throws Exception {
        return TypeUtil.castToCharacter(_value);
    }

    @Override
    public Number asNumber() throws Exception {
        return TypeUtil.castToNumber(_value);
    }

    @Override
    public BigInteger asBigInteger() throws Exception {
        return TypeUtil.castToBigInteger(_value);
    }

    @Override
    public BigDecimal asBigDecimal() throws Exception {
        return TypeUtil.castToBigDecimal(_value);
    }

    @Override
    public Iterator<JSONElement> iterator() {
        return Collections.singleton((JSONElement) this).iterator();
    }

    @Override
    public void forEach(Consumer<? super JSONElement> action) {
        action.accept(this);
    }

    @Override
    public Spliterator<JSONElement> spliterator() {
        return Collections.singleton((JSONElement) this).spliterator();
    }

}
