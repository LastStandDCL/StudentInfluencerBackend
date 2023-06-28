package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * The type Jwt validate controller.
 *
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023 /6/15 16:58
 */
@RestController
@RequestMapping("/jwt")
public class JWTValidateController {

    /**
     * Get jwt infos response result.
     *
     * @param token the token
     * @return the response result
     */
    @PostMapping("/infos")
    public ResponseResult getJwtInfos(@RequestBody String token){
        if (token == null) {
            return new ResponseResult(201, "jwt不存在，用户未登录");
        }
        try {
            Claims claims = JwtUtils.parseJWT(token);
            //获取过期时间
            Date expiration = claims.getExpiration();
            System.out.println(expiration);
            //获取当前时间
            Date date = new Date();
            int compareTo = expiration.compareTo(date);
            if (compareTo >= 1) {
                return new ResponseResult(202, "jwt过期");
            } else {
                return new ResponseResult(200, "jwt未过期");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
