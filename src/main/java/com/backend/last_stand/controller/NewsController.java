package com.backend.last_stand.controller;

import com.backend.last_stand.entity.News;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/16 19:24
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    /**
     * 新增新闻
     * @return
     */
    @PostMapping("/addNews")
    public ResponseResult addNews(News news) {
        return newsService.addNews(news);
    }

    @PostMapping("/deleteNews")
    public ResponseResult delNews(News news) {
        return newsService.delNews(news);
    }

    @PostMapping("/updateNews")
    public ResponseResult updateNews(News news) {
        return newsService.updateNews(news);
    }

    /**
     * 返回指定页码，指定数目的news   pageNum为1，pagesize为3表示返回第一页三个数据,后续可以根据需求返回优先级高的
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getNewsByPage")
    public ResponseResult getNewsByPage(Integer pageNum, Integer pageSize) {
        return newsService.getNewsByPage(pageNum, pageSize);
    }


}
