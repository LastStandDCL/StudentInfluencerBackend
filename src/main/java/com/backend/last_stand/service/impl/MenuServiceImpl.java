package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.Menu;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.mapper.MenuMapper;
import com.backend.last_stand.mapper.UserMapper;
import com.backend.last_stand.service.MenuService;
import com.backend.last_stand.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/5 13:52
 */
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public boolean saveBatch(Collection<Menu> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Menu> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<Menu> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(Menu entity) {
        return false;
    }

    @Override
    public Menu getOne(Wrapper<Menu> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<Menu> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<Menu> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }
}
