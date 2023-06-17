package com.backend.last_stand.service;

import com.backend.last_stand.entity.News;
import com.backend.last_stand.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface NewsService extends IService<News> {

    ResponseResult addNews(News news);

    ResponseResult delNews(News news);

    ResponseResult updateNews(News news);

    ResponseResult getNewsByPage(Integer pageNum, Integer pageSize);
}
