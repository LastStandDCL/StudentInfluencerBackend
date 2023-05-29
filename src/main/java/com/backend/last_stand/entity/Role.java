package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author chenhong
 * @version 1.0
 * @description 角色表
 * @date 2023/5/29 16:55
 */
@TableName(value="sys_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @TableId
    Long Id;

    /**
     * 角色名称
     */
    String name;

    /**
     * 角色权限字符串
     */
    String roleKey;

    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;
    /**
     * 是否删除（0未删除 1已删除）
     */
    private Integer delFlag;

    /**
     * 备注
     */
    private String remark;


}
