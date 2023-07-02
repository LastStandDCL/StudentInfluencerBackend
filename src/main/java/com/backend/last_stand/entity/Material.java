package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description  TODO
 * &#064;date  2023/6/19 21:13
 */
@TableName(value="sys_material")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Material implements Serializable  {
    private static final long serialVersionUID = -54979041101736L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 材料名称
     */
    private String fileName;

    /**
     * 材料的url
     */
    private String url;

    /**
     * 材料所属队伍id
     */
    private Long teamId;
}
