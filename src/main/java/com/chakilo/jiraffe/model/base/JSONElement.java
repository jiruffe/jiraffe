package com.chakilo.jiraffe.model.base;

import com.chakilo.jiraffe.analyzer.ObjectAnalyzer;
import com.chakilo.jiraffe.analyzer.StringAnalyzer;
import com.chakilo.jiraffe.model.JSONArray;
import com.chakilo.jiraffe.model.JSONObject;
import com.chakilo.jiraffe.model.JSONValue;
import com.chakilo.jiraffe.util.ObjectUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

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
        try {
            return StringAnalyzer.analyze(this);
        } catch (Exception e) {
            return super.toString();
        }
    }

    /**
     * 转为对象
     *
     * @param target 对象类型
     * @param <T>    对象类型
     * @return 目标对象
     */
    public <T> T toObject(Class<T> target) {
        return ObjectAnalyzer.analyze(this, target);
    }

    /**
     * 是否为空
     *
     * @return true if is void
     */
    public boolean isVoid() {
        return true;
    }

    /**
     * 是否是数组类型[]
     *
     * @return true if instance of <code>JSONArray</code>, otherwise false
     */
    public boolean isArray() {
        return this instanceof JSONArray;
    }

    /**
     * 是否是对象类型{}
     *
     * @return true if instance of <code>JSONObject</code>, otherwise false
     */
    public boolean isObject() {
        return this instanceof JSONObject;
    }

    /**
     * 是否是值类型""
     *
     * @return true if instance of <code>JSONValue</code>, otherwise false
     */
    public boolean isValue() {
        return this instanceof JSONValue;
    }

    /**
     * 获取类型
     *
     * @return 类型
     */
    public JSONElementType getType() {
        if (isArray()) {
            return JSONElementType.ARRAY;
        } else if (isObject()) {
            return JSONElementType.OBJECT;
        } else if (isValue()) {
            return JSONElementType.VALUE;
        } else {
            return JSONElementType.UNKNOWN;
        }
    }

    /**
     * 获取所有的键
     * @return 键
     * @throws Exception if element contains no key
     */
    public Set<Object> keys() throws Exception {
        throw new UnsupportedOperationException("Could not get keys from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取子元素
     *
     * @param k 键
     * @return 子元素
     */
    public JSONElement peek(Object k) throws Exception {
        throw new UnsupportedOperationException("Could not peek from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 加入元素
     *
     * @param v 子元素
     * @return 自身
     */
    public JSONElement offer(Object v) throws Exception {
        throw new UnsupportedOperationException("Could not offer v to " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 加入元素
     *
     * @param k 键
     * @param v 子元素
     * @return 自身
     */
    public JSONElement offer(Object k, Object v) throws Exception {
        throw new UnsupportedOperationException("Could not offer k, v to " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public Object getValue() throws Exception {
        throw new UnsupportedOperationException("Could not get value from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取String值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public String getString() throws Exception {
        throw new UnsupportedOperationException("Could not get String from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取byte值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public byte getByte() throws Exception {
        throw new UnsupportedOperationException("Could not get byte from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取short值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public short getShort() throws Exception {
        throw new UnsupportedOperationException("Could not get short from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取int值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public int getInt() throws Exception {
        throw new UnsupportedOperationException("Could not get int from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取long值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public long getLong() throws Exception {
        throw new UnsupportedOperationException("Could not get long from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取float值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public float getFloat() throws Exception {
        throw new UnsupportedOperationException("Could not get float from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取double值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public double getDouble() throws Exception {
        throw new UnsupportedOperationException("Could not get double from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取boolean值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public boolean getBoolean() throws Exception {
        throw new UnsupportedOperationException("Could not get boolean from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取char值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public char getChar() throws Exception {
        throw new UnsupportedOperationException("Could not get char from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取Number值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public Number getNumber() throws Exception {
        throw new UnsupportedOperationException("Could not get Number from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取BigInteger值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public BigInteger getBigInteger() throws Exception {
        throw new UnsupportedOperationException("Could not get BigInteger from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * 获取BigDecimal值
     *
     * @return 值
     * @throws Exception if not supported
     */
    public BigDecimal getBigDecimal() throws Exception {
        throw new UnsupportedOperationException("Could not get BigDecimal from " + ObjectUtil.getSimpleName(this));
    }

}
