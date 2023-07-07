package com.tfxing.persondaily.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.tfxing.persondaily.dao.AnswerMapper;
import com.tfxing.persondaily.dao.DeptMapper;
import com.tfxing.persondaily.dao.PersonMapper;
import com.tfxing.persondaily.dao.QuestionMapper;
import com.tfxing.persondaily.entity.po.*;
import com.tfxing.persondaily.entity.strategy.CustomMergeStrategy;
import com.tfxing.persondaily.entity.strategy.DemoMergeStrategy;
import com.tfxing.persondaily.entity.strategy.ExcelMergeIndex;
import com.tfxing.persondaily.entity.strategy.Point;
import com.tfxing.persondaily.service.ExcelService;
import com.tfxing.persondaily.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public void testImportListener(MultipartFile file) throws IOException {
        Consumer<List<Question>> consumer = questionList -> {
            for (Question question : questionList) {
                questionMapper.insert(question);
            }
        };
        EasyExcelFactory.read(new BufferedInputStream(file.getInputStream()), Question.class,ExcelUtils.getListener(consumer,100));
    }

    @Override
    public void testExportExcel(HttpServletResponse response) throws IOException {

        List<Question> questions = questionMapper.selectList(null);

        List<Answer> answers = answerMapper.selectList(null);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-disposition", "attachment;filename=" + "全部数据.xlsx" );

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0, "问题sheet").head(Question.class).build();
        excelWriter.write(questions, writeSheet);

        WriteSheet writeSheet1 = EasyExcel.writerSheet(1, "答案sheet").head(Answer.class).build();
        excelWriter.write(answers, writeSheet1);

        excelWriter.finish();

    }

    @Override
    public void testExportPersonExcel(HttpServletResponse response) throws IOException {
        List<TPerson> personList = personMapper.selectList(null);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-disposition", "attachment;filename=" + "全部数据.xlsx" );

        DemoMergeStrategy demoMergeStrategy = new DemoMergeStrategy(Arrays.asList(new ExcelMergeIndex(new Point(0,0),new Point(1,2))));

        EasyExcel.write(response.getOutputStream())
                .head(TPerson.class)
                .registerWriteHandler(demoMergeStrategy)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("人员列表")
                .doWrite(personList);
    }

}
