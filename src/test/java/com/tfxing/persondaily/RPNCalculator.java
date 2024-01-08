package com.tfxing.persondaily;

import java.math.BigDecimal;
import java.util.Stack;

public class RPNCalculator {
    public static BigDecimal calculate(String[] tokens) {
        Stack<BigDecimal> numberStack = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                BigDecimal number = new BigDecimal(token);
                numberStack.push(number);
            } else if (isOperator(token)) {
                BigDecimal operand2 = numberStack.pop();
                BigDecimal operand1 = numberStack.pop();

                BigDecimal result;
                switch (token) {
                    case "+":
                        result = operand1.add(operand2);
                        break;
                    case "-":
                        result = operand1.subtract(operand2);
                        break;
                    case "*":
                        result = operand1.multiply(operand2);
                        break;
                    case "/":
                        result = operand1.divide(operand2, 10, BigDecimal.ROUND_HALF_UP);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator: " + token);
                }

                numberStack.push(result);
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        if (numberStack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return numberStack.pop();
    }

    private static boolean isNumber(String token) {
        try {
            new BigDecimal(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    public static void main(String[] args) {
        String[] tokens = {"3", "4", "+", "2", "*", "1", "+"};
        BigDecimal result = calculate(tokens);
        System.out.println(result);
    }
}
