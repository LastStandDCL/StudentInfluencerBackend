package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @author wangziyang
 * @since 2023-06-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_application_team")
public class ApplicationTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 申请的名称
     */
    private String name;

    /**
     * 申请主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上传者的id
     */
    private Long updaterId;

    /**
     * 上传队伍的id
     */
    private Long teamId;

    /**
     * 申请的类型，0为中期报告，1为总结报告
     */
    private Integer type;

    /**
     * 申请提交时间
     */
    private LocalDateTime applyDate;

    /**
     * 审核状态，0为未通过，1为通过，2为审核中
     */
    private Integer stage;

    /**
     * 上传材料链接
     */
    private Integer materialUrl;

    /**
     * 申请的年份
     */
    private String year;
}
