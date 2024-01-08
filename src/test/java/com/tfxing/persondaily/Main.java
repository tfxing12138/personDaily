package com.tfxing.persondaily;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> infixExpression = Arrays.asList("(", "(", "100", "+", "101", ")", "+", "(", "102", "+", "103", ")", ")", "*", "104","+","105");
        System.out.println(infixExpression);
        List<String> rpnExpression = convertToRPN(infixExpression);
        System.out.println(rpnExpression);
        int result = evaluateRPN(rpnExpression);
        System.out.println(result);
    }

    public static List<String> convertToRPN(List<String> infixExpression) {
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);

        Stack<String> operatorStack = new Stack<>();
        List<String> output = new ArrayList<>();

        for (String token : infixExpression) {
            if (isNumber(token)) {
                output.add(token);
            } else if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    output.add(operatorStack.pop());
                }
                operatorStack.pop(); // 弹出左括号
            } else if (isOperator(token)) {
                while (!operatorStack.isEmpty() && precedence.getOrDefault(operatorStack.peek(), 0) >= precedence.get(token)) {
                    output.add(operatorStack.pop());
                }
                operatorStack.push(token);
            }
        }

        while (!operatorStack.isEmpty()) {
            output.add(operatorStack.pop());
        }

        return output;
    }

    public static boolean isNumber(String token) {
        return token.matches("\\d+");
    }

    public static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    public static int evaluateRPN(List<String> rpnExpression) {
        Stack<Integer> stack = new Stack<>();

        for (String token : rpnExpression) {
            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else if (isOperator(token)) {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result = evaluate(operand1, operand2, token);
                stack.push(result);
            }
        }

        return stack.pop();
    }

    public static int evaluate(int operand1, int operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

}
