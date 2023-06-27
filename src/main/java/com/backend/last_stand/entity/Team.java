package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/18 10:19
 */
@TableName(value ="sys_team")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team  implements Serializable {
    private static final long serialVersionUID = -4031342234512L;

    /**
     * 队伍表的主键
     */
    @TableId
    private Long id;

    /**
     * 队伍名称
     */
    private String teamName;

    /**
     * 队伍关联的学校Id    队伍和学校多对一，在多的表加外键
     */
    private Long schoolId;


    /**
     * 队伍的创建时间，前端可以取年份作为标签
     */
    private Date createTime;

    /**
     * 参与的活动id
     */
    private Long activityId;

    /**
     * 队伍年份
     */
    private String year;





}

