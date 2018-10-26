package com.chakilo;

import com.chakilo.m.JObject;

import java.util.List;

/**
 * 2018.10.25
 *
 * @author Chakilo
 */
final class Analyser {

    /**
     * 状态机
     */
    enum AnalysisStatus {
        /**
         * 普通状态
         */
        DEFAULT,
        /**
         * 对象状态
         * {}
         */
        IN_OBJECT,
        /**
         * 数组状态
         * []
         */
        IN_ARRAY
    }

    /**
     * token的类型
     */
    enum TokenType {
        /**
         * 普通token
         */
        DEFAULT,
        /**
         * 逗号
         * ,
         */
        COMMA,
        /**
         * 冒号
         * :
         */
        COLON,
        /**
         * 引号
         * '
         * "
         */
        QUOTE,
        /**
         * 左花括号
         * {
         */
        BRACE_LEFT,
        /**
         * 右花括号
         * }
         */
        BRACE_RIGHT,
        /**
         * 左方括号
         * [
         */
        SQUARE_BRACKET_LEFT,
        /**
         * 右方括号
         * ]
         */
        SQUARE_BRACKET_RIGHT
    }

    /**
     * token
     */
    class Token {

        /**
         * token的类型
         */
        TokenType token_type;
        /**
         * 原始字符串
         */
        String original_string;
        /**
         * 起始索引
         */
        long index_start;
        /**
         * 结束索引
         */
        long index_end;

    }

    /**
     * 分析json 得到token列表
     * @param json
     * @return
     */
    List<Token> tokenize(String json) {

        return null;
    }

    JElement parseObject(List<Token> tokens) {
        return null;
    }

    JElement parseArray(List<Token> tokens) {
        return null;
    }

}
