package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.Menu;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Role;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.mapper.RoleMapper;
import com.backend.last_stand.mapper.UserMapper;
import com.backend.last_stand.service.RoleService;
import com.backend.last_stand.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/6 11:46
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public boolean save(Role entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Role> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    public boolean saveBatch(Collection<Role> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Role> entityList) {
        return super.saveOrUpdateBatch(entityList);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Role> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean removeById(Role entity) {
        return super.removeById(entity);
    }

    @Override
    public boolean removeByMap(Map<String, Object> columnMap) {
        return super.removeByMap(columnMap);
    }

    @Override
    public boolean remove(Wrapper<Role> queryWrapper) {
        return super.remove(queryWrapper);
    }

    @Override
    public boolean removeByIds(Collection<?> list, boolean useFill) {
        return super.removeByIds(list, useFill);
    }

    @Override
    public boolean removeBatchByIds(Collection<?> list) {
        return super.removeBatchByIds(list);
    }

    @Override
    public boolean removeBatchByIds(Collection<?> list, boolean useFill) {
        return super.removeBatchByIds(list, useFill);
    }

    @Override
    public boolean updateById(Role entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean update(Wrapper<Role> updateWrapper) {
        return super.update(updateWrapper);
    }

    @Override
    public boolean update(Role entity, Wrapper<Role> updateWrapper) {
        return super.update(entity, updateWrapper);
    }

    @Override
    public boolean updateBatchById(Collection<Role> entityList) {
        return super.updateBatchById(entityList);
    }

    @Override
    public boolean updateBatchById(Collection<Role> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(Role entity) {
        return false;
    }

    @Override
    public Role getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public List<Role> listByIds(Collection<? extends Serializable> idList) {
        return super.listByIds(idList);
    }

    @Override
    public List<Role> listByMap(Map<String, Object> columnMap) {
        return super.listByMap(columnMap);
    }

    @Override
    public Role getOne(Wrapper<Role> queryWrapper) {
        return super.getOne(queryWrapper);
    }

    @Override
    public Role getOne(Wrapper<Role> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<Role> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<Role> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public long count() {
        return super.count();
    }

    @Override
    public long count(Wrapper<Role> queryWrapper) {
        return super.count(queryWrapper);
    }

    @Override
    public List<Role> list(Wrapper<Role> queryWrapper) {
        return super.list(queryWrapper);
    }

    @Override
    public List<Role> list() {
        return super.list();
    }

    @Override
    public <E extends IPage<Role>> E page(E page, Wrapper<Role> queryWrapper) {
        return super.page(page, queryWrapper);
    }

    @Override
    public <E extends IPage<Role>> E page(E page) {
        return super.page(page);
    }

    @Override
    public List<Map<String, Object>> listMaps(Wrapper<Role> queryWrapper) {
        return super.listMaps(queryWrapper);
    }

    @Override
    public List<Map<String, Object>> listMaps() {
        return super.listMaps();
    }

    @Override
    public List<Object> listObjs() {
        return super.listObjs();
    }

    @Override
    public <V> List<V> listObjs(Function<? super Object, V> mapper) {
        return super.listObjs(mapper);
    }

    @Override
    public List<Object> listObjs(Wrapper<Role> queryWrapper) {
        return super.listObjs(queryWrapper);
    }

    @Override
    public <V> List<V> listObjs(Wrapper<Role> queryWrapper, Function<? super Object, V> mapper) {
        return super.listObjs(queryWrapper, mapper);
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E pageMaps(E page, Wrapper<Role> queryWrapper) {
        return super.pageMaps(page, queryWrapper);
    }

    @Override
    public <E extends IPage<Map<String, Object>>> E pageMaps(E page) {
        return super.pageMaps(page);
    }

    @Override
    public QueryChainWrapper<Role> query() {
        return super.query();
    }

    @Override
    public LambdaQueryChainWrapper<Role> lambdaQuery() {
        return super.lambdaQuery();
    }

    @Override
    public LambdaQueryChainWrapper<Role> lambdaQuery(Role entity) {
        return super.lambdaQuery(entity);
    }

    @Override
    public KtQueryChainWrapper<Role> ktQuery() {
        return super.ktQuery();
    }

    @Override
    public KtUpdateChainWrapper<Role> ktUpdate() {
        return super.ktUpdate();
    }

    @Override
    public UpdateChainWrapper<Role> update() {
        return super.update();
    }

    @Override
    public LambdaUpdateChainWrapper<Role> lambdaUpdate() {
        return super.lambdaUpdate();
    }

    @Override
    public boolean saveOrUpdate(Role entity, Wrapper<Role> updateWrapper) {
        return super.saveOrUpdate(entity, updateWrapper);
    }

    @Override
    public ResponseResult getPermissonFromRoleId(Long id) {
        List<Menu> permissonFromRoleId = roleMapper.getPermissonFromRoleId(id);
        if (permissonFromRoleId == null) {
            throw new RuntimeException("查询指定角色权限失败");
        }
        return new ResponseResult(200, "查询指定角色权限成功", permissonFromRoleId);
    }

    @Override
    public ResponseResult getAllRoles() {
        List<Role> roles = roleMapper.selectList(null);
        return new ResponseResult(200, "查询全部角色成功", roles);
    }

    @Override
    public ResponseResult updateRole(Role role) {
        int i = roleMapper.updateById(role);
        if (i != 1) {
            throw new RuntimeException("更新角色表信息失败");
        }
        return new ResponseResult(200, "更新角色表信息成功");
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        int i = roleMapper.deleteById(id);
        if(i != 1) {
            throw new RuntimeException("删除角色表信息失败");
        }
        return new ResponseResult(200, "删除角色表信息成功");
    }

    @Override
    public ResponseResult addRole(Role role) {
        int i = roleMapper.insert(role);
        if (i != 1) {
            throw new RuntimeException("添加角色表信息失败");
        }
        return new ResponseResult(200, "添加角色表信息成功");
    }
}
