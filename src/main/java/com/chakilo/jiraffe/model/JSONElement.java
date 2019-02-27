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

import com.chakilo.jiraffe.analyzer.ObjectAnalyzer;
import com.chakilo.jiraffe.analyzer.StringAnalyzer;
import com.chakilo.jiraffe.util.ObjectUtil;
import com.chakilo.jiraffe.util.TypeUtil;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents JSON element including JSON map {}, list [], or primitive value such as integer, string...
 *
 * @author Chakilo
 * 2018.10.23
 */
public abstract class JSONElement implements Iterable<JSONElement> {

    /**
     * Converts this element to JSON {@link String}.
     *
     * @return JSON {@link String}.
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
     * Converts this element to Java {@link Object}.
     *
     * @param target The target {@link Type}.
     * @param <T>    The target {@link Type}.
     * @return the target Java {@link Object}.
     * @throws Exception if error occurred while analyzing {@link Type}.
     */
    public <T> T toObject(Type target) throws Exception {
        return ObjectAnalyzer.analyze(this, target);
    }

    /**
     * Returns {@link JSONVoid#INSTANCE} which represents a <code>void</code> element, also known as <code>null</code>, <code>undefined</code> or <code>NaN</code> in JSON.
     *
     * @return {@link JSONVoid#INSTANCE} which represents a <code>void</code> element.
     */
    public static JSONElement Void() {
        return JSONVoid.INSTANCE;
    }

    /**
     * Alias of {@link #Void()}.
     *
     * @return what {@link #Void()} returns.
     */
    public static JSONElement newInstance() {
        return Void();
    }

    /**
     * Get a new instance of {@link JSONElement} with specified original {@link Object}.
     *
     * @param o the original {@link Object}.
     * @return a new instance of {@link JSONElement} with specified original {@link Object}.
     * @throws Exception if error occurred while analyzing {@link Object}.
     */
    public static JSONElement newInstance(Object o) throws Exception {
        if (null == o) {
            return newInstance();
        } else if (o instanceof JSONElement) {
            return (JSONElement) o;
        } else {
            return ObjectAnalyzer.analyze(o);
        }
    }

    /**
     * Get a new instance of {@link JSONList}.
     *
     * @return a new instance of {@link JSONList}.
     */
    public static JSONElement newList() {
        return new JSONList();
    }

    /**
     * Get a new instance of {@link JSONList} with specified sub-elements.
     *
     * @param sub_elements the sub-elements.
     * @return a new instance of {@link JSONList} with specified sub-elements.
     */
    public static JSONElement newList(List<JSONElement> sub_elements) {
        return new JSONList(sub_elements);
    }

    /**
     * Get a new instance of {@link JSONMap}.
     *
     * @return a new instance of {@link JSONMap}.
     */
    public static JSONElement newMap() {
        return new JSONMap();
    }

    /**
     * Get a new instance of {@link JSONMap} with specified sub-elements.
     *
     * @param sub_elements the sub-elements.
     * @return a new instance of {@link JSONMap} with specified sub-elements.
     */
    public static JSONElement newMap(Map<Object, JSONElement> sub_elements) {
        return new JSONMap(sub_elements);
    }

    /**
     * Get a new instance of {@link JSONPrimitive}.
     *
     * @return a new instance of {@link JSONPrimitive}.
     */
    public static JSONElement newPrimitive() {
        return new JSONPrimitive();
    }

    /**
     * Get a new instance of {@link JSONPrimitive} with specified original value.
     *
     * @param v the original value.
     * @return a new instance of {@link JSONPrimitive} with specified original value.
     */
    public static JSONElement newPrimitive(Object v) {
        if (v instanceof JSONPrimitive) {
            return (JSONElement) v;
        } else if (v instanceof JSONElement) {
            throw new UnsupportedOperationException("Could not cast JSONPrimitive from " + ObjectUtil.getSimpleName(v));
        } else if (TypeUtil.isPrimitive(v) || v instanceof Number || v instanceof String) {
            return new JSONPrimitive(v);
        } else {
            throw new UnsupportedOperationException("Could not cast JSONPrimitive from " + ObjectUtil.getCanonicalName(v));
        }
    }

    /**
     * Returns whether this element is <code>void</code>.
     *
     * @return <code>true</code> if is <code>void</code>, <code>false</code> otherwise.
     */
    public boolean isVoid() {
        return this instanceof JSONVoid;
    }

    /**
     * Returns whether this element is empty.
     *
     * @return <code>true</code> if is empty, <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return this instanceof JSONVoid;
    }

    /**
     * Returns whether this element is an instance of {@link JSONList}.
     *
     * @return <code>true</code> if instance of {@link JSONList}, <code>false</code> otherwise.
     */
    public boolean isList() {
        return this instanceof JSONList;
    }

    /**
     * Returns whether this element is an instance of {@link JSONMap}.
     *
     * @return <code>true</code> if instance of {@link JSONMap}, <code>false</code> otherwise.
     */
    public boolean isMap() {
        return this instanceof JSONMap;
    }

    /**
     * Returns whether this element is an instance of {@link JSONPrimitive}.
     *
     * @return <code>true</code> if instance of {@link JSONPrimitive}, <code>false</code> otherwise.
     */
    public boolean isPrimitive() {
        return this instanceof JSONPrimitive;
    }

    /**
     * Get the {@link JSONElementType} of this element.
     *
     * @return {@link JSONElementType} of this element.
     */
    public JSONElementType getType() {
        if (isVoid()) {
            return JSONElementType.VOID;
        } else if (isList()) {
            return JSONElementType.LIST;
        } else if (isMap()) {
            return JSONElementType.MAP;
        } else if (isPrimitive()) {
            return JSONElementType.PRIMITIVE;
        } else {
            return JSONElementType.UNKNOWN;
        }
    }

    /**
     * Returns the number of sub-elements in this element. If this element contains more than {@link Integer#MAX_VALUE} sub-elements, returns {@link Integer#MAX_VALUE}.
     *
     * @return the number of sub-elements in this element.
     * @throws Exception if counting size is not available for this element.
     */
    public int size() throws Exception {
        throw new UnsupportedOperationException("Could not get size from " + ObjectUtil.getSimpleName(this));
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
     * @throws Exception if not supported.
     */
    public JSONElement peek(Object k) throws Exception {
        throw new UnsupportedOperationException("Could not peek from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Offers a sub-element to this element.
     *
     * @param v the sub-element.
     * @return this element itself.
     * @throws Exception if not supported.
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
     * @throws Exception if not supported.
     */
    public JSONElement offer(Object k, Object v) throws Exception {
        throw new UnsupportedOperationException("Could not offer k, v to " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@link List} that this element represents.
     *
     * @return the {@link List} that this element represents.
     * @throws Exception if this element does not represent a {@link List}.
     */
    public List<JSONElement> asList() throws Exception {
        throw new UnsupportedOperationException("Could not cast List from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@link Map} that this element represents.
     *
     * @return the {@link Map} that this element represents.
     * @throws Exception if this element does not represent a {@link Map}.
     */
    public Map<Object, JSONElement> asMap() throws Exception {
        throw new UnsupportedOperationException("Could not cast Map from " + ObjectUtil.getSimpleName(this));
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
     * Get the {@link String} that this element represents.
     *
     * @return the {@link String} that this element represents.
     * @throws Exception if this element does not represent a {@link String}.
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
     * Get the <code>int</code> that this element represents.
     *
     * @return the <code>int</code> that this element represents.
     * @throws Exception if this element does not represent an <code>int</code>.
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
     * Get the <code>char</code> that this element represents.
     *
     * @return the <code>char</code> that this element represents.
     * @throws Exception if this element does not represent a <code>char</code>.
     */
    public char asChar() throws Exception {
        throw new UnsupportedOperationException("Could not cast char from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@link Number} that this element represents.
     *
     * @return the {@link Number} that this element represents.
     * @throws Exception if this element does not represent a {@link Number}.
     */
    public Number asNumber() throws Exception {
        throw new UnsupportedOperationException("Could not cast Number from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@link BigInteger} that this element represents.
     *
     * @return the {@link BigInteger} that this element represents.
     * @throws Exception if this element does not represent a {@link BigInteger}.
     */
    public BigInteger asBigInteger() throws Exception {
        throw new UnsupportedOperationException("Could not cast BigInteger from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@link BigDecimal} that this element represents.
     *
     * @return the {@link BigDecimal} that this element represents.
     * @throws Exception if this element does not represent a {@link BigDecimal}.
     */
    public BigDecimal asBigDecimal() throws Exception {
        throw new UnsupportedOperationException("Could not cast BigDecimal from " + ObjectUtil.getSimpleName(this));
    }

}
