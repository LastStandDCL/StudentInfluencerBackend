package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenhong
 * @version 1.0
 * @description 角色可以执行的权限表
 * @date 2023/5/29 16:58
 */
@TableName(value="sys_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenu {

    Long roleId;

    Long menuId;

}
