package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenhong
 * @version 1.0
 * @description 单个活动阶段
 * @date 2023/6/18 10:32
 */
@TableName(value ="sys_stage")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stage implements Serializable  {
    private static final long serialVersionUID = -4031342281273134512L;

    @TableId
    private Long id;

    private String stageName;

    private Date begin;

    private  Date end;

    private Long activityId;
}
