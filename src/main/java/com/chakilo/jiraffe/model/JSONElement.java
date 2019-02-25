package com.chakilo.jiraffe.model;

import com.chakilo.jiraffe.analyzer.ObjectAnalyzer;
import com.chakilo.jiraffe.analyzer.StringAnalyzer;
import com.chakilo.jiraffe.util.ObjectUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 2018.10.23
 *
 * Represents JSON element including JS object {}, list [], or value (primitive type) such as integer, string...
 *
 * @author Chakilo
 */
public abstract class JSONElement implements Iterable<JSONElement> {

    /**
     * Get a new instance of <code>JSONList</code>.
     *
     * @return a new instance of <code>JSONList</code>.
     */
    public static JSONElement newList() {
        return new JSONList();
    }

    /**
     * Get a new instance of <code>JSONList</code> with specified sub-elements.
     *
     * @param sub_elements the sub-elements.
     * @return a new instance of <code>JSONList</code> with specified sub-elements.
     */
    public static JSONElement newList(List<JSONElement> sub_elements) {
        return new JSONList(sub_elements);
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
     * Get a new instance of <code>JSONObject</code> with specified sub-elements.
     *
     * @param sub_elements the sub-elements.
     * @return a new instance of <code>JSONObject</code> with specified sub-elements.
     */
    public static JSONElement newObject(Map<Object, JSONElement> sub_elements) {
        return new JSONObject(sub_elements);
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
     * Get a new instance of <code>JSONValue</code> with specified original value.
     *
     * @param v the original value.
     * @return a new instance of <code>JSONValue</code> with specified original value.
     */
    public static JSONElement newValue(Object v) {
        return new JSONValue(v);
    }

    /**
     * Represents a void element, also known as <code>null</code>, <code>undefined</code> or <code>NaN</code> in JS.
     *
     * @return a void element.
     */
    public static JSONElement Void() {
        return JSONVoid.VOID;
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
     * Returns whether this element is an instance of JS list [].
     *
     * @return true if instance of <code>JSONList</code>, otherwise false.
     */
    public boolean isList() {
        return this instanceof JSONList;
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
        } else if (isList()) {
            return JSONElementType.LIST;
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
     * @return the value that this element represents.
     * @throws Exception if this element does not represent a value.
     */
    public Object asValue() throws Exception {
        throw new UnsupportedOperationException("Could not cast value from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>String</code> that this element represents.
     *
     * @return the <code>String</code> that this element represents.
     * @throws Exception if this element does not represent a <code>String</code>.
     */
    public String asString() throws Exception {
        throw new UnsupportedOperationException("Could not cast String from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>byte</code> that this element represents.
     *
     * @return the <code>byte</code> that this element represents.
     * @throws Exception if this element does not represent a <code>byte</code>.
     */
    public byte asByte() throws Exception {
        throw new UnsupportedOperationException("Could not cast byte from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>short</code> that this element represents.
     *
     * @return the <code>short</code> that this element represents.
     * @throws Exception if this element does not represent a <code>short</code>.
     */
    public short asShort() throws Exception {
        throw new UnsupportedOperationException("Could not cast short from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>integer</code> that this element represents.
     *
     * @return the <code>integer</code> that this element represents.
     * @throws Exception if this element does not represent an <code>integer</code>.
     */
    public int asInt() throws Exception {
        throw new UnsupportedOperationException("Could not cast int from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>long</code> that this element represents.
     *
     * @return the <code>long</code> that this element represents.
     * @throws Exception if this element does not represent a <code>long</code>.
     */
    public long asLong() throws Exception {
        throw new UnsupportedOperationException("Could not cast long from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>float</code> that this element represents.
     *
     * @return the <code>float</code> that this element represents.
     * @throws Exception if this element does not represent a <code>float</code>.
     */
    public float asFloat() throws Exception {
        throw new UnsupportedOperationException("Could not cast float from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>double</code> that this element represents.
     *
     * @return the <code>double</code> that this element represents.
     * @throws Exception if this element does not represent a <code>double</code>.
     */
    public double asDouble() throws Exception {
        throw new UnsupportedOperationException("Could not cast double from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>boolean</code> that this element represents.
     *
     * @return the <code>boolean</code> that this element represents.
     * @throws Exception if this element does not represent a <code>boolean</code>.
     */
    public boolean asBoolean() throws Exception {
        throw new UnsupportedOperationException("Could not cast boolean from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>character</code> that this element represents.
     *
     * @return the <code>character</code> that this element represents.
     * @throws Exception if this element does not represent a <code>character</code>.
     */
    public char asChar() throws Exception {
        throw new UnsupportedOperationException("Could not cast char from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>number</code> that this element represents.
     *
     * @return the <code>number</code> that this element represents.
     * @throws Exception if this element does not represent a <code>number</code>.
     */
    public Number asNumber() throws Exception {
        throw new UnsupportedOperationException("Could not cast Number from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>BigInteger</code> that this element represents.
     *
     * @return the <code>BigInteger</code> that this element represents.
     * @throws Exception if this element does not represent a <code>BigInteger</code>.
     */
    public BigInteger asBigInteger() throws Exception {
        throw new UnsupportedOperationException("Could not cast BigInteger from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the <code>BigDecimal</code> that this element represents.
     *
     * @return the <code>BigDecimal</code> that this element represents.
     * @throws Exception if this element does not represent a <code>BigDecimal</code>.
     */
    public BigDecimal asBigDecimal() throws Exception {
        throw new UnsupportedOperationException("Could not cast BigDecimal from " + ObjectUtil.getSimpleName(this));
    }

}
