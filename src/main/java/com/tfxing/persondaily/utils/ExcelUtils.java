package com.tfxing.persondaily.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;


public class ExcelUtils {


    /**
     * 获取easyexcel导入监听器
     * @param consumer 消费者
     * @param threshold 行数阈值，达到阈值后处理数据并清空存储数据集合
     * @param <T>
     * @return
     */
    public static <T> AnalysisEventListener<T> getListener(Consumer<List<T>> consumer, int threshold){
        return new AnalysisEventListener<T>() {
            private List<T> list = new LinkedList<T>();

            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                //EasyExcel逐行读取并解析
                list.add(t);
                if (list.size() == threshold){
                    //已解析数据行数超过threshold，先处理并清空存储数据集合，以免数据量过大造成内存溢出
                    consumer.accept(list);
                    list.clear();
                }

            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                if (list.size()>0){
                    consumer.accept(list);
                }
            }
        };
    }
}
