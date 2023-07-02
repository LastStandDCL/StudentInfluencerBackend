package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description  TODO
 * &#064;date  2023/6/15 22:58
 */
@TableName("sys_news")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class News implements Serializable {
    @Serial
    private static final long serialVersionUID = -4035678223458312L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     *封面图片
     */
    private String img;

    /**
     * 外链, 文本，视频，图片
     */
    private String url;

    /**
     * 是否在轮播图上显示
     */
    private Integer isShow;

    /**
     * 创建人的用户id
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

    /**
     * 新闻标题
     */
    private String newsTitle;

}
