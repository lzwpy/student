package com.petclass.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petclass.entity.Rule;
import com.petclass.mapper.RuleMapper;
import com.petclass.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl extends ServiceImpl<RuleMapper, Rule> implements RuleService {
    @Override
    public List<Rule> listByTeacher(Long teacherId) {
        return list(new LambdaQueryWrapper<Rule>()
            .eq(Rule::getTeacherId, teacherId)
            .orderByAsc(Rule::getType)
            .orderByAsc(Rule::getSortOrder)
            .orderByAsc(Rule::getId));
    }
}
