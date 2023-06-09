package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description  角色表
 * &#064;date  2023/5/29 16:55
 */
@TableName(value="sys_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @TableId(value = "id", type = IdType.AUTO)
    Long Id;

    /**
     * 角色名称
     */
    String name;

    /**
     * 角色中文名称
     */
    String nameZh;

    /**
     * 父级角色id
     */
    Long parentId;


    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

    /**
     * 是否删除（0未删除 1已删除）
     */
    private Integer delFlag;

    /**
     * 备注
     */
    private String remark;


}
