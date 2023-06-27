package com.backend.last_stand.service;

import com.backend.last_stand.entity.News;
import com.backend.last_stand.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface NewsService extends IService<News> {

    ResponseResult addNews(HashMap<String, String> mp);

    ResponseResult delNews(News news);

    ResponseResult updateNews(News news, Long id);

    ResponseResult getNewsByPage(Integer pageNum, Integer pageSize);

    ResponseResult addNewsPriority(HashMap<String, String> mp);

    ResponseResult getNewsByTime(Integer pageNum, Integer pageSize);

    ResponseResult getNewsByPriority(Integer pageNum, Integer pageSize);

    ResponseResult getNewsCountUndeleted();
}
