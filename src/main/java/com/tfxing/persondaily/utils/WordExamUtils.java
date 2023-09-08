package com.tfxing.persondaily.utils;

import java.util.*;

public class WordExamUtils {

    public WordExamUtils() {
        numbers = new ArrayList<>();
        random = new Random();

        // 初始化数字列表，包含0到99的所有数字，每个数字出现2次
        for (int i = 0; i < wordList.size(); i++) {
            numbers.add(i);
            numbers.add(i);
        }
    }

    List<String> wordList = Arrays.asList(
            "物种:species",
            "辩解:explanation",
            "显著:significant",
            "传统的:tradition",
            "证据:evidence",
            "温度:temperature",
            "提供:provide",
            "解释:interpretation",
            "支持:support",
            "视野:view");

    public Integer size() {
        return wordList.size();
    }

    public Integer roundSize() {
        return numbers.size();
    }

    private List<Integer> numbers;
    private Random random;

    public String[] exam() {
        Integer index = getNextRandomNumber();

        if(Integer.valueOf(-1).equals(index)) {
            return new String[]{"END"};
        }

        String str = wordList.get(index);
        String[] strArr = str.split(":");

        return strArr;
    }


    public int getNextRandomNumber() {
        if (numbers.isEmpty()) {
            return -1;
        }

        // 从数字列表中随机选择一个数字
        int randomIndex = random.nextInt(numbers.size());
        int randomNumber = numbers.get(randomIndex);

        // 从数字列表中移除已经被选中的数字
        numbers.remove(randomIndex);

        return randomNumber;
    }

    public static void main(String[] args) {
        WordExamUtils wordExamUtils = new WordExamUtils();

        Scanner scanner = new Scanner(System.in);

        Integer roundSize = wordExamUtils.roundSize();

        while(roundSize > 0) {
            String[] exam = wordExamUtils.exam();
            System.out.println(exam[0]);
            System.out.println("请输入:");

            String input = scanner.next();
            if(exam[1].equals(input)) {
                System.out.println("正确");
            } else {
                System.out.println("错误");
            }

            System.out.println();

            roundSize --;
        }
    }
}


