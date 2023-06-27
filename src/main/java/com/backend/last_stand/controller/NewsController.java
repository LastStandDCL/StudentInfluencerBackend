package com.backend.last_stand.controller;

import com.backend.last_stand.entity.News;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * The type News controller.
 *
 * @author chenhong
 * @version 1.0
 * @description 新闻接口
 * @date 2023 /6/16 19:24
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    /**
     * 新增新闻, 新闻内容中的url和img需要用户上传后，前端赋值url和img的值，然后插入到数据库中,这里不设置优先级，默认is_show = 0
     *
     * @param
     * @param
     * @return response result
     */
    @PostMapping("/addNews")
    public ResponseResult addNews(@RequestBody HashMap<String, String> mp) {
        return newsService.addNews(mp);
    }


    /**
     * 新增新闻, 并且设置优先级
     *
     * @param
     * @return response result
     */
    @PostMapping("/addNewsPriority")
    public ResponseResult addNewsPriority(@RequestBody HashMap<String, String> mp) {
        return newsService.addNewsPriority(mp);
    }

    /**
     * 逻辑删除
     *
     * @param news the news
     * @return response result
     */
    @PostMapping("/deleteNews")
    public ResponseResult delNews(News news) {
        return newsService.delNews(news);
    }

    /**
     * 更新新闻内容，比如url，imgurl等
     *
     * @param news the news
     * @param uid  the uid
     * @return response result
     * @Paeam id 修改新闻的用户id
     */
    @PostMapping("/updateNews")
    public ResponseResult updateNews(News news, Long uid) {
        return newsService.updateNews(news, uid);
    }

    /**
     * 返回指定页码，指定数目的news   pageNum为1，pagesize为3表示返回第一页三个数据,后续可以根据需求返回优先级高的（将数据库中is_show字段中按照降序排列，前端可以赋予优先级给我）
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return news by page
     */
    @GetMapping("/getNewsByPage")
    public ResponseResult getNewsByPage(Integer pageNum, Integer pageSize) {
        return newsService.getNewsByPage(pageNum, pageSize);
    }

    /**
     * 根据更新时间来返回新闻数据，时间最新的在越前面,时间相同的返回优先级更高的
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return news by time
     */
    @GetMapping("/getNewsByTime")
    public ResponseResult getNewsByTime(Integer pageNum, Integer pageSize) {
        return newsService.getNewsByTime(pageNum, pageSize);
    }

    /**
     * 根据优先级来返回新闻数据，优先级越高在越前面，优先级相同返回时间更新的
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return news by priority
     */
    @GetMapping("/getNewsByPriority")
    public ResponseResult getNewsByPriority(Integer pageNum, Integer pageSize) {
        return newsService.getNewsByPriority(pageNum, pageSize);
    }

    @GetMapping("/count-news-undeleted")
    public ResponseResult getNewsCountUndeleted(){
        return newsService.getNewsCountUndeleted();
    }


}
