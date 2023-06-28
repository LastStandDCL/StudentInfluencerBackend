package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.ApplicationImage;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.mapper.ApplicationImageMapper;
import com.backend.last_stand.service.ApplicationImageService;
import com.backend.last_stand.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    private static final String FILE_DIR = "image";

    private final FileService fileService;

    @Autowired
    public ApplicationImageServiceImpl(FileService fileService){
        this.fileService = fileService;
    }
    @Override
    public ResponseResult uploadImage(MultipartFile uploadFile, Long userId) {
        String fileName = fileService.saveFile(uploadFile, FILE_DIR, userId);
        //TODO: 录入数据库
        return null;
    }

    @Override
    public ResponseResult applyForMerit(Long imageId, Long userId) {
        return null;
    }

    @Override
    public ResponseResult pendingMerit(Long imageId, boolean pass, Long judgeId) {
        return null;
    }

    @Override
    public ResponseResult deleteImage(Long imageId, Long userId) {
        return null;
    }

    @Override
    public ResponseResult getTeamImages(Long userId) {
        return null;
    }

    @Override
    public ResponseResult getPublicImages() {
        return null;
    }

    @Override
    public ResponseResult download(String fileName) {
        return null;
    }
}
