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

    @Test(expected = UnsupportedOperationException.class)
    public void test1() {
        JSONElement.newPrimitive().peek(new Object());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test2() {
        JSONElement.newMap().offer(JSONElement.newPrimitive());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test3() {
        JSONElement.newList().offer(new Object(), JSONElement.newPrimitive());
    }

    @Test
    public void test4() {
        List<JSONElement> l = new ArrayList<>();
        l.add(JSONElement.theVoid());
        assert JSONElementType.VOID == JSONElement.newList(l).peek(0).getType();
    }

    @Test
    public void test5() {
        Map<String, JSONElement> m = new HashMap<>();
        m.put("", JSONElement.theVoid());
        assert JSONElementType.VOID == JSONElement.newMap(m).peek("").getType();
    }

    @Test
    public void test6() {
        assert JSONElement.newMap().isEmpty();
        assert JSONElement.newList().isEmpty();
        assert !JSONElement.newMap().offer("", JSONElement.theVoid()).isEmpty();
        assert !JSONElement.newList().offer(JSONElement.theVoid()).isEmpty();
    }

    @Test
    public void test7() {
        assert !JSONElement.newMap().offer("", JSONElement.theVoid()).entries().isEmpty();
        assert !JSONElement.newMap().offer("", JSONElement.theVoid()).keys().isEmpty();
        assert !JSONElement.newMap().offer("", JSONElement.theVoid()).values().isEmpty();
        assert !JSONElement.newList().offer(JSONElement.theVoid()).entries().isEmpty();
        assert !JSONElement.newList().offer(JSONElement.theVoid()).keys().isEmpty();
        assert !JSONElement.newList().offer(JSONElement.theVoid()).values().isEmpty();
    }

    @Test
    public void test8() {
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
    public void test9() {
        assert JSONElement.newMap().offer("", JSONElement.theVoid()).containsKey("");
        assert JSONElement.newList().offer(JSONElement.theVoid()).containsValue(JSONElement.theVoid());
    }

    @Test
    public void test10() {
        assert true;
    }

}
