package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author bowen
 * @since 2023-06-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("sys_activity")
public class Activity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String activityName;

    private String year;

    private LocalDate begin;

    private LocalDate signupDue;

    private LocalDate materialDue;

    private LocalDate summaryDue;

    private Integer currentComplete;

    private String signUpDescription;

    private String materialDescription;

    private LocalDate speechDue;

    private String speechDescription;

    private String summaryDescription;

    private String endDescription;
}
