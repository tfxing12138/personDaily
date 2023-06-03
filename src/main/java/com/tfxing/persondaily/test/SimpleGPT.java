package com.tfxing.persondaily.test;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tfxing.persondaily.entity.po.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleGPT {

    private Map<String,String> baseMap = new HashMap();

    private StringBuilder answerStr = new StringBuilder();

    public void setBaseMap(Map baseMap) {
        this.baseMap = baseMap;
    }

    public void listen(String question) {
        String[] wordArr = question.split("");

        questionHandle(wordArr);

        for (String word : wordArr) {
            String wordDb = getWordFormDB(word);
            answerStr.append(wordDb);
        }
    }

    /**
     * 怎么让程序理解一段句子
     * 例如：今天的天气很不错
     * 问题处理
     * @param wordArr
     */
    private void questionHandle(String[] wordArr) {
        if(null == wordArr || wordArr.length == 0) {
            return;
        }

        List<String> wordList = new ArrayList<>();
        for (int i = 0; i < wordArr.length; i++) {
            String word = "";
            for (int i1 = i+1; i1 < wordArr.length; i1++) {
                word += wordArr[i1];
            }
            wordList.add(word);
        }

        saveBatchWord(wordList);
    }

    /**
     * 保存单词
     * @param wordList
     */
    private void saveBatchWord(List<String> wordList) {
        for (String word : wordList) {
            baseMap.put(word,"");
        }
    }

    private String getWordFormDB(String word) {
        String wordDb = baseMap.get(word);
        if(StringUtils.isNotBlank(wordDb)) {
            return "";
        }

        if(wordDb.startsWith(":")) {
            return getWordFormDB(wordDb);
        }

        return wordDb;
    }

    public String reply() {
        String answerStr = this.answerStr.toString();
        clearAnserStr();
        return answerStr;
    }

    private void clearAnserStr() {
        answerStr = new StringBuilder();
    }


}

/**
 * A:你是谁
 * B:我是name
 * A:现在开始，你的名字是name
 * B:好的，我的名字是name
 * A:今天的天气怎么样
 * B:我不知道今天的天气怎么样
 * A:今天的天气真不错
 * B:好的，今天天气真不错
 * A:今天天气怎么样
 * B:今天天气很好
 *
 * 你
 * 你是
 * 你是谁
 * 是谁
 *
 * 我
 * 我是
 * 我是n
 * 我是na
 * 我是nam
 * 我是name
 * 是n
 * 是na
 * 是nam
 * 是name
 * n
 */