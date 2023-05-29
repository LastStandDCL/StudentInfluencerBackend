package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenhong
 * @version 1.0
 * @description 用户和角色连接表
 * @date 2023/5/29 16:56
 */
@TableName(value="sys_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    Long userId;

    Long roleId;

}
