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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
//对分词结果进行解析
public class parser {

    //当前处理的位置
    private int pos;
    //变量转换表
    private final HashMap<String,Double> varTable;

    private ArrayDeque<tokenizer.Token> operatorStack = new ArrayDeque<>();
    //操作数栈,操作数可能包括数字，参数列表
    private ArrayDeque<Object> operandStack = new ArrayDeque<>();

    //构造函数
    public parser(HashMap<String,Double> DefineVarTable ) {
        this.pos = 0;
        varTable = new HashMap<>();
        varTable.put("PI",Math.PI);
        varTable.putAll(DefineVarTable);
    }

    public parser(){
        this.pos = 0;
        varTable = new HashMap<>();
        varTable.put("PI",Math.PI);
    }

    //输出Pos附近的token的值
    private String PrintPosToken(ArrayList<tokenizer.Token> tokens){
        int halfLen = 3;
        int start = Math.max(0,pos-halfLen);
        int end = Math.min(pos+halfLen,tokens.size());
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(tokens.get(i).value);
        }
        return sb.toString();
    }

    private int Priority(tokenizer.Token op){
        //定义优先级

        int LeftBracket = 0;
        int RightBracket = 1;
        int Comma = 1;     //逗号优先级
        int Add = 2;
        int Sub = 2;
        int Mul = 3;
        int Div = 3;
        int Func = 4;       //所有函数视为单目运算符
        int Power = 5;      //幂运算符

        tokenizer.Type tp = op.type;
        String value = op.value;
        int priority = 0;

        switch (tp){
            case LEFT_BRACKET:
                priority = LeftBracket;
                break;
            case RIGHT_BRACKET:
                priority = RightBracket;
                break;
            case OPERATOR:
                switch (value){
                    case "+":
                        priority = Add;
                        break;
                    case "-":
                        priority = Sub;
                        break;
                    case "*":
                        priority = Mul;
                        break;
                    case "/":
                        priority = Div;
                        break;
                    case "^":
                        priority = Power;
                        break;
                    case ",":
                        priority = Comma;
                        break;
                    default:
                }
                break;
            default:
                priority = Func;
        }
        return priority;
    }
    //运算符优先级对比
    private Boolean ifTopHigher(tokenizer.Token stackTop,tokenizer.Token current){
        //栈顶运算符优先级高于或者等于当前运算符优先级
        return Priority(stackTop) >= Priority(current);
    }

    private Boolean isBinaryOperator(String str){
        //判断是否为双目运算符
        ArrayList<String> BinaryOperators = new ArrayList<>();
        BinaryOperators.add("+");
        BinaryOperators.add("-");
        BinaryOperators.add("*");
        BinaryOperators.add("/");
        BinaryOperators.add("^");
        BinaryOperators.add(",");
        return BinaryOperators.contains(str);
    }

    private double FuncCalculate(String func, ArrayList<Double> args){
        double result = 0;
        switch (func) {
            case "NEG":
                result = -args.get(0);
                break;
            case "POS":
                result = args.get(0);
                break;
            case "sin":
                result = Math.sin(args.get(0));
                break;
            case "cos":
                result = Math.cos(args.get(0));
                break;
            case "tan":
                result = Math.tan(args.get(0));
                break;
            case "asin":
                result = Math.asin(args.get(0));
                break;
            case "acos":
                result = Math.acos(args.get(0));
                break;
            case "atan":
                result = Math.atan(args.get(0));
                break;
            case "sinh":
                result = Math.sinh(args.get(0));
                break;
            case "cosh":
                result = Math.cosh(args.get(0));
                break;
            case "tanh":
                result = Math.tanh(args.get(0));
                break;
            case "log":
                result = Math.log(args.get(0)) / Math.log(args.get(1));
                break;
            case "log10":
                result = Math.log10(args.get(0));
                break;
            case "log2":
                result = Math.log(args.get(0)) / Math.log(2);
                break;
            case "exp":
                result = Math.exp(args.get(0));
                break;
            case "sqrt":
                result = Math.sqrt(args.get(0));
                break;
            case "abs":
                result = Math.abs(args.get(0));
                break;
            case "ceil":
                result = Math.ceil(args.get(0));
                break;
            case "floor":
                result = Math.floor(args.get(0));
                break;
            case "round":
                result = Math.round(args.get(0));
                break;
            case "min":
                result = Math.min(args.get(0), args.get(1));
                break;
            case "max":
                result = Math.max(args.get(0), args.get(1));
                break;
            case "atan2":
                result = Math.atan2(args.get(0), args.get(1));
                break;
            default:
                throw new IllegalArgumentException("未知的函数或变量: " + func);
        }
        return result;
    }

    //双目运算符计算
    private double BinaryCalculate(String op, double a, double b){
        double result = 0;
        switch (op){
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            case "^":
                result = Math.pow(a, b);
                break;
            default:
        }
        return result;
    }

    private ArrayList<Double> MergeArgs(double a,double b){
        // 诸如 1,2 ---> [1,2]
        ArrayList<Double> args = new ArrayList<>();
        args.add(a);
        args.add(b);
        return args;
    }

    private ArrayList<Double> MergeArgs(ArrayList<Double> left,double right){
        //在诸如 1,2,3时，先聚合为[1,2],3 ---> [1,2,3]
        left.add(right);
        return left;
    }

    private ArrayList<Double> MergeArgs(ArrayList<Double> left,ArrayList<Double> right){
        //在诸如 1,2,3,4时，先聚合为[1,2],3,4 ---> [1,2,3,4]
        left.addAll(right);
        return left;
    }

    //处理一次操作数
    private void process(){
        //弹出运算符栈
        tokenizer.Token opToken = operatorStack.pop();
        String op = opToken.value;
        //如果是双目运算符，弹出两个操作数
        if(opToken.type == tokenizer.Type.OPERATOR){
            Object operand2 = operandStack.pop();
            Object operand1 = operandStack.pop();
            //若弹出的是列表，进行参数列表的聚合
            if (operand1 instanceof ArrayList){
                ArrayList<Double> args = (ArrayList<Double>) operand1;
                //若弹出的是列表，进行参数列表的聚合
                if (operand2 instanceof ArrayList){
                    ArrayList<Double> args2 = (ArrayList<Double>) operand2;
                    ArrayList<Double> result = MergeArgs(args,args2);
                    operandStack.push(result);
                }
                //若弹出的是双目运算符，则计算双目运算符的值
                else if (operand2 instanceof Double) {
                    double b = (double) operand2;
                    ArrayList<Double> result = MergeArgs(args,b);
                    operandStack.push(result);
                }
            }
            //若弹出的是双目运算符，则计算双目运算符的值
            else if (operand1 instanceof Double) {
                double a = (double) operand1;
                double b = (double) operand2;
                if( op.equals(",") ){
                    ArrayList<Double> result = MergeArgs(a,b);
                    operandStack.push(result);
                }
                else {
                    double result = BinaryCalculate(op, a, b);
                    operandStack.push(result);
                }
            }
        }
        else {
            //若弹出的是函数，则计算函数值
            Object operand = operandStack.pop();
            if (operand instanceof ArrayList) {
                ArrayList<Double> args = (ArrayList<Double>) operand;
                double result = FuncCalculate(op, args);
                operandStack.push(result);
            }
            else {
                double a = (double) operand;
                double result = FuncCalculate(op, new ArrayList<Double>(){{
                    add(a);
                }});
                operandStack.push(result);
            }
        }

    }
    //解析函数
    public double parse(ArrayList<tokenizer.Token> tokens) {
        //分词结果
        //运算符栈，可能包括 OPERATOR , LEFT_BRACKET, RIGHT_BRACKET和VARIABLE,都以String形式存储
        this.operatorStack = new ArrayDeque<>();
        //操作数栈,操作数可能包括数字，参数列表
        this.operandStack = new ArrayDeque<>();
        this.pos = 0;

        boolean LastTokenIsOperator = false; //若连续有两个op,则后一个op为单目运算符

        try {
            //循环处理
            while (pos < tokens.size()) {
                //获取当前的token
                tokenizer.Token token = tokens.get(pos);
                tokenizer.Type tp = token.type;
                String value = token.value;
                //处理数字
                if (tp == tokenizer.Type.NUMBER) {
                    //将数字压入操作数栈
                    operandStack.push(Double.parseDouble(value));
                    LastTokenIsOperator = false;
                }
                //处理变量
                else if (tp == tokenizer.Type.VARIABLE) {
                    //查找变量转换表，不在变量转换表中的变量将被视为Function，即单目运算符
                    if (varTable.containsKey(value)) {
                        operandStack.push(varTable.get(value));
                        LastTokenIsOperator = false;
                    } else {
                        operatorStack.push(token);
                        LastTokenIsOperator = true;
                    }
                }
                //处理操作符
                else if (tp == tokenizer.Type.OPERATOR) {
                    //处理+ -,若前一个token是操作符或者左括号，则当前操作符为一种函数
                    if (value.equals("+") && LastTokenIsOperator) {
                        operatorStack.push(new tokenizer.Token(tokenizer.Type.FUNCTION, "POS"));
                    } else if (value.equals("-") && LastTokenIsOperator) {
                        operatorStack.push(new tokenizer.Token(tokenizer.Type.FUNCTION, "NEG"));
                    }
                    //双目运算符
                    else if (isBinaryOperator(value)) {
                        //若当前操作符优先级高于栈顶操作符，则将当前操作符压入栈
                        if (operatorStack.isEmpty() || ! ifTopHigher(operatorStack.peek(),token)) {
                            operatorStack.push(token);
                        }
                        //否则，弹出栈顶操作符，计算栈顶操作符的值
                        else {
                            process();
                            //将当前操作符压入栈
                            operatorStack.push(token);
                        }
                    }
                    //非POS NEG的单目运算符视为函数
                    else {
                        operatorStack.push(new tokenizer.Token(tokenizer.Type.FUNCTION, value));
                    }

                    LastTokenIsOperator = true;
                }
                //左括号
                else if (tp == tokenizer.Type.LEFT_BRACKET) {
                    operatorStack.push(token);
                    LastTokenIsOperator = true;
                }
                //右括号
                else if (tp == tokenizer.Type.RIGHT_BRACKET) {
                    //弹出栈顶操作符，直到遇到左括号
                    while (!operatorStack.isEmpty() && operatorStack.peek().type != tokenizer.Type.LEFT_BRACKET) {
                        process();
                    }
                    //弹出左括号
                    operatorStack.pop();
                    LastTokenIsOperator = false;
                }
                else {
                    throw new IllegalArgumentException("未知的token类型: " + tp);
                }
                //处理下一个token
                pos++;
            }
            //处理剩余的操作符
            while (!operatorStack.isEmpty()) {
                process();
            }
        }
        catch (
                Exception e) {
            System.out.println("表达式错误: " + e.getMessage() + " 发生于 " + PrintPosToken(tokens) +
                    " 附近，当前字符为 " + tokens.get(pos-1).toString());
            throw e;

        }
        //返回最终结果
        return (double) operandStack.pop();
    }

}
