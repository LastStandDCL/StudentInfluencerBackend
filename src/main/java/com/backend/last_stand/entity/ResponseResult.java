package com.backend.last_stand.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description  响应内容封装给前端
 * &#064;date  2023/5/22 19:01
 */
@Data
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)//JsonJsonInclude.Include.NON_NULL这个最常用，即如果加该注解的字段为null,那么就不序列化这个字段了。
public class ResponseResult {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;
    /**
     * 查询到的结果数据，
     */
    private Object data;

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
