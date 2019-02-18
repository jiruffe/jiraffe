package com.chakilo.jiraffe.model.base;

import com.chakilo.jiraffe.analyser.ObjectAnalyser;
import com.chakilo.jiraffe.analyser.StringAnalyser;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 2018.10.23
 *
 * 元素
 *
 * @author Chakilo
 */
public abstract class JSONElement implements Iterable<JSONElement> {

    /**
     * 转为json string
     *
     * @return json
     */
    @Override
    public String toString() {
        return StringAnalyser.analyse(this);
    }

    /**
     * 转为对象
     *
     * @param target 对象类型
     * @param <T>    对象类型
     * @return 目标对象
     */
    public <T> T toObject(Class<T> target) {
        return ObjectAnalyser.analyse(this, target);
    }

    /**
     * 获取子元素
     *
     * @param k 键
     * @return 子元素
     */
    public JSONElement peek(Object k) throws Exception {
        throw new UnsupportedOperationException("Could not peek from " + this.getClass().getSimpleName());
    }

    /**
     * 加入元素
     *
     * @param v 子元素
     * @return 自身
     */
    public JSONElement offer(Object v) throws Exception {
        throw new UnsupportedOperationException("Could not offer v to " + this.getClass().getSimpleName());
    }

    /**
     * 加入元素
     *
     * @param k 键
     * @param v 子元素
     * @return 自身
     */
    public JSONElement offer(Object k, Object v) throws Exception {
        throw new UnsupportedOperationException("Could not offer k, v to " + this.getClass().getSimpleName());
    }

    /**
     * 获取值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public Object getValue() throws Exception {
        throw new UnsupportedOperationException("Could not get value from " + this.getClass().getSimpleName());
    }

    /**
     * 获取String值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public String getString() throws Exception {
        throw new UnsupportedOperationException("Could not get String from " + this.getClass().getSimpleName());
    }

    /**
     * 获取byte值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public byte getByte() throws Exception {
        throw new UnsupportedOperationException("Could not get byte from " + this.getClass().getSimpleName());
    }

    /**
     * 获取short值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public short getShort() throws Exception {
        throw new UnsupportedOperationException("Could not get short from " + this.getClass().getSimpleName());
    }

    /**
     * 获取int值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public int getInt() throws Exception {
        throw new UnsupportedOperationException("Could not get int from " + this.getClass().getSimpleName());
    }

    /**
     * 获取long值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public long getLong() throws Exception {
        throw new UnsupportedOperationException("Could not get long from " + this.getClass().getSimpleName());
    }

    /**
     * 获取float值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public float getFloat() throws Exception {
        throw new UnsupportedOperationException("Could not get float from " + this.getClass().getSimpleName());
    }

    /**
     * 获取double值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public double getDouble() throws Exception {
        throw new UnsupportedOperationException("Could not get double from " + this.getClass().getSimpleName());
    }

    /**
     * 获取boolean值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public boolean getBoolean() throws Exception {
        throw new UnsupportedOperationException("Could not get boolean from " + this.getClass().getSimpleName());
    }

    /**
     * 获取char值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public char getChar() throws Exception {
        throw new UnsupportedOperationException("Could not get char from " + this.getClass().getSimpleName());
    }

    /**
     * 获取Number值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public Number getNumber() throws Exception {
        throw new UnsupportedOperationException("Could not get Number from " + this.getClass().getSimpleName());
    }

    /**
     * 获取BigInteger值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public BigInteger getBigInteger() throws Exception {
        throw new UnsupportedOperationException("Could not get BigInteger from " + this.getClass().getSimpleName());
    }

    /**
     * 获取BigDecimal值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public BigDecimal getBigDecimal() throws Exception {
        throw new UnsupportedOperationException("Could not get BigDecimal from " + this.getClass().getSimpleName());
    }

}
