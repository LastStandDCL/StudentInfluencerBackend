package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.ActivityNews;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.mapper.ActivityNewsMapper;
import com.backend.last_stand.service.ActivityNewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/21 12:23
 */
@Service
public class ActivityNewsServiceImpl  extends ServiceImpl<ActivityNewsMapper, ActivityNews> implements ActivityNewsService {

    @Override
    public ResponseResult getActivityNewsFromTeam(ActivityNews activityNews) {
        //根据队伍id来查找 团队内部的活动新闻稿件
        List<ActivityNews> activityNewsFromTeam =
                baseMapper.getActivityNewsFromTeam(activityNews.getTeamId());
        return new ResponseResult(200, "根据团队id返回新闻稿信息成功", activityNewsFromTeam);
    }

    @Override
    public ResponseResult insertActivityNews(ActivityNews activityNews) {
        //设置时间
        activityNews.setAddTime(new Date());
        baseMapper.insert(activityNews);
        return new ResponseResult(200, "创建用户新闻稿成功");
    }
}
