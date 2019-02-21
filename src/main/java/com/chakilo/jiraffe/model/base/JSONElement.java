package com.chakilo.jiraffe.model.base;

import com.chakilo.jiraffe.analyzer.ObjectAnalyzer;
import com.chakilo.jiraffe.analyzer.StringAnalyzer;
import com.chakilo.jiraffe.model.JSONArray;
import com.chakilo.jiraffe.model.JSONObject;
import com.chakilo.jiraffe.model.JSONValue;
import com.chakilo.jiraffe.model.JSONVoid;
import com.chakilo.jiraffe.util.ObjectUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

/**
 * 2018.10.23
 *
 * Represents JSON element including JS object {}, array [], or value (primitive type) such as integer, string...
 *
 * @author Chakilo
 */
public abstract class JSONElement implements Iterable<JSONElement> {

    /**
     * Represents a void element, also known as <code>null</code>, <code>undefined</code> or <code>NaN</code> in JS.
     */
    public static final JSONElement VOID = JSONVoid.VOID;

    /**
     * Get a new instance of <code>JSONArray</code>.
     *
     * @return a new instance of <code>JSONArray</code>.
     */
    public static JSONElement newArray() {
        return new JSONArray();
    }

    /**
     * Get a new instance of <code>JSONObject</code>.
     *
     * @return a new instance of <code>JSONObject</code>.
     */
    public static JSONElement newObject() {
        return new JSONObject();
    }

    /**
     * Get a new instance of <code>JSONValue</code>.
     *
     * @return a new instance of <code>JSONValue</code>.
     */
    public static JSONElement newValue() {
        return new JSONValue();
    }

    /**
     * Converts this element to JSON string.
     *
     * @return JSON string.
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
     * Converts this element to java object.
     *
     * @param target The target type class.
     * @param <T>    The target type.
     * @return the target java object.
     * @throws Exception if error occurred while analyzing class.
     */
    public <T> T toObject(Class<T> target) throws Exception {
        if (JSONElement.class.isAssignableFrom(target)) {
            return (T) this;
        } else {
            return ObjectAnalyzer.analyze(this, target);
        }
    }

    /**
     * Returns whether this element is void.
     *
     * @return true if is void, otherwise false.
     */
    public boolean isVoid() {
        return this instanceof JSONVoid;
    }

    /**
     * Returns whether this element is empty.
     *
     * @return true if is empty, otherwise false.
     */
    public boolean isEmpty() {
        return this instanceof JSONVoid;
    }

    /**
     * Returns whether this element is an instance of JS array [].
     *
     * @return true if instance of <code>JSONArray</code>, otherwise false.
     */
    public boolean isArray() {
        return this instanceof JSONArray;
    }

    /**
     * Returns whether this element is an instance of JS object {}.
     *
     * @return true if instance of <code>JSONObject</code>, otherwise false.
     */
    public boolean isObject() {
        return this instanceof JSONObject;
    }

    /**
     * Returns whether this element is an instance of JS primitive type.
     *
     * @return true if instance of <code>JSONValue</code>, otherwise false.
     */
    public boolean isValue() {
        return this instanceof JSONValue;
    }

    /**
     * Get the type of this element.
     *
     * @return type of this element.
     */
    public JSONElementType getType() {
        if (isVoid()) {
            return JSONElementType.VOID;
        } else if (isArray()) {
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
     * Get all the keys of this element.
     *
     * @return a set of keys.
     * @throws Exception if element contains no keys.
     */
    public Set<Object> keys() throws Exception {
        throw new UnsupportedOperationException("Could not get keys from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Returns the sub-element with specified key.
     *
     * @param k the key.
     * @return the sub-element.
     */
    public JSONElement peek(Object k) throws Exception {
        throw new UnsupportedOperationException("Could not peek from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Offers a sub-element to this element.
     *
     * @param v the sub-element.
     * @return this element itself.
     */
    public JSONElement offer(Object v) throws Exception {
        throw new UnsupportedOperationException("Could not offer v to " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Offers a sub-element with specified key to this element.
     *
     * @param k the key.
     * @param v the sub-element.
     * @return this element itself.
     */
    public JSONElement offer(Object k, Object v) throws Exception {
        throw new UnsupportedOperationException("Could not offer k, v to " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the value that this element represents.
     *
     * @return the value.
     * @throws Exception if this element does not represent a value.
     */
    public Object getValue() throws Exception {
        throw new UnsupportedOperationException("Could not get value from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>String</code> that this element represents.
     *
     * @return the <code>String</code>.
     * @throws Exception if this element does not represent a value.
     */
    public String getString() throws Exception {
        throw new UnsupportedOperationException("Could not get String from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the byte value that this element represents.
     *
     * @return the byte value.
     * @throws Exception if this element does not represent a value.
     */
    public byte getByte() throws Exception {
        throw new UnsupportedOperationException("Could not get byte from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the short value that this element represents.
     *
     * @return the short value.
     * @throws Exception if this element does not represent a value.
     */
    public short getShort() throws Exception {
        throw new UnsupportedOperationException("Could not get short from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the integer value that this element represents.
     *
     * @return the integer value.
     * @throws Exception if this element does not represent a value.
     */
    public int getInt() throws Exception {
        throw new UnsupportedOperationException("Could not get int from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the long value that this element represents.
     *
     * @return the long value.
     * @throws Exception if this element does not represent a value.
     */
    public long getLong() throws Exception {
        throw new UnsupportedOperationException("Could not get long from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the float value that this element represents.
     *
     * @return the float value.
     * @throws Exception if this element does not represent a value.
     */
    public float getFloat() throws Exception {
        throw new UnsupportedOperationException("Could not get float from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the double value that this element represents.
     *
     * @return the double value.
     * @throws Exception if this element does not represent a value.
     */
    public double getDouble() throws Exception {
        throw new UnsupportedOperationException("Could not get double from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the boolean value that this element represents.
     *
     * @return the boolean value.
     * @throws Exception if this element does not represent a value.
     */
    public boolean getBoolean() throws Exception {
        throw new UnsupportedOperationException("Could not get boolean from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the character value that this element represents.
     *
     * @return the character value.
     * @throws Exception if this element does not represent a value.
     */
    public char getChar() throws Exception {
        throw new UnsupportedOperationException("Could not get char from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the number value that this element represents.
     *
     * @return the number value.
     * @throws Exception if this element does not represent a value.
     */
    public Number getNumber() throws Exception {
        throw new UnsupportedOperationException("Could not get Number from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>BigInteger</code> value that this element represents.
     *
     * @return the <code>BigInteger</code> value.
     * @throws Exception if this element does not represent a value.
     */
    public BigInteger getBigInteger() throws Exception {
        throw new UnsupportedOperationException("Could not get BigInteger from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>BigDecimal</code> value that this element represents.
     *
     * @return the <code>BigDecimal</code> value.
     * @throws Exception if this element does not represent a value.
     */
    public BigDecimal getBigDecimal() throws Exception {
        throw new UnsupportedOperationException("Could not get BigDecimal from " + ObjectUtil.getSimpleName(this));
    }

}
