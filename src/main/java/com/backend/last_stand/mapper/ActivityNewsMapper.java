package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.ActivityNews;
import com.backend.last_stand.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/21 12:23
 */
public interface ActivityNewsMapper  extends BaseMapper<ActivityNews> {
    List<ActivityNews> getActivityNewsFromTeam(Long teamId);
}
