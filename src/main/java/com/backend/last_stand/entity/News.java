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
 * @description TODO
 * @date 2023/6/15 22:58
 */
@TableName("sys_news")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News implements Serializable {
    private static final long serialVersionUID = -4035678223458312L;

    /**
     * 主键
     */
    @TableId
    private Long id;


    /**
     *封面图片或者视频链接
     */
    private String img;

    /**
     * 外链
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
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

}
