package com.backend.last_stand.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 邮件信息
 * @author chenhong
 * @date 2023/5/22 19:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailData {

    /**
     * 发送邮箱列表
     */
    @NotEmpty
    private List<String> tos;

    /**
     * 主题
     */
    @NotBlank
    private String subject;

    /**
     * 内容
     */
    @NotBlank
    private String content;
}
