package com.backend.last_stand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.backend.last_stand.entity.Menu;

import java.util.List;


public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long id);
}
