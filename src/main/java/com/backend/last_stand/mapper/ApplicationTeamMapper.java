package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.ApplicationTeam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangziyang
 * @since 2023-06-28
 */
public interface ApplicationTeamMapper extends BaseMapper<ApplicationTeam> {

    List<ApplicationTeam> getTeamReportByYearAndStage(String year, int stage);
}
