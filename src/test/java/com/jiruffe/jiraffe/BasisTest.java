package com.jiruffe.jiraffe;

import com.jiruffe.jiraffe.model.JSONElement;
import com.jiruffe.jiraffe.model.JSONElementType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jiruffe
 * 2019.02.18
 */
public class BasisTest {

    @Test
    public void testHashcode() {
        JSONElement e = JSONElement.newMap();
        assert 0 == e.hashCode();
        e.offer("", JSONElement.newList().offer(0));
        assert 0 != e.hashCode();
    }

    @Test
    public void testEquals() {
        JSONElement e1 = JSONElement.newMap();
        JSONElement e2 = JSONElement.newMap();
        assert e1.equals(e2);
        e1.offer("", JSONElement.newList().offer(0));
        e2.offer("", JSONElement.newList().offer(0));
        assert e1.equals(e2);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPeekFromPrimitive() {
        JSONElement.newPrimitive().peek(new Object());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOfferVToMap() {
        JSONElement.newMap().offer(JSONElement.newPrimitive());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOfferKVToList() {
        JSONElement.newList().offer(new Object(), JSONElement.newPrimitive());
    }

    @Test
    public void testInitializeList() {
        List<JSONElement> l = new ArrayList<>();
        l.add(JSONElement.theVoid());
        assert JSONElementType.VOID == JSONElement.newList(l).peek(0).getType();
    }

    @Test
    public void testInitializeMap() {
        Map<String, JSONElement> m = new HashMap<>();
        m.put("", JSONElement.theVoid());
        assert JSONElementType.VOID == JSONElement.newMap(m).peek("").getType();
    }

    @Test
    public void testIsEmpty() {
        assert JSONElement.newMap().isEmpty();
        assert JSONElement.newList().isEmpty();
        assert !JSONElement.newMap().offer("", JSONElement.theVoid()).isEmpty();
        assert !JSONElement.newList().offer(JSONElement.theVoid()).isEmpty();
    }

    @Test
    public void testEntriesKeysValues() {
        assert !JSONElement.newMap().offer("", JSONElement.theVoid()).entries().isEmpty();
        assert !JSONElement.newMap().offer("", JSONElement.theVoid()).keys().isEmpty();
        assert !JSONElement.newMap().offer("", JSONElement.theVoid()).values().isEmpty();
        assert !JSONElement.newList().offer(JSONElement.theVoid()).entries().isEmpty();
        assert !JSONElement.newList().offer(JSONElement.theVoid()).keys().isEmpty();
        assert !JSONElement.newList().offer(JSONElement.theVoid()).values().isEmpty();
    }

    @Test
    public void testPoll() {
        JSONElement m = JSONElement.newMap().offer("", JSONElement.theVoid());
        assert !m.isEmpty();
        assert JSONElementType.VOID == m.poll("").getType();
        assert m.isEmpty();
        JSONElement l = JSONElement.newList().offer(JSONElement.theVoid());
        assert !l.isEmpty();
        assert JSONElementType.VOID == l.poll(0).getType();
        assert l.isEmpty();
    }

    @Test
    public void testContains() {
        assert JSONElement.newMap().offer("", JSONElement.theVoid()).containsKey("");
        assert JSONElement.newList().offer(JSONElement.theVoid()).containsValue(JSONElement.theVoid());
    }

}
