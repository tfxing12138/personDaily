package com.tfxing.persondaily.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfxing.persondaily.entity.po.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
@Mapper
@Repository
public interface QuestionMapper extends BaseMapper<Question> {
}
