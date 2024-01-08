package com.tfxing.persondaily;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Calculator {
    public static BigDecimal calculate(List<BigDecimal> numbers, List<String> operators) {
        Stack<BigDecimal> numberStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        for (int i = 0; i < operators.size(); i++) {
            String operator = operators.get(i);

            if (operator.equals("(")) {
                operatorStack.push(operator);
            } else if (operator.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    calculateTop(numberStack, operatorStack);
                }
                operatorStack.pop();
            } else if (isOperator(operator)) {
                while (!operatorStack.empty() && hasHigherPrecedence(operator, operatorStack.peek())) {
                    calculateTop(numberStack, operatorStack);
                }
                operatorStack.push(operator);
            } else {
                BigDecimal number = numbers.get(i);
                numberStack.push(number);
            }
        }

        while (!operatorStack.empty()) {
            calculateTop(numberStack, operatorStack);
        }

        return numberStack.pop();
    }

    private static boolean isOperator(String operator) {
        return operator.equals("+") || operator.equals("-");
    }

    private static boolean hasHigherPrecedence(String op1, String op2) {
        if (op2.equals("(") || op2.equals(")")) {
            return false;
        }
        return (op1.equals("*") && op2.equals("+"));
    }

    private static void calculateTop(Stack<BigDecimal> numberStack, Stack<String> operatorStack) {
        if (numberStack.isEmpty() || operatorStack.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression");
        }

        BigDecimal operand2 = numberStack.pop();
        BigDecimal operand1 = numberStack.pop();
        String operator = operatorStack.pop();

        BigDecimal result;
        switch (operator) {
            case "+":
                result = operand1.add(operand2);
                break;
            case "-":
                result = operand1.subtract(operand2);
                break;
            case "*":
                result = operand1.multiply(operand2);
                break;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }

        numberStack.push(result);
    }

    public static void main(String[] args) {
        List<BigDecimal> numbers = Arrays.asList(new BigDecimal(100), new BigDecimal(101), new BigDecimal(102),
                new BigDecimal(103), new BigDecimal(104));
        List<String> operators = Arrays.asList("[", "(", "+", ")", "(", "+", ")", "]", "*");

        BigDecimal result = calculate(numbers, operators);
        System.out.println(result);
    }
}
