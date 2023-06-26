package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chenhong
 * @version 1.0
 * @description 活动表和阶段表是一对多，一个活动中可能涉及多个阶段
 * @date 2023/6/18 10:34
 */
@TableName(value ="sys_activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity implements Serializable {
    private static final long serialVersionUID = -40311238742234512L;

    @TableId
    private Long id;

    private String activityName;

    private String description;

    private String year;

    /**
     * 活动开始日期
     */
    private Date begin;

    /**
     * 活动报名截止日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signupdue;

    /**
     * 材料申领截止日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date materialdue;


    /**
     * 宣讲活动截止日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date speechdue;

    /**
     * 目前活动进展阶段
     */
    private Integer currentStage;

    /**
     * 目前阶段完成人数
     */
    private String currentComplete;

    /**
     * 当前阶段的任务教程链接
     */
    private String currentTutorial;

}
