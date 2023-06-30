package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.ApplicationImage;
import com.backend.last_stand.entity.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bowen
 * @since 2023-06-28
 */
public interface ApplicationImageMapper extends BaseMapper<ApplicationImage> {
    IPage<ApplicationImage> getTeamImages(Page<ApplicationImage> page, Long teamId);

    IPage<ApplicationImage> getPublicImages(Page<ApplicationImage> page);
}
