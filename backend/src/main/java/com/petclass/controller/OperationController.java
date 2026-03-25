package com.petclass.controller;

import com.petclass.common.Result;
import com.petclass.common.SecurityUtils;
import com.petclass.dto.OperationQueryDto;
import com.petclass.service.OperationService;
import com.petclass.vo.OperationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;

    @GetMapping("/recent")
    public Result<List<OperationVO>> recent(OperationQueryDto query) {
        return Result.ok(operationService.listRecent(SecurityUtils.getCurrentTeacherId(), query));
    }

    @PostMapping("/{id}/revert")
    public Result<Void> revert(@PathVariable Long id) {
        operationService.revertOperation(SecurityUtils.getCurrentTeacherId(), id);
        return Result.ok();
    }
}
