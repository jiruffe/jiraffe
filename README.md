
[![Build Status](https://travis-ci.org/jiruffe/jiraffe.svg?branch=master)](https://travis-ci.org/chakilo/jiraffe)
[![Coverage Status](https://coveralls.io/repos/github/chakilo/jiraffe/badge.svg?branch=master)](https://coveralls.io/github/chakilo/jiraffe?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/3e4beccbed464f67b89e3c8b4c6b8ebb)](https://www.codacy.com/app/chakilo/jiraffe?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=chakilo/jiraffe&amp;utm_campaign=Badge_Grade)

# Jiraffe

A simple Java library for JSON conversion.

## Examples

  * Parsing JSON

    ```java
    JSONElement element = JSON.deserialize("{a:{b:{c:{d:{e:'f'}}}}}");
    assert "f".equals(element.peek("a")
                             .peek("b")
                             .peek("c")
                             .peek("d")
                             .peek("e")
                             .asString());
    ```

  * Creating JSON

    ```java
    JSONElement element = JSONElement.newList();
    element.offer(123)
           .offer(123.45)
           .offer("123.45")
           .offer("2019-01-02 03:04:05")
           .offer(true)
           .offer(false)
           .offer(null)
           .offer(JSONElement.newMap());
    assert "[123,123.45,\"123.45\",\"2019-01-02 03:04:05\",true,false,null,{}]".equals(element.toString());
    ```

  * Parsing JSON to Java Object or stringifying an Object.

    ```java
    class CModel {
        public int a;
    }
    class DModel extends CModel {
        public String b;
        public int[] c;
        public String[] d;
        public List<EModel> e;
        public Map<String, String> f;
    }
    class EModel {
        public int a;
    }
    
    void test1() {
        DModel d = new DModel();
        d.a = 1;
        d.b = "bbb";
        d.c = new int[]{1, 2, 3};
        d.d = new String[]{"a", "b", "c"};
        EModel e = new EModel();
        e.a = 5;
        d.e = new ArrayList<>();
        d.e.add(e);
        d.f = new HashMap<>();
        d.f.put("aaaa", "bbbb");
    
        assert "{\"a\":1,\"b\":\"bbb\",\"c\":[1,2,3],\"d\":[\"a\",\"b\",\"c\"],\"e\":[{\"a\":5}],\"f\":{\"aaaa\":\"bbbb\"}}"
            .equals(JSON.stringify(d));
    }
    void test2() {
        DModel d = JSON.parse("{\"a\":1,\"b\":\"bbb\",\"c\":[1,2,3],\"d\":[\"a\",\"b\",\"c\"],\"e\":[{\"a\":5}],\"f\":{\"aaaa\":\"bbbb\"}}", DModel.class);
        
        assert null != d;
        assert 1 == d.a;
        assert "bbb".equals(d.b);
        assert Arrays.equals(new int[]{1, 2, 3}, d.c);
        assert Arrays.equals(new String[]{"a", "b", "c"}, d.d);
        assert null != d.e;
        assert 1 == d.e.size();
        assert d.e.get(0).a == 5;
        assert null != d.f;
        assert 1 == d.f.size();
        assert "bbbb".equals(d.f.get("aaaa"));
    }
    ```
