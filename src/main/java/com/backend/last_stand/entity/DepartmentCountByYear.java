package com.backend.last_stand.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/27 9:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentCountByYear {

    private String name;

    private Integer value;
}
