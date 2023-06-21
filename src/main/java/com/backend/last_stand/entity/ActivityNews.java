package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Activity news.
 *
 * @author chenhong
 * @version 1.0
 * @description 活动新闻稿
 * @date 2023 /6/21 12:15
 */
@TableName(value ="sys_activity_news")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityNews implements Serializable {
    private static final long serialVersionUID = -40311111111512L;

    @TableId
    private Long id;

    private String title;

    private Long userId;

    private Long teamId;

    private Date addTime;

    private String state;

    private String fileUrl;

    private String description;
}
