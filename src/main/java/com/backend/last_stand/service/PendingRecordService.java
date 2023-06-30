package com.backend.last_stand.service;

import com.backend.last_stand.entity.ApplicationTeam;
import com.backend.last_stand.entity.PendingRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bowen
 * @since 2023-06-29
 */
public interface PendingRecordService extends IService<PendingRecord> {

    public boolean logTeamReportApplication(Long applicantId, Long teamId, String year, Date recordTime);

    public boolean logMiddleReportApplication(Long applicantId, Long teamId, String year, Date recordTime);

    public boolean logReportExamination(ApplicationTeam applicationTeam, int stage, String message);
}
