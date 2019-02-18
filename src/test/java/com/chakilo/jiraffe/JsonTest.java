package com.chakilo.jiraffe;


import com.chakilo.jiraffe.model.base.JSONElement;
import org.junit.Test;

/**
 * 2019.02.13
 *
 * @author Chakilo
 */
public class JsonTest {

    @Test
    public void test1() {

        JSONElement element = JSON.deserialize("{'a':'b','c':{'d':'e'},'f':{'g':\"h\"},'i':[{'j':'k','l':'m'},'n']}");

        assert null != element;

    }

    @Test
    public void test2() {

        JSONElement element = JSON.deserialize("[123,123.45,'123.45','2019-01-02 03:04:05',true,false]");

        assert null != element;

        assert "[123,123.45,\"123.45\",\"2019-01-02 03:04:05\",true,false]".equals(element.toString());

    }

}
