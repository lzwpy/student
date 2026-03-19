package com.petclass.controller;

import com.petclass.common.Result;
import com.petclass.service.LeaderboardService;
import com.petclass.vo.LeaderboardItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    @GetMapping("/pet")
    public Result<List<LeaderboardItemVO>> petRank(@RequestParam("classId") Long classId) {
        return Result.ok(leaderboardService.petRank(classId));
    }

    @GetMapping("/coin")
    public Result<List<LeaderboardItemVO>> coinRank(@RequestParam("classId") Long classId) {
        return Result.ok(leaderboardService.coinRank(classId));
    }
}
