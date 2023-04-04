package com.tfxing.persondaily.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.tfxing.persondaily.dao.QuestionMapper;
import com.tfxing.persondaily.entity.po.Question;
import com.tfxing.persondaily.service.ExcelService;
import com.tfxing.persondaily.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public void testImportListener(MultipartFile file) throws IOException {
        Consumer<List<Question>> consumer = questionList -> {
            for (Question question : questionList) {
                questionMapper.insert(question);
            }
        };
        EasyExcelFactory.read(new BufferedInputStream(file.getInputStream()), Question.class,ExcelUtils.getListener(consumer,100));
    }
}
