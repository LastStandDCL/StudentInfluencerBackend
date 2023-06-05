package com.backend.last_stand.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenhong
 * @version 1.0
 * @description 拓展UserDetails类，自定义登录实现类，获取用户的权限使用
 * @date 2023/5/25 13:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnhancedUser implements UserDetails {

        private User user;

        //存储权限信息
        private List<String> permissions;

        //存储角色信息
        private List<Role> roles;

        public EnhancedUser(User user, List<String> permissions, List<Role> roles) {
            this.user = user;
            this.permissions = permissions;
            this.roles = roles;
        }

        //存储SpringSecurity所需要的权限信息的集合
        @JSONField(serialize = false)//该属性不进行序列化处理
        private List<GrantedAuthority> authorities;

    /**
     * 获取权限
     * @return
     */
        @Override
        public  Collection<? extends GrantedAuthority> getAuthorities() {
            //如果已经有权限信息了，就直接返回
            if(authorities!=null){
                return authorities;
            }
            //把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
            authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            return authorities;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUserName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
}

