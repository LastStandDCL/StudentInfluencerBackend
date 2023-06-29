package com.backend.last_stand.service;

import com.backend.last_stand.entity.ApplicationImage;
import com.backend.last_stand.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.math.BigInteger;

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
    ResponseResult uploadImage(MultipartFile uploadFile, Long userId);

    ResponseResult applyForMerit(Long imageId, Long userId);

    ResponseResult pendingMerit(Long imageId, boolean pass, Long judgeId);

    ResponseResult deleteImage(Long imageId, Long userId);

    ResponseResult getTeamImages(Long userId);

    ResponseResult getPublicImages();

    ResponseResult getPendingImages();

    ResponseEntity<Object> download(String fileName) throws FileNotFoundException;
}
