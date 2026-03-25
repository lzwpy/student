package com.petclass.mapper;

import com.petclass.vo.LeaderboardItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 宠物榜以 {@code pet} 为主表；金币榜以 {@code student_coin} 为主表，避免两榜共用同一套拼装逻辑。
 */
@Mapper
public interface LeaderboardMapper {

    @Select("""
        SELECT s.id AS studentId, s.name AS studentName, p.name AS petName, p.image_key AS imageKey,
               p.level AS level, p.total_exp AS totalExp, COALESCE(c.coins, 0) AS coins
        FROM pet p
        INNER JOIN student s ON s.id = p.student_id
        LEFT JOIN student_coin c ON c.student_id = s.id
        WHERE s.classroom_id = #{classId}
        ORDER BY p.total_exp DESC, s.id ASC
        """)
    List<LeaderboardItemVO> selectPetLeaderboard(@Param("classId") Long classId);

    @Select("""
        SELECT s.id AS studentId, s.name AS studentName, p.name AS petName, p.image_key AS imageKey,
               p.level AS level, p.total_exp AS totalExp, c.coins AS coins
        FROM student_coin c
        INNER JOIN student s ON s.id = c.student_id
        LEFT JOIN pet p ON p.student_id = s.id
        WHERE s.classroom_id = #{classId}
        ORDER BY c.coins DESC, s.id ASC
        """)
    List<LeaderboardItemVO> selectCoinLeaderboard(@Param("classId") Long classId);
}
