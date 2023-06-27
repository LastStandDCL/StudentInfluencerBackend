package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.News;
import com.backend.last_stand.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/16 19:26
 */
public interface NewsMapper extends BaseMapper<News> {

    IPage<News> getNews(Page<News> page);

    IPage<News> getNewsByTime(Page<News> page);

    IPage<News> getNewsByPriority(Page<News> page);

    Integer getNewsCountUndeleted();
}
