package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.News;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.mapper.NewsMapper;
import com.backend.last_stand.service.NewsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;


/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/16 19:25
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Override
    public ResponseResult addNews(HashMap<String, String> mp) {

        String url = mp.get("url");
        String img = mp.get("img");
        String user = mp.get("userId");
        Long userId = Long.valueOf(user);

        News news = new News();
        news.setUrl(url);
        news.setImg(img);
        news.setCreateBy(userId);
        news.setCreateTime(new Date());
        //代表插入的数据没有被删除
        news.setDelFlag(0);

        int insert1 = baseMapper.insert(news);
        if (insert1 != 1) {
            throw new RuntimeException("插入新闻数据异常");
        }
        return new ResponseResult(200, "新增新闻成功", news);
    }

    @Override
    public ResponseResult addNewsPriority(HashMap<String, String> mp) {
        String url = mp.get("url");
        String img = mp.get("img");
        String user = mp.get("userId");
        String priority = mp.get("priority");
        Long userId = Long.valueOf(user);
        News news = new News();
        news.setUrl(url);
        news.setImg(img);
        news.setCreateBy(userId);
        news.setCreateTime(new Date());
        news.setDelFlag(0);
        news.setIsShow(Integer.valueOf(priority));

        int insert = baseMapper.insert(news);
        if (insert != 1) {
            throw new RuntimeException("新增新闻失败");
        }
        return new ResponseResult(200, "新增新闻成功", news);
    }


    @Override
    public ResponseResult delNews(News news) {
        //获取id信息
        Long id = news.getId();
        //设置为一表示删除
        news.setDelFlag(1);

        int i = baseMapper.updateById(news);

        if (i != 1) {
            throw new RuntimeException("删除新闻失败");
        }

        return new ResponseResult(200, "删除新成功");
    }

    @Override
    public ResponseResult updateNews(News news, Long id) {
        news.setUpdateTime(new Date());
        //设置更新新闻的用户id
        news.setUpdateBy(id);
        int i = baseMapper.updateById(news);
        if (i != 1) {
            throw new RuntimeException("更新新闻信息失败");
        }
        return new ResponseResult(200, "更新新闻信息成功");
    }

    @Override
    public ResponseResult getNewsByPage(Integer pageNum, Integer pageSize) {
        Page<News> page = new Page<>(pageNum, pageSize);

        IPage<News> getNews = baseMapper.getNews(page);
        return new ResponseResult(200, "返回新闻结果", getNews);
    }

    @Override
    public ResponseResult getNewsByTime(Integer pageNum, Integer pageSize) {
        Page<News> page = new Page<>(pageNum, pageSize);

        IPage<News> getNews = baseMapper.getNewsByTime(page);
        return new ResponseResult(200, "根据修改时间返回最新新闻结果", getNews);
    }

    @Override
    public ResponseResult getNewsByPriority(Integer pageNum, Integer pageSize) {
        Page<News> page = new Page<>(pageNum, pageSize);

        IPage<News> getNews = baseMapper.getNewsByPriority(page);
        return new ResponseResult(200, "根据新闻优先级返回最新新闻结果", getNews);
    }

    /**
     * 获取未被删除的news数
     * @return
     */
    @Override
    public ResponseResult getNewsCountUndeleted() {
        Integer newsCount = baseMapper.getNewsCountUndeleted();
        HashMap<String,Object> data = new HashMap<>();
        data.put("newsCount", newsCount);
        return new ResponseResult(200, "未被删除的新闻数", data);
    }


}
