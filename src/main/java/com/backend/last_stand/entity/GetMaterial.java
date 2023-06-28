package com.backend.last_stand.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/27 15:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value="sys_getmaterial")
public class GetMaterial implements Serializable {
    @Serial
    private static final long serialVersionUID = -40356781411234312L;
    /**
     * 获取材料id
     */
    private Long id;

    /**
     * 抢到材料的用户
     */
    private Long uid;

    /**
     * 抢到的材料的id
     */
    private Long mid;


    /**
     * 抢到材料的url
     */
    private String url;

    /**
     * 抢材料时间
     */
    private Date getDate;
}
