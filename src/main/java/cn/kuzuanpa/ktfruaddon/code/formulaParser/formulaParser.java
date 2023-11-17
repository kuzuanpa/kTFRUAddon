package cn.kuzuanpa.ktfruaddon.code.formulaParser;

import java.util.ArrayList;
import java.util.HashMap;
public class formulaParser {

    private final HashMap<String,Double> variables;
    public formulaParser(HashMap<String,Double> variables) {
        this.variables = variables;
    }
    public formulaParser(){
        this.variables = new HashMap<>();
    }
    public double eval(String str) {
        //分词
        ArrayList<tokenizer.Token> tokens = tokenizer.tokenize(str);
        //解析
        parser m_parser = new parser(this.variables);
        //输出运算结果
        return m_parser.parse(tokens);
    }
}
