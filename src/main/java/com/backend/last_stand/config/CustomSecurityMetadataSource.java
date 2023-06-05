package com.backend.last_stand.config;

 import com.backend.last_stand.entity.Menu;
 import com.backend.last_stand.service.MenuService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.access.ConfigAttribute;
 import org.springframework.security.access.SecurityConfig;
 import org.springframework.security.web.FilterInvocation;
 import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
 import org.springframework.stereotype.Component;
 import org.springframework.util.AntPathMatcher;
 import java.util.Collection;
 import java.util.List;

/**
  * @ClassName CustomSecurityMetadataSource
  * @Description TODO
  * @Author chenhong
  * @Date 2023/5/26 23:01
  * @Version 1.0
  */
 @Component
 public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
     @Autowired
     private  MenuService menuService;
     AntPathMatcher antPathMatcher = new AntPathMatcher();

     @Autowired
     public CustomSecurityMetadataSource(MenuService menuService) {
         this.menuService = menuService;
     }

     /**
      * 自定义动态资源权限元数据信息
      *
      * @param object
      * @return
      * @throws IllegalArgumentException
      */
     @Override
     public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
         // 1.当前请求对象
         String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();

         // 2.查询所有菜单
         List<Menu> allMenu = menuService.list();
         for (Menu menu : allMenu) {
             if (antPathMatcher.match(menu.getPerms(), requestURI)) {
                 String[] roles = menu.getRoles().stream().map(r -> r.getName()).toArray(String[]::new);
                 return SecurityConfig.createList(roles);
             }
         }
         return null;
     }

     @Override
     public Collection<ConfigAttribute> getAllConfigAttributes() {
         return null;
     }

     @Override
     public boolean supports(Class<?> clazz) {
         return FilterInvocation.class.isAssignableFrom(clazz);
     }
 }
