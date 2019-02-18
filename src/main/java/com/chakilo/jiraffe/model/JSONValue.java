package com.chakilo.jiraffe.model;

import com.chakilo.jiraffe.lib.SingletonIterator;
import com.chakilo.jiraffe.model.base.JSONElement;
import com.chakilo.jiraffe.utils.StringUtil;
import com.chakilo.jiraffe.utils.TypeUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
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
        return new SingletonIterator<>(this, false);
    }

    @Override
    public void forEach(Consumer<? super JSONElement> action) {
        action.accept(this);
    }

    @Override
    public Spliterator<JSONElement> spliterator() {
        return Spliterators.spliterator(iterator(), 1, Spliterator.CONCURRENT);
    }

    @Override
    public Object getValue() throws Exception {
        return _value;
    }

    @Override
    public String getString() throws Exception {
        return StringUtil.toString(_value);
    }

    @Override
    public byte getByte() throws Exception {
        return TypeUtil.castToByte(_value);
    }

    @Override
    public short getShort() throws Exception {
        return new Integer(TypeUtil.castToInteger(_value)).shortValue();
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
        return super.getFloat();
    }

    @Override
    public double getDouble() throws Exception {
        return super.getDouble();
    }

    @Override
    public boolean getBoolean() throws Exception {
        return super.getBoolean();
    }

    @Override
    public char getChar() throws Exception {
        return super.getChar();
    }

    @Override
    public Number getNumber() throws Exception {
        return super.getNumber();
    }

    @Override
    public BigInteger getBigInteger() throws Exception {
        return super.getBigInteger();
    }

    @Override
    public BigDecimal getBigDecimal() throws Exception {
        return super.getBigDecimal();
    }

}
