package com.backend.last_stand.entity;

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
 * @description TODO
 * @date 2023/5/31 12:34
 */
@TableName(value ="sys_school")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School  implements Serializable {
    private static final long serialVersionUID = -40356782823868312L;
    /**
     * 主键
     */
    @TableId
    private Long id;

    private String province;

    private String schoolName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
