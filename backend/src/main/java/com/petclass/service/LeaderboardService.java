package com.petclass.service;

import com.petclass.vo.LeaderboardItemVO;

import java.util.List;

public interface LeaderboardService {
    List<LeaderboardItemVO> petRank(Long classId);

    List<LeaderboardItemVO> coinRank(Long classId);
}
