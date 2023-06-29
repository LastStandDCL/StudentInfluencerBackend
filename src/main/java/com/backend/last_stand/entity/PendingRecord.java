package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@Accessors(chain = true)
@TableName("sys_pending_record")
public class PendingRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long pendingId;

    private Long pendingType;

    private Long applicantId;

    private Long monitorId;

    private Long teamId;

    private Integer year;

    private LocalDateTime recordTime;

    private String message;
}
