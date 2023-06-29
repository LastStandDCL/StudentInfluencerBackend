package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author bowen
 * @since 2023-06-28
 */
@Getter
@Setter
@Builder
@Accessors(chain = true)
@TableName("sys_application_image")
public class ApplicationImage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工单号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 申请人id
     */
    private Long applicantId;

    /**
     * 申请状态，0失败1通过2审核中
     */
    private Integer stage;

    /**
     * 图片链接
     */
    private String imageLink;

    /**
     * 申请时间
     */
    private LocalDateTime time;
    /**
     * 队伍名
     */
    private Long teamId;
}
