package com.backend.last_stand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.backend.last_stand.entity.Menu;

import java.util.List;


public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 通过uid来查询用户的所有权限
     * @param id
     * @return
     */
    List<String> selectPermsByUserId(Long id);


}
