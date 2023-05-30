package com.backend.last_stand.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author bowen
 * @since 2023-01-21
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@Accessors(chain = true)
@TableName("v5_album")
public class Album implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String uploader;

    private Boolean isPublic;

    private String resourceLink;
}