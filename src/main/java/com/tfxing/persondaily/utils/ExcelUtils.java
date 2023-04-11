package com.tfxing.persondaily.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tfxing.persondaily.entity.po.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
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

    /**
     * 导出数据为excel文件
     *
     * @param filename       文件名称
     * @param dataResult     集合内的bean对象类型要与clazz参数一致
     * @param clazz          集合内的bean对象类型要与clazz参数一致
     * @param response       HttpServlet响应对象
     */
    public static void export(String filename, List<?> dataResult, Class<?> clazz, HttpServletResponse response) {
        response.setStatus(200);
        OutputStream outputStream = null;
        ExcelWriter excelWriter = null;
        try {
            if (StringUtils.isBlank(filename)) {
                throw new RuntimeException("'filename' 不能为空");
            }
            String fileName = filename.concat(".xlsx");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            outputStream = response.getOutputStream();

            // 根据不同的策略生成不同的ExcelWriter对象
            if (dataResult == null){
                excelWriter = getTemplateExcelWriter(outputStream);
            } else {
                excelWriter = getExportExcelWriter(outputStream);
            }

            WriteTable writeTable = EasyExcel.writerTable(0).head(clazz).needHead(true).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(fileName).build();
            // 写出数据
            excelWriter.write(dataResult, writeSheet, writeTable);

        } catch (Exception e) {
            log.error("导出excel数据异常：", e);
            throw new RuntimeException(e);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    log.error("导出数据关闭流异常", e);
                }
            }
        }
    }

    /**
     * 根据不同策略生成不同的ExcelWriter对象， 可根据实际情况修改
     * @param outputStream  数据输出流
     * @return  模板下载ExcelWriter对象
     */
    private static ExcelWriter getTemplateExcelWriter(OutputStream outputStream){
        return EasyExcel.write(outputStream)
                .registerWriteHandler(getStyleStrategy())               //字体居中策略
                .build();
    }

    /**
     * 根据不同策略生成不同的ExcelWriter对象， 可根据实际情况修改
     * @param outputStream  数据输出流
     * @return  数据导出ExcelWriter对象
     */
    private static ExcelWriter getExportExcelWriter(OutputStream outputStream){
        return EasyExcel.write(outputStream)
                .registerWriteHandler(getStyleStrategy())   //字体居中策略
                .build();
    }

    /**
     *  设置表格内容居中显示策略
     * @return
     */
    private static HorizontalCellStyleStrategy getStyleStrategy(){
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        //设置头字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)13);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

        // 内容策略
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        // 设置内容水平居中
        writeCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, writeCellStyle);
    }


    /**
     * 获取导出excel的sheet页
     * @param index
     * @param sheetName
     * @return
     */
    public static WriteSheet getWriteSheet(int index, String sheetName, Class<?> clazz) {
        WriteSheet writeSheet = EasyExcel.writerSheet(index, sheetName).head(clazz).build();
        return writeSheet;
    }
}
