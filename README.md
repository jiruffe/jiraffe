# jiraffe

A Java library for JSON conversion.

## How-to

```java
String s_single = "[123,123.45,'123.45','2019-01-02 03:04:05',true,false,null]";
String s_double = "[123,123.45,\"123.45\",\"2019-01-02 03:04:05\",true,false,null]";
JSONElement element = JSON.deserialize(s_single);
assert 123 == element.peek(0).asInt();
assert s_double.equals(element.toString());
```
