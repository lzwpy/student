package com.petclass.controller;

import com.petclass.common.Result;
import com.petclass.common.SecurityUtils;
import com.petclass.dto.RuleDtos;
import com.petclass.entity.Rule;
import com.petclass.service.RuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
public class RuleController {
    private final RuleService ruleService;

    @GetMapping
    public Result<List<Rule>> list() {
        return Result.ok(ruleService.listByTeacher(SecurityUtils.getCurrentTeacherId()));
    }

    @PostMapping
    public Result<Void> create(@Valid @RequestBody RuleDtos.SaveRequest request) {
        Rule rule = new Rule();
        rule.setTeacherId(SecurityUtils.getCurrentTeacherId());
        rule.setName(request.getName());
        rule.setType(request.getType());
        rule.setIcon(request.getIcon());
        rule.setExpValue(request.getExpValue());
        rule.setCoinValue(request.getCoinValue());
        rule.setSortOrder(request.getSortOrder() == null ? 0 : request.getSortOrder());
        ruleService.save(rule);
        return Result.ok();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody RuleDtos.SaveRequest request) {
        Rule rule = ruleService.getById(id);
        if (rule == null || !rule.getTeacherId().equals(SecurityUtils.getCurrentTeacherId())) {
            throw new IllegalArgumentException("规则不存在");
        }
        rule.setName(request.getName());
        rule.setType(request.getType());
        rule.setIcon(request.getIcon());
        rule.setExpValue(request.getExpValue());
        rule.setCoinValue(request.getCoinValue());
        rule.setSortOrder(request.getSortOrder() == null ? 0 : request.getSortOrder());
        ruleService.updateById(rule);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        Rule rule = ruleService.getById(id);
        if (rule == null || !rule.getTeacherId().equals(SecurityUtils.getCurrentTeacherId())) {
            throw new IllegalArgumentException("规则不存在");
        }
        ruleService.removeById(id);
        return Result.ok();
    }
}
