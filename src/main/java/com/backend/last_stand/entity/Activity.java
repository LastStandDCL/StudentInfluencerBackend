package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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

}
