package com.petclass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.petclass.entity.Rule;

import java.util.List;

public interface RuleService extends IService<Rule> {
    List<Rule> listByTeacher(Long teacherId);
}
