package com.tfxing.persondaily.utils;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class WordExamPlusUtils {

    @Data
    static class WordItem {
        private String wordCn;

        private String wordEng;

        private List<String> exampleSentenceList;
    }

    private List<Integer> numbers;
    private Random random;
    private Map<String,WordItem> wordMap;
    private List<String> keyList;

    private Boolean isEnd = false;

    public WordExamPlusUtils() {
        numbers = new ArrayList<>();
        random = new Random();
        wordMap = new HashMap<>();
        keyList = new ArrayList<>();

        List<String> wordCnPrefixList = Arrays.asList("adj", "n", "vt", "v", "vi");

        // 指定Markdown文件的路径
        String filePath = "src/main/resources/EnglishStudy.md";

        WordItem wordItem = null;
        StringJoiner wordCn = new StringJoiner("\n");
        List<String> exampleSentenceList = new ArrayList<>();

        try {
            // 创建一个BufferedReader来读取文件
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;
            // 逐行读取文件内容并打印到控制台
            while ((line = reader.readLine()) != null) {
                if(skip(line)) {
                    continue;
                }

                if(line.startsWith("**") && isEnd) {
                    String wordCnStr = wordCn.toString();

                    wordItem.setWordCn(wordCn.toString());
                    wordItem.setExampleSentenceList(exampleSentenceList);

                    wordMap.put(wordCnStr, wordItem);
                    keyList.add(wordCnStr);

                    wordCn = new StringJoiner("\n");
                    exampleSentenceList = new ArrayList<>();
                    wordItem = new WordItem();

                    String wordEng = line.replaceAll("\\*","");
                    wordItem.setWordEng(wordEng);

                    continue;
                }

                if (line.startsWith("**")) {
                    wordItem = new WordItem();

                    String wordEng = line.replaceAll("\\*","");
                    wordItem.setWordEng(wordEng);

                    isEnd = true;
                }

                if(containsWordPrefix(line,wordCnPrefixList)) {
                    wordCn.add(line);
                }

                if(isNumPrefix(line)) {
                    exampleSentenceList.add(line);
                }
            }

            // 关闭文件流
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            String wordCnStr = wordCn.toString();

            wordItem.setWordCn(wordCn.toString());
            wordItem.setExampleSentenceList(exampleSentenceList);

            wordMap.put(wordCnStr, wordItem);
            keyList.add(wordCnStr);

            // 初始化数字列表，每个数字出现2次
            for (int i = 0; i < keyList.size(); i++) {
                numbers.add(i);
                numbers.add(i);
            }
        }
    }

    /**
     * 是否以数字.开头
     * @param line
     * @return
     */
    private boolean isNumPrefix(String line) {
        return line.matches("^\\d+\\..*");
    }

    /**
     * 以预设开头匹配
     * @param line
     * @param wordCnPrefixList
     * @return
     */
    private boolean containsWordPrefix(String line, List<String> wordCnPrefixList) {
        if(CollectionUtil.isEmpty(wordCnPrefixList)) {
            return false;
        }

        for (String wordCnPrefix : wordCnPrefixList) {
            if(line.startsWith(wordCnPrefix)) {
                return true;
            }
        }

        return false;
    }

    private boolean skip(String line) {
        return StringUtils.isBlank(line) ||
                line.startsWith("#") ||
                line.startsWith("_") ||
                line.startsWith("例句");
    }



    public Integer size() {
        return keyList.size();
    }

    public Integer roundSize() {
        return numbers.size();
    }


    public WordItem exam() {
        Integer index = getNextRandomNumber();

        if(Integer.valueOf(-1).equals(index)) {
            return null;
        }

        String key = keyList.get(index);
        WordItem wordItem = wordMap.get(key);

        return wordItem;
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
        WordExamPlusUtils wordExamUtils = new WordExamPlusUtils();

        Scanner scanner = new Scanner(System.in);

        Integer roundSize = wordExamUtils.roundSize();

        Integer total = roundSize;

        List<String> errorWordList = new ArrayList<>();

        while(roundSize > 0) {
            WordItem exam = wordExamUtils.exam();
            System.out.println(exam.getWordCn());
            System.out.println("请输入:");

            String input = scanner.next();
            if(exam.getWordEng().equalsIgnoreCase(input)) {
                System.out.println("正确");
            } else {
                System.out.println("错误");
                System.out.println(exam.getWordEng());

                errorWordList.add(exam.getWordCn() + " : " + exam.getWordEng());
            }

            for (String exampleSentence : exam.getExampleSentenceList()) {
                System.out.println(exampleSentence);
            }

            System.out.println();

            roundSize --;
        }

        if(CollectionUtil.isNotEmpty(errorWordList)) {
            int errorSize = errorWordList.size();
            int successSize = total - errorSize;
            BigDecimal rate = BigDecimal.valueOf(successSize).divide(BigDecimal.valueOf(total), 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
            System.out.println("本次测试，一共测试了" + total + "个单词\n正确个数：" + successSize + "\n错误个数：" + errorSize + "\n" + "正确率为：" + rate + "%");
        }
    }
}


