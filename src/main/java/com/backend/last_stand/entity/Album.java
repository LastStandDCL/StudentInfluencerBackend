package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 团队照片
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sys_album")
public class Album implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /**
     * 上传
     */
    private String uploader;

    /**
     * 是否公开
     */
    private Boolean isPublic;

    /**
     * 链接
     */
    private String resourceLink;

    /**
     * 照片所属团队
     */
    private Long teamId;
}
