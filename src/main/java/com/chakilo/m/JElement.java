package com.chakilo.m;

import java.io.Serializable;

/**
 * 2018.10.23
 *
 * 元素
 *
 * @author Chakilo
 */
public abstract class JElement implements Serializable, Iterable<JElement> {

    /**
     * 原始字符串
     */
    protected String __original_string;

    /**
     * 获取子元素
     * @param k 键
     * @return 子元素
     */
    public JElement peek(Object k) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * 加入元素
     * @param v 子元素
     * @return 自身
     */
    public JElement offer(Object v) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * 加入元素
     * @param k 键
     * @param v 子元素
     * @return 自身
     */
    public JElement offer(Object k, Object v) throws Exception {
        throw new UnsupportedOperationException();
    }

}
