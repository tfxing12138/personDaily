package com.tfxing.persondaily;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class PolandNotation {
    public static void main(String[] args) {
//       完成一个中缀表达式转成后缀表达式的功能
//      1+((2+3))×4-5  -----> 1 2 3 + 4 × + 5 -
//      我们对字符串操作不方便，将中缀表达式放入List集合找那个
        String expression = "1+((2+3)+(1+1))×4-5";
//       转换成对应的list
        List<String>  InfixExpressionList  =toInfixExpressionList(expression);
        System.out.println("中缀表达式的List="+InfixExpressionList);
//      转换成后缀表达式的List
        List<String> suffixExpressionList  = parseSuffixExpressionList(InfixExpressionList);
        System.out.println("后缀表达式的List"+suffixExpressionList);
        System.out.println("结果:"+ calculate(suffixExpressionList));
 
 
 
    }
//  将中缀表达式转成对应的List
    public static  List<String> toInfixExpressionList(String s){
//       定义List存放中缀表达式对应内容
        List<String> ls = new ArrayList<>();
        int i =0;
        String str;  //多位数拼接  因为计算的时候不可能光有一位数
        char c;      // 没遍历一个字符，就放入c中
        do{
            if( (c=s.charAt(i))<48 || (c=s.charAt(i))>57){
//             运行到这里肯定说明char不是0....9
               ls.add(""+c);
               i++;
            }else {
//              这种情况就是数字了，但是我们需要考虑度多位数
//              保证是空串
                str="";
                while (i<s.length() && (c=s.charAt(i))>=48 && (c=s.charAt(i))<=57 ){
                    str +=c;  //拼接
                    i++;
                }
                ls.add(str);
 
            }
 
        }while (i<s.length());
        return  ls;
 
    }
 
//  中缀表达式转换成后缀表达式
    public static List<String>  parseSuffixExpressionList(List<String> ls){
//      定义两个栈
        Stack<String> s1 = new Stack<>();  //符号栈
//      说明：我们s2栈只进栈，只有最后的时候倒序输出，所以我们选择List集合就可以了，方便
        List<String> s2 =new ArrayList<>();//储存中间结果的栈
 
        for(String item:ls){
//           如果是一个数就进栈s2
            if(item.matches("\\d+")){
//              遇到操作数时，将其压s2；
                s2.add(item);
            }else if(item.equals("(")){
//              如果是左括号“(”，则直接压入s1
                s1.push(item);
            }else if(item.equals(")")){
//               如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，
//               直到遇到左括号为止，此时将这一对括号丢弃
                 while (!s1.peek().equals("(")){
                     s2.add(s1.pop());
                 }
//               将s1栈的左括号丢掉
                s1.pop();
 
            }else {
 
//               这种情况是匹配到字符的时候
//               优先级比栈顶运算符的高，也将运算符压入s1；
//               否则（运算符的优先级小于等于），将s1栈顶的运算符弹出并压入到s2中，再与s1中新的栈顶运算符相比较，如果遇到空栈或者（，直接进栈
                while (s1.size() !=0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)){
                    s2.add(s1.pop());
                }
//               出循环的话，item的运算符肯定比顶部的高或者栈空了
//               优先级比栈顶运算符的高，也将运算符压入s1；
                s1.push(item);
//                if(s1.peek()==null || Operation.getValue(item)>Operation.getValue(s1.peek())){
//                    s1.push(item);
//                }
            }
        }
 
//       将s1中剩余的运算符依次弹出并压入s2
        while (s1.size()!=0){
            s2.add(s1.pop());
        }
//      因为是存放到列表里面，就不需要逆序输出了，现在已经是逆序表达式
        return s2;
 
    }
 
 
//    将一个逆波兰表达式，依次将数据和运算符放到ArrayList中
    public static List<String> getListString (String suffixExpression){
//      分割
        String[] split = suffixExpression.split(" ");
        List<String> list = new ArrayList<>();
//      用这个api更快
        Collections.addAll(list, split);
 
        return list;
 
    }
 
//  完成对逆波兰表达式的计算，我们已经变成了对List集合的遍历
    public static  int calculate(List<String> ls){
//      此时只需要一个栈就行了
        Stack<String> stack = new Stack<>();
//      遍历ls
        for (String item:ls){
            if(item.matches("\\d+")){
//               入栈
                stack.push(item);
            }else {
//               pop出两个数，并运算再入栈
                int num1 = Integer.parseInt(stack.pop());
                int num2 = Integer.parseInt(stack.pop());
                int res=0;
                if(item.equals("+")){
                    res = num1 + num2;
                }else if(item.equals("-")){
                    res =num2-num1;
                }else if(item.equals("×")){
                    res =num1*num2;
                }else if(item.equals("/")){
                    res = num2/num1;
                }else {
                    throw new  RuntimeException("运算符有误");
                }
//              结果入栈
                stack.push(res+"");
            }
        }
//        最后留在stack中的数据是运算结果
        return Integer.parseInt(stack.pop());
 
    }
 
}
 
 
 
class Operation{
    private static int ADD=1;  //加
    private static int SUB=1;  //减
    private static int MUL=2;  //乘
    private static int DIV=2;  //除
 
//   写方法返回对应的优先级数字
    public static  int getValue(String operation){
        operation= operation.trim();
 
        int result =0;
        switch (operation){
            case "+":
                result =ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "×":
                result=MUL;
                break;
            case "/":
                result =DIV;
                break;
            default:
                System.out.println(operation);
                System.out.println("不存在该运算符");
                break;
        }
        return result;
    }
 
}