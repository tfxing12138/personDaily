package com.tfxing.persondaily.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfxing.persondaily.entity.po.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */

@Mapper
@Repository
public interface AnswerMapper extends BaseMapper<Answer> {
    List<Answer> testForEach(@Param("list") List<String> list);
}
