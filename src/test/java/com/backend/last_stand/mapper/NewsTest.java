package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.News;
import com.backend.last_stand.service.NewsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/17 10:33
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class NewsTest {
    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsService newsService;

    @Test
    public void test01() {
        Page<News> page = new Page<>(2, 3);
        IPage<News> news = newsMapper.getNews(page);
        System.out.println(news);
    }

    @Test
    public void test02() {
        News news = new News();
        news.setImg("http://localhost:8010/ahasfafa.png");
    }




}
