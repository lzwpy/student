package com.petclass.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petclass.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
}
