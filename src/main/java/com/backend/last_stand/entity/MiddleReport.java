package com.backend.last_stand.entity;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description  TODO
 * &#064;date  2023/6/21 13:35
 */
public class MiddleReport implements Serializable {
    @Serial
    private static final long serialVersionUID = -54979071104113736L;

    private Long id;


    private String url;

    private String check;

    private String teamId;

}
