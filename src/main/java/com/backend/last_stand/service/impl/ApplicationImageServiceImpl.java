package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.ApplicationImage;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.mapper.ApplicationImageMapper;
import com.backend.last_stand.service.ApplicationImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bowen
 * @since 2023-06-28
 */
@Service
public class ApplicationImageServiceImpl extends ServiceImpl<ApplicationImageMapper, ApplicationImage> implements ApplicationImageService {

    @Override
    public ResponseResult uploadImage() {
        return null;
    }
}
