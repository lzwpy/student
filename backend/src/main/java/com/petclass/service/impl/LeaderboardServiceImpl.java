package com.petclass.service.impl;

import com.petclass.mapper.LeaderboardMapper;
import com.petclass.service.LeaderboardService;
import com.petclass.vo.LeaderboardItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {
    private final LeaderboardMapper leaderboardMapper;

    @Override
    public List<LeaderboardItemVO> petRank(Long classId) {
        return leaderboardMapper.selectPetLeaderboard(classId);
    }

    @Override
    public List<LeaderboardItemVO> coinRank(Long classId) {
        return leaderboardMapper.selectCoinLeaderboard(classId);
    }
}
