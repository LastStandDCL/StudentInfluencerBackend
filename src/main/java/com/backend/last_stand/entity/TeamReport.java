package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/21 13:36
 */
public class TeamReport implements Serializable {
    private static final long serialVersionUID = -5497921344113736L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    private String url;

    private String check;

    private String teamId;
}
