package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * The type Activity news.
 *
 * @author chenhong
 * @version 1.0
 * &#064;description  活动新闻稿
 * &#064;date  2023 /6/21 12:15
 */
@TableName(value ="sys_activity_news")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityNews implements Serializable {
    @Serial
    private static final long serialVersionUID = -40311111111512L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private Long userId;

    private Long teamId;

    private Date addTime;

    private String state;

    private String fileUrl;

    private String description;
}
