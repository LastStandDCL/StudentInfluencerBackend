package com.backend.last_stand.service;

import com.backend.last_stand.entity.ApplicationMaterial;
import com.backend.last_stand.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bowen
 * @since 2023-06-29
 */
public interface ApplicationMaterialService extends IService<ApplicationMaterial> {
    ResponseResult uploadMaterial(MultipartFile uploadFile, Long userId);

    ResponseResult pending(Long materialId, boolean pass, Long judgeId);

    ResponseResult deleteMaterial(Long materialId, Long userId);

    ResponseResult getTeamMaterial(Long userId);

    ResponseResult getPendingMaterial();

    ResponseEntity<Object> download(String fileName) throws FileNotFoundException;
}
