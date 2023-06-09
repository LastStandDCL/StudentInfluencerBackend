package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@TableName(value ="sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -40356785423868312L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 邮箱赋值，用作remember_me标识
     */
    private String userName;

    /**
     * 学生学号信息，不为空
     */
    private String studentId;


    /**
     * 姓名
     */
    private String name;

    /**
     * 学号
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String telephone;


    /**
     * 所在省份
     */
    private String province;

    /**
     * 所在城市
     */
    private String city;


    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;
    /**
     * 账号状态（0正常 1停用）
     */
    private String status;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户类型（0管理员，1普通用户, 2老师, 3学生大使）
     */
    private String userType;
    /**
     * 创建人的用户id
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;

}

