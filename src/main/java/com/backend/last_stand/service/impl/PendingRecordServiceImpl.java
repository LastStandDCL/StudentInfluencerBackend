package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.PendingRecord;
import com.backend.last_stand.mapper.PendingRecordMapper;
import com.backend.last_stand.service.PendingRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bowen
 * @since 2023-06-29
 */
@Service
public class PendingRecordServiceImpl extends ServiceImpl<PendingRecordMapper, PendingRecord> implements PendingRecordService {

    private static final int TYPE_MIDDLEREPORT_APPLYCATION = 0;

    private static final int TYPE_TEAMREPORT_APPLYCATION = 1;

    @Autowired
    private PendingRecordMapper pendingRecordMapper;

    /**
     * 用户提交团队总结报告日志记录
     * @param applicantId
     * @param teamId
     * @param year
     * @param recordTime
     * @return
     */
    @Override
    public boolean logTeamReportApplication(Long applicantId, Long teamId, String year, Date recordTime) {
        PendingRecord pendingRecord = new PendingRecord();
        pendingRecord.setTeamId(teamId);
        pendingRecord.setApplicantId(applicantId);
        pendingRecord.setRecordTime(recordTime);
        pendingRecord.setYear(year);
        pendingRecord.setPendingType(TYPE_TEAMREPORT_APPLYCATION);
        pendingRecord.setMessage("用户id:" + applicantId + "提交团队id:" + teamId + "的团队总结报告");
        pendingRecordMapper.insert(pendingRecord);
        return true;
    }

    /**
     * 用户提交团队总结报告日志记录
     * @param applicantId
     * @param teamId
     * @param year
     * @param recordTime
     * @return
     */
    @Override
    public boolean logMiddleReportApplication(Long applicantId, Long teamId, String year, Date recordTime) {
        PendingRecord pendingRecord = new PendingRecord();
        pendingRecord.setTeamId(teamId);
        pendingRecord.setApplicantId(applicantId);
        pendingRecord.setRecordTime(recordTime);
        pendingRecord.setYear(year);
        pendingRecord.setPendingType(TYPE_MIDDLEREPORT_APPLYCATION);
        pendingRecord.setMessage("用户id:" + applicantId + "提交团队id:" + teamId + "的团队中期报告");
        pendingRecordMapper.insert(pendingRecord);
        return true;
    }
}
