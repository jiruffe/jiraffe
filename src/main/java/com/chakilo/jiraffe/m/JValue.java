package com.chakilo.jiraffe.m;

import com.chakilo.jiraffe.lib.SingletonIterator;

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
public final class JValue extends JElement {

    private Object _value;

    public JValue() {
        this(null);
    }

    public JValue(Object v) {
        _value = v;
    }

    @Override
    public Iterator<JElement> iterator() {
        return new SingletonIterator<>(this, false);
    }

    @Override
    public void forEach(Consumer<? super JElement> action) {
        action.accept(this);
    }

    @Override
    public Spliterator<JElement> spliterator() {
        return Spliterators.spliterator(iterator(), 1, Spliterator.CONCURRENT);
    }

    @Override
    public Object getValue() throws Exception {
        return _value;
    }

    @Override
    public String getString() throws Exception {
        return _value.toString();
    }

    @Override
    public byte getByte() throws Exception {
        return super.getByte();
    }

    @Override
    public short getShort() throws Exception {
        return super.getShort();
    }

    @Override
    public int getInt() throws Exception {
        return super.getInt();
    }

    @Override
    public long getLong() throws Exception {
        return super.getLong();
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
