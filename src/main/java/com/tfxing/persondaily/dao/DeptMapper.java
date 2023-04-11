package com.tfxing.persondaily.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tfxing.persondaily.entity.po.TDept;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptMapper extends BaseMapper<TDept> {
}
