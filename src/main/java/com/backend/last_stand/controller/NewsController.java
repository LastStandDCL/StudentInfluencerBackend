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
 * @description 新闻接口
 * @date 2023/6/16 19:24
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    /**
     * 新增新闻, 新闻内容中的url和img需要用户上传后，前端赋值url和img的值，然后插入到数据库中,这里不设置优先级，默认is_show = 0
     * @return
     */
    @PostMapping("/addNews")
    public ResponseResult addNews(News news, Long uid) {
        return newsService.addNews(news, uid);
    }


    /**
     * 新增新闻, 并且设置优先级
     * @return
     */
    @PostMapping("/addNews")
    public ResponseResult addNewsPriority(News news, Long uid, Integer isShow) {
        return newsService.addNewsPriority(news, uid, isShow);
    }

    /**
     * 逻辑删除
     * @param news
     * @return
     */
    @PostMapping("/deleteNews")
    public ResponseResult delNews(News news) {
        return newsService.delNews(news);
    }

    /**
     * 更新新闻内容，比如url，imgurl等
     * @param news
     * @Paeam id 修改新闻的用户id
     * @return
     */
    @PostMapping("/updateNews")
    public ResponseResult updateNews(News news, Long uid) {
        return newsService.updateNews(news, uid);
    }

    /**
     * 返回指定页码，指定数目的news   pageNum为1，pagesize为3表示返回第一页三个数据,后续可以根据需求返回优先级高的（将数据库中is_show字段中按照降序排列，前端可以赋予优先级给我）
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getNewsByPage")
    public ResponseResult getNewsByPage(Integer pageNum, Integer pageSize) {
        return newsService.getNewsByPage(pageNum, pageSize);
    }


}
