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


/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/16 19:25
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    private NewsMapper newsMapper;


    @Override
    public ResponseResult addNews(News news) {
        //获取url
        String url = news.getUrl();
        System.out.println(url);

        int insert = newsMapper.insert(news);
        if (insert != 1) {
            throw new RuntimeException("新增新闻失败");
        }
        return new ResponseResult<>(200, "新增新闻成功");
    }

    @Override
    public ResponseResult delNews(News news) {
        //获取id信息
        Long id = news.getId();
        //设置为一表示删除
        news.setDelFlag(1);

        int i = newsMapper.updateById(news);

        if (i != 1) {
            throw new RuntimeException("删除新闻失败");
        }

        return new ResponseResult<>(200, "删除新成功");
    }

    @Override
    public ResponseResult updateNews(News news) {
        int i = newsMapper.updateById(news);
        if (i != 1) {
            throw new RuntimeException("更新新闻信息失败");
        }
        return new ResponseResult<>(200, "更新新闻信息成功");
    }

    @Override
    public ResponseResult getNewsByPage(Integer pageNum, Integer pageSize) {
        Page<News> page = new Page<>(pageNum, pageSize);

        IPage<News> getNews = newsMapper.getNews(page);
        return new ResponseResult<>(200, "返回新闻结果", getNews);
    }


}
