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
 * @since 2023-06-29
 */
@Getter
@Setter
@Builder
@Accessors(chain = true)
@TableName("sys_application_material")
public class ApplicationMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long applicantId;

    private Long monitorId;

    private LocalDateTime submitTime;

    private Long teamId;

    /**
     * 0没通过1通过2审批中
     */
    private Integer stage;

    private String fileLink;
}
