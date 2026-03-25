package com.petclass.service;

import com.petclass.dto.OperationQueryDto;
import com.petclass.vo.OperationVO;

import java.util.List;

public interface OperationService {
    void createScoreOperation(Long teacherId, Long classroomId, Long studentId, Long scoreLogId, String title, Integer expChange, Integer coinChange);

    void createPurchaseOperation(Long teacherId, Long classroomId, Long studentId, Long purchaseLogId, String itemName, Integer price);

    List<OperationVO> listRecent(Long teacherId, OperationQueryDto query);

    void revertOperation(Long teacherId, Long operationId);
}
