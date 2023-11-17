/*
 * This class was created by <Pactivsa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */
package cn.kuzuanpa.ktfruaddon.code.formulaParser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isWhitespace;

//公式解析分词
public class tokenizer {
    //定义分词的类型
    public enum Type {
        //数字
        NUMBER,
        //变量：要求以字母开头，后面可以是字母或数字
        VARIABLE,
        //运算符
        OPERATOR,
        //左括号
        LEFT_BRACKET,
        //右括号
        RIGHT_BRACKET,
        //函数，也即前缀单目运算符
        FUNCTION
    }
    //类型对应的正则表达式，每次从头部开始匹配
    private static final String regexNumber = "\\d+(\\.\\d+)?";
    //变量匹配，要求以字母开头，后面可以是字母或数字或者_
    private static final String regexVariable = "[a-zA-Z]\\w*";
    //运算符匹配。包括+ - * / ^ 并且将 , 也视为运算符，其将多个数字/变量/参数列表聚合成参数列表
    private static final String regexOperator = "[\\+\\-\\*/\\^,]";
    //左括号匹配
    private static final String regexLeftBracket = "\\(";
    //右括号匹配
    private static final String regexRightBracket = "\\)";

    //分词结果的类型
    public static class Token {
        //分词的类型
        public Type type;
        //分词的字符串
        public String value;
        //构造函数
        public Token(Type type, String value) {
            this.type = type;
            this.value = value;
        }
        //重写toString方法
        @Override
        public String toString() {
            return String.format("%s[%s]", type, value);
        }

    }

    //分词函数
    public static ArrayList<Token> tokenize(String str) {
        //分词结果
        ArrayList<Token> tokens = new ArrayList<>();
        //当前处理的位置
        int pos = 0;
        //正则表达式匹配器
        Matcher matcher;
        //循环处理
        while (pos < str.length()) {
            //跳过空白字符
            if (isWhitespace(str.charAt(pos))) {
                pos++;
                continue;
            }
            //匹配数字
            matcher = Pattern.compile(regexNumber).matcher(str.substring(pos));
            if (matcher.lookingAt()) {
                tokens.add(new Token(Type.NUMBER, matcher.group()));
                pos += matcher.end();
                continue;
            }
            //匹配变量
            matcher = Pattern.compile(regexVariable).matcher(str.substring(pos));
            if (matcher.lookingAt()) {
                tokens.add(new Token(Type.VARIABLE, matcher.group()));
                pos += matcher.end();
                continue;
            }
            //匹配运算符
            matcher = Pattern.compile(regexOperator).matcher(str.substring(pos));
            if (matcher.lookingAt()) {
                tokens.add(new Token(Type.OPERATOR, matcher.group()));
                pos += matcher.end();
                continue;
            }
            //匹配左括号
            matcher = Pattern.compile(regexLeftBracket).matcher(str.substring(pos));
            if (matcher.lookingAt()) {
                tokens.add(new Token(Type.LEFT_BRACKET, matcher.group()));
                pos += matcher.end();
                continue;
            }
            //匹配右括号
            matcher = Pattern.compile(regexRightBracket).matcher(str.substring(pos));
            if (matcher.lookingAt()) {
                tokens.add(new Token(Type.RIGHT_BRACKET, matcher.group()));
                pos += matcher.end();
                continue;
            }
            //如果没有匹配上，抛出异常
            throw new RuntimeException("Unexpected character " + str.charAt(pos));
        }
        return tokens;
    }

    //测试
    public static void main(String[] args) {
        String str = "1+2*(3+fuc_233(-4,3,2,2))/(sin(x1+y1)) ";
        System.out.println(tokenize(str));
    }
}
