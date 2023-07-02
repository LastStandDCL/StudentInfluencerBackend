package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description  TODO
 * &#064;date  2023/6/21 13:35
 */
public class PersonalReport implements Serializable {
    @Serial
    private static final long serialVersionUID = -54979045413736L;

    @TableId(type = IdType.AUTO)
    private Long id;


    private String url;

    private String check;

    private Long userId;

    private Long teamId;

    private Date addTime;


}
