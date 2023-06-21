package com.backend.last_stand.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/21 13:35
 */
public class PersonalReport implements Serializable {
    private static final long serialVersionUID = -54979045413736L;

    private Long id;


    private String url;

    private String check;

    private Long userId;

    private Long teamId;

    private Date addTime;


}
