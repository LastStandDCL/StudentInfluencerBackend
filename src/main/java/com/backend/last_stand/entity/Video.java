package com.backend.last_stand.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    private Integer id;
    private String localAddress;
    private String suffix;//后缀
}
