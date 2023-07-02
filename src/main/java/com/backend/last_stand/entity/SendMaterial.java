package com.backend.last_stand.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description  TODO  &#064;date  2023/6/27 15:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="sys_sendmaterial")
public class SendMaterial implements Serializable {
    @Serial
    private static final long serialVersionUID = -403567814121212312L;

    /**
     *发材料表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 材料url
     */
    private String url;


    /**
     * 材料个数
     */
    private Integer total;

    /**
     * 材料剩余个数
     */
    private Integer remain;

    /**
     * 发材料时间
     */
    private Date sendDate;
}
