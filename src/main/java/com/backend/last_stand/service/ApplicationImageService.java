package com.backend.last_stand.service;

import com.backend.last_stand.entity.ApplicationImage;
import com.backend.last_stand.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bowen
 * @since 2023-06-28
 */
@Service
public interface ApplicationImageService extends IService<ApplicationImage> {
    ResponseResult uploadImage();

    ResponseResult applyForMerit();

    ResponseResult pendingMerit();

    ResponseResult deleteImage();
}
