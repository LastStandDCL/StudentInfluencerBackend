package com.backend.last_stand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 分类计数用户实体
 *
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/27 9:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountUserByYear {

    private String name;

    private Integer value;
}
