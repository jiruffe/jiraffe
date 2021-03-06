/*
 *    Copyright 2018 Jiruffe
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

package com.jiruffe.jiraffe.model;

import com.jiruffe.jiraffe.analyzer.ObjectAnalyzer;
import com.jiruffe.jiraffe.analyzer.StringAnalyzer;
import com.jiruffe.jiraffe.util.ObjectUtil;
import com.jiruffe.jiraffe.util.StringUtil;
import com.jiruffe.jiraffe.util.TypeUtil;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents JSON element including JSON map {}, list [], or primitive value such as integer, string...
 *
 * @author Jiruffe
 * 2018.10.23
 */
public abstract class JSONElement implements Iterable<JSONElement.Entry> {

    /**
     * Converts this element to JSON {@link String}.
     *
     * @return JSON {@link String}.
     */
    @Override
    public String toString() {
        return asString();
    }

    /**
     * Converts this element to Java {@link Object}.
     *
     * @param target The target {@link Type}.
     * @param <T>    The target {@link Type}.
     * @return the target Java {@link Object}.
     */
    public <T> T toObject(Type target) {
        return ObjectAnalyzer.analyze(this, target);
    }

    /**
     * Alias of {@link #theVoid()}.
     *
     * @return what {@link #theVoid()} returns.
     * @deprecated use {@link #theVoid()}, {@link #newList()}, {@link #newList(List)}, {@link #newMap()}, {@link #newMap(Map)},
     * {@link #newPrimitive()}, {@link #newPrimitive(Object)} or {@link #newInstance(Object)} instead.
     */
    @Deprecated()
    public static JSONElement newInstance() {
        return theVoid();
    }

    /**
     * Get a new instance of {@link JSONElement} with specified original {@link Object}.
     *
     * @param o the original {@link Object}.
     * @return a new instance of {@link JSONElement} with specified original {@link Object}.
     */
    public static JSONElement newInstance(Object o) {
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
        if (null == sub_elements) {
            return newList();
        } else {
            return new JSONList(sub_elements);
        }
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
    public static JSONElement newMap(Map<?, JSONElement> sub_elements) {
        if (null == sub_elements) {
            return newMap();
        } else {
            return new JSONMap(sub_elements);
        }
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
        if (null == v) {
            return newPrimitive();
        } else if (v instanceof JSONPrimitive) {
            return (JSONElement) v;
        } else if (v instanceof JSONElement) {
            throw new ClassCastException("Could not cast JSONPrimitive from " + ObjectUtil.getSimpleName(v));
        } else if (TypeUtil.isPrimitive(v) || v instanceof Number || v instanceof String) {
            return new JSONPrimitive(v);
        } else {
            throw new ClassCastException("Could not cast JSONPrimitive from " + ObjectUtil.getCanonicalName(v));
        }
    }

    /**
     * Returns {@link JSONVoid#INSTANCE} which represents a {@code void} element,
     * also known as {@code null}, {@code undefined} or {@code NaN} in JSON.
     *
     * @return {@link JSONVoid#INSTANCE} which represents a {@code void} element.
     */
    public static JSONElement theVoid() {
        return JSONVoid.INSTANCE;
    }

    /**
     * Returns whether this element is empty.
     *
     * @return {@code true} if is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Could not check whether is empty or not from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Returns whether this element is {@code void}.
     *
     * @return {@code true} if is {@code void}, {@code false} otherwise.
     */
    public boolean isVoid() {
        return this instanceof JSONVoid;
    }

    /**
     * Returns whether this element is an instance of {@link JSONList}.
     *
     * @return {@code true} if instance of {@link JSONList}, {@code false} otherwise.
     */
    public boolean isList() {
        return this instanceof JSONList;
    }

    /**
     * Returns whether this element is an instance of {@link JSONMap}.
     *
     * @return {@code true} if instance of {@link JSONMap}, {@code false} otherwise.
     */
    public boolean isMap() {
        return this instanceof JSONMap;
    }

    /**
     * Returns whether this element is an instance of {@link JSONPrimitive}.
     *
     * @return {@code true} if instance of {@link JSONPrimitive}, {@code false} otherwise.
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
     * Returns the number of sub-elements in this element.
     * If this element contains more than {@link Integer#MAX_VALUE} sub-elements, returns {@link Integer#MAX_VALUE}.
     *
     * @return the number of sub-elements in this element.
     */
    public int size() {
        throw new UnsupportedOperationException("Could not get size from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get all the entries (key-value pair) of this element.
     *
     * @return a {@link Collection} of entries.
     */
    public Collection<Entry> entries() {
        throw new UnsupportedOperationException("Could not get entries from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get all the keys of this element.
     *
     * @return a {@link Collection} of keys.
     */
    public Collection<Object> keys() {
        throw new UnsupportedOperationException("Could not get keys from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get all the values of this element.
     *
     * @return a {@link Collection} of values.
     */
    public Collection<JSONElement> values() {
        throw new UnsupportedOperationException("Could not get values from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Returns the sub-element with specified key.
     *
     * @param k the specified key.
     * @return the sub-element.
     */
    public JSONElement peek(Object k) {
        throw new UnsupportedOperationException("Could not peek from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Remove the sub-element with specified key.
     *
     * @param k the specified key.
     * @return the removed sub-element.
     */
    public JSONElement poll(Object k) {
        throw new UnsupportedOperationException("Could not poll from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Offers a sub-element to this element.
     *
     * @param v the sub-element.
     * @return this element itself.
     */
    public JSONElement offer(Object v) {
        throw new UnsupportedOperationException("Could not offer v to " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Offers a sub-element with specified key to this element.
     *
     * @param k the key.
     * @param v the sub-element.
     * @return this element itself.
     */
    public JSONElement offer(Object k, Object v) {
        throw new UnsupportedOperationException("Could not offer k, v to " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Merge another element to this element.
     *
     * @param e the other element.
     * @return this element itself.
     */
    public JSONElement merge(JSONElement e) {
        throw new UnsupportedOperationException("Only merging JSONList into JSONList or merging JSONMap into JSONMap are supported");
    }

    /**
     * Returns if this element contains specified key.
     *
     * @param k the specified key.
     * @return {@code true} if this element contains specified key, {@code false} otherwise.
     */
    public boolean containsKey(Object k) {
        return false;
    }

    /**
     * Returns if this element contains specified sub-element.
     *
     * @param v the specified sub-element.
     * @return {@code true} if this element contains specified sub-element, {@code false} otherwise.
     */
    public boolean containsValue(Object v) {
        return equals(v);
    }

    /**
     * Get the {@link List} that this element represents.
     *
     * @return the {@link List} that this element represents.
     */
    public List<JSONElement> asList() {
        throw new ClassCastException("Could not cast List from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@link Map} that this element represents.
     *
     * @return the {@link Map} that this element represents.
     */
    public Map<Object, JSONElement> asMap() {
        throw new ClassCastException("Could not cast Map from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the original value that this element represents.
     *
     * @return the original value that this element represents.
     */
    public Object asValue() {
        return this;
    }

    /**
     * Returns the {@link String} that this element represents if instance of {@link JSONPrimitive},
     * or the JSON expression of this element otherwise.
     *
     * @return the {@link String} that this element represents or the JSON expression of this element.
     */
    public String asString() {
        return StringAnalyzer.analyze(this);
    }

    /**
     * Get the {@code byte} that this element represents.
     *
     * @return the {@code byte} that this element represents.
     */
    public byte asByte() {
        throw new ClassCastException("Could not cast byte from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@code short} that this element represents.
     *
     * @return the {@code short} that this element represents.
     */
    public short asShort() {
        throw new ClassCastException("Could not cast short from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@code int} that this element represents.
     *
     * @return the {@code int} that this element represents.
     */
    public int asInt() {
        throw new ClassCastException("Could not cast int from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@code long} that this element represents.
     *
     * @return the {@code long} that this element represents.
     */
    public long asLong() {
        throw new ClassCastException("Could not cast long from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@code float} that this element represents.
     *
     * @return the {@code float} that this element represents.
     */
    public float asFloat() {
        throw new ClassCastException("Could not cast float from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@code double} that this element represents.
     *
     * @return the {@code double} that this element represents.
     */
    public double asDouble() {
        throw new ClassCastException("Could not cast double from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@code boolean} that this element represents.
     *
     * @return the {@code boolean} that this element represents.
     */
    public boolean asBoolean() {
        throw new ClassCastException("Could not cast boolean from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@code char} that this element represents.
     *
     * @return the {@code char} that this element represents.
     */
    public char asChar() {
        throw new ClassCastException("Could not cast char from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@link Number} that this element represents.
     *
     * @return the {@link Number} that this element represents.
     */
    public Number asNumber() {
        throw new ClassCastException("Could not cast Number from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@link BigInteger} that this element represents.
     *
     * @return the {@link BigInteger} that this element represents.
     */
    public BigInteger asBigInteger() {
        throw new ClassCastException("Could not cast BigInteger from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Get the {@link BigDecimal} that this element represents.
     *
     * @return the {@link BigDecimal} that this element represents.
     */
    public BigDecimal asBigDecimal() {
        throw new ClassCastException("Could not cast BigDecimal from " + ObjectUtil.getSimpleName(this));
    }

    /**
     * Alias of {@link #toObject(Type)}.
     *
     * @param target the target {@link Type}.
     * @param <T>    the target {@link Type}.
     * @return the target Java {@link Object}.
     */
    public <T> T as(Type target) {
        return toObject(target);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        if (isVoid() || isEmpty()) {
            return 0;
        } else if (isPrimitive()) {
            return asValue().hashCode();
        } else if (isList()) {
            return asList().hashCode();
        } else if (isMap()) {
            return asMap().hashCode();
        }
        return toString().hashCode();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (null == obj) {
            return false;
        } else if (!(obj instanceof JSONElement)) {
            return false;
        } else if (getType() != ((JSONElement) obj).getType()) {
            return false;
        } else if (isVoid()) {
            return true;
        } else if (isPrimitive()) {
            return Objects.equals(asValue(), ((JSONElement) obj).asValue());
        } else if (isList()) {
            return Objects.equals(asList(), ((JSONElement) obj).asList());
        } else if (isMap()) {
            return Objects.equals(asMap(), ((JSONElement) obj).asMap());
        }
        return toString().equals(StringUtil.toString(obj instanceof JSONElement ? obj : newInstance(obj)));
    }

    /**
     * An element entry (key-value pair).
     *
     * @see JSONElement#entries()
     */
    public final class Entry {

        private final Object key;
        private final JSONElement element;

        Entry(Object key, JSONElement element) {
            this.key = key;
            this.element = element;
        }

        public Object getKey() {
            return key;
        }

        public JSONElement getElement() {
            return element;
        }

        @Override
        public int hashCode() {
            return (null == key ? 0 : key.hashCode()) ^ (null == element ? 0 : element.hashCode());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (null == obj) {
                return false;
            } else if (!(obj instanceof Entry)) {
                return false;
            }
            return Objects.equals(key, ((Entry) obj).key) && Objects.equals(element, ((Entry) obj).element);
        }

    }

}
