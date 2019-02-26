package com.chakilo.jiraffe;

import com.chakilo.jiraffe.model.DModel;
import com.chakilo.jiraffe.model.JSONElement;
import com.chakilo.jiraffe.util.ObjectUtil;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Chakilo
 * 2019.02.18
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
        JSONElement.newPrimitive().peek(new Object());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test3() throws Exception {
        JSONElement.newMap().offer(JSONElement.newPrimitive());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test4() throws Exception {
        JSONElement.newList().offer(new Object(), JSONElement.newPrimitive());
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

    @Test
    public void test9() throws NoSuchFieldException {
        assert DModel.class.getField("b").getGenericType() instanceof Class;
        assert DModel.class.getField("e").getGenericType() instanceof ParameterizedType;
    }

}
