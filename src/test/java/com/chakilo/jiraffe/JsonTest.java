package com.chakilo.jiraffe;


import com.chakilo.jiraffe.model.JSONArray;
import com.chakilo.jiraffe.model.JSONObject;
import com.chakilo.jiraffe.model.JSONValue;
import org.junit.Test;

/**
 * 2019.02.13
 *
 * @author Chakilo
 */
public class JsonTest {

    @Test
    public void test0() {
        int a = 1;
        assert (((Object) a) instanceof Integer);
    }

    @Test
    public void test1() {
        Character a = 1;
        assert 1 == (long) (a);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test2() throws Exception {
        new JSONValue().peek(new Object());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test3() throws Exception {
        new JSONObject().offer(new JSONValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test4() throws Exception {
        new JSONArray().offer(new Object(), new JSONValue());
    }

    @Test
    public void test5() {
        assert "1".equals(((Object) new Integer(1)).toString());
    }

}
