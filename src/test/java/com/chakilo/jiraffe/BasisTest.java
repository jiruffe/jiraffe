package com.chakilo.jiraffe;

import com.chakilo.jiraffe.model.JSONElement;
import com.chakilo.jiraffe.util.ObjectUtil;
import org.junit.Test;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 2019.02.18
 *
 * @author Chakilo
 */
public class BasisTest {

    @Test
    public void test0() {
        int a = 1;
        assert (((Object) a) instanceof Integer);
    }

    @Test
    public void test1() {
        Character a = 1;
        assert 1d == (double) (a);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test2() throws Exception {
        JSONElement.newValue().peek(new Object());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test3() throws Exception {
        JSONElement.newObject().offer(JSONElement.newValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test4() throws Exception {
        JSONElement.newList().offer(new Object(), JSONElement.newValue());
    }

    @Test
    public void test5() {
        assert "1".equals(((Object) new Integer(1)).toString());
    }

    @Test
    public void test6() {
        System.out.println(String.valueOf(Long.MAX_VALUE).length());
    }

    @Test
    public void test7() {
        Object o = null;
        System.out.println(ObjectUtil.getCanonicalName(o));
    }

    @Test
    public void test8() {
        Hashtable d = new Hashtable();
        System.out.println(d instanceof Map);
    }

}
