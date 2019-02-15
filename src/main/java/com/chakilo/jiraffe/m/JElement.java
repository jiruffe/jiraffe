package com.chakilo.jiraffe.m;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 2018.10.23
 *
 * 元素
 *
 * @author Chakilo
 */
public abstract class JElement implements Serializable, Iterable<JElement> {

    /**
     * 原始字符串
     */
    protected String __original_string;

    /**
     * 获取子元素
     * @param k 键
     * @return 子元素
     */
    public JElement peek(Object k) throws Exception {
        throw new UnsupportedOperationException("Could not peek from " + this.getClass().getSimpleName());
    }

    /**
     * 加入元素
     * @param v 子元素
     * @return 自身
     */
    public JElement offer(Object v) throws Exception {
        throw new UnsupportedOperationException("Could not offer v to " + this.getClass().getSimpleName());
    }

    /**
     * 加入元素
     * @param k 键
     * @param v 子元素
     * @return 自身
     */
    public JElement offer(Object k, Object v) throws Exception {
        throw new UnsupportedOperationException("Could not offer k, v to " + this.getClass().getSimpleName());
    }

    /**
     * 获取值
     * @return
     * @throws Exception
     */
    public Object getValue() throws Exception {
        throw new UnsupportedOperationException("Could not get value from " + this.getClass().getSimpleName());
    }

    /**
     * 获取String值
     * @return
     * @throws Exception
     */
    public String getString() throws Exception {
        throw new UnsupportedOperationException("Could not get String from " + this.getClass().getSimpleName());
    }

    /**
     * 获取byte值
     * @return
     * @throws Exception
     */
    public byte getByte() throws Exception {
        throw new UnsupportedOperationException("Could not get byte from " + this.getClass().getSimpleName());
    }

    /**
     * 获取short值
     * @return
     * @throws Exception
     */
    public short getShort() throws Exception {
        throw new UnsupportedOperationException("Could not get short from " + this.getClass().getSimpleName());
    }

    /**
     * 获取int值
     * @return
     * @throws Exception
     */
    public int getInt() throws Exception {
        throw new UnsupportedOperationException("Could not get int from " + this.getClass().getSimpleName());
    }

    /**
     * 获取long值
     * @return
     * @throws Exception
     */
    public long getLong() throws Exception {
        throw new UnsupportedOperationException("Could not get long from " + this.getClass().getSimpleName());
    }

    /**
     * 获取float值
     * @return
     * @throws Exception
     */
    public float getFloat() throws Exception {
        throw new UnsupportedOperationException("Could not get float from " + this.getClass().getSimpleName());
    }

    /**
     * 获取double值
     * @return
     * @throws Exception
     */
    public double getDouble() throws Exception {
        throw new UnsupportedOperationException("Could not get double from " + this.getClass().getSimpleName());
    }

    /**
     * 获取boolean值
     * @return
     * @throws Exception
     */
    public boolean getBoolean() throws Exception {
        throw new UnsupportedOperationException("Could not get boolean from " + this.getClass().getSimpleName());
    }

    /**
     * 获取char值
     * @return
     * @throws Exception
     */
    public char getChar() throws Exception {
        throw new UnsupportedOperationException("Could not get char from " + this.getClass().getSimpleName());
    }

    /**
     * 获取Number值
     * @return
     * @throws Exception
     */
    public Number getNumber() throws Exception {
        throw new UnsupportedOperationException("Could not get Number from " + this.getClass().getSimpleName());
    }

    /**
     * 获取BigInteger值
     * @return
     * @throws Exception
     */
    public BigInteger getBigInteger() throws Exception {
        throw new UnsupportedOperationException("Could not get BigInteger from " + this.getClass().getSimpleName());
    }

    /**
     * 获取BigDecimal值
     * @return
     * @throws Exception
     */
    public BigDecimal getBigDecimal() throws Exception {
        throw new UnsupportedOperationException("Could not get BigDecimal from " + this.getClass().getSimpleName());
    }

}
