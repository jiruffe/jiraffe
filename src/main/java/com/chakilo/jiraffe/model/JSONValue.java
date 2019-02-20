package com.chakilo.jiraffe.model;

import com.chakilo.jiraffe.model.base.JSONElement;
import com.chakilo.jiraffe.model.base.JSONElementType;
import com.chakilo.jiraffe.util.TypeUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 2018.10.23
 *
 * 值类型
 *
 * @author Chakilo
 */
public final class JSONValue extends JSONElement {

    private Object _value;

    public JSONValue() {
        this(null);
    }

    public JSONValue(Object v) {
        _value = v;
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

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isObject() {
        return false;
    }

    @Override
    public boolean isValue() {
        return true;
    }

    @Override
    public JSONElementType getType() {
        return JSONElementType.VALUE;
    }

    @Override
    public Object getValue() throws Exception {
        return _value;
    }

    @Override
    public String getString() throws Exception {
        return TypeUtil.castToString(_value);
    }

    @Override
    public byte getByte() throws Exception {
        return TypeUtil.castToByte(_value);
    }

    @Override
    public short getShort() throws Exception {
        return TypeUtil.castToShort(_value);
    }

    @Override
    public int getInt() throws Exception {
        return TypeUtil.castToInteger(_value);
    }

    @Override
    public long getLong() throws Exception {
        return TypeUtil.castToLong(_value);
    }

    @Override
    public float getFloat() throws Exception {
        return TypeUtil.castToFloat(_value);
    }

    @Override
    public double getDouble() throws Exception {
        return TypeUtil.castToDouble(_value);
    }

    @Override
    public boolean getBoolean() throws Exception {
        return TypeUtil.castToBoolean(_value);
    }

    @Override
    public char getChar() throws Exception {
        return TypeUtil.castToCharacter(_value);
    }

    @Override
    public Number getNumber() throws Exception {
        return TypeUtil.castToNumber(_value);
    }

    @Override
    public BigInteger getBigInteger() throws Exception {
        return TypeUtil.castToBigInteger(_value);
    }

    @Override
    public BigDecimal getBigDecimal() throws Exception {
        return TypeUtil.castToBigDecimal(_value);
    }

}
