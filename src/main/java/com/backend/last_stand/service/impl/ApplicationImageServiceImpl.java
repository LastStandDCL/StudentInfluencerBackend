package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.ApplicationImage;
import com.backend.last_stand.entity.News;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Team;
import com.backend.last_stand.mapper.ApplicationImageMapper;
import com.backend.last_stand.service.ApplicationImageService;
import com.backend.last_stand.service.FileService;
import com.backend.last_stand.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

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

    private static final int PRIVATE_IMAGE = -1;

    private static final int FAILED_POST = 0;

    private static final int PUBLIC_IMAGE = 1;

    private static final int PENDING = 2;

    private final FileService fileService;

    private final UserService userService;

    @Autowired
    @SuppressWarnings("all")
    public ApplicationImageServiceImpl(FileService fileService, UserService userService){
        this.fileService = fileService;
        this.userService = userService;
    }

    @Override
    public ResponseResult uploadImage(MultipartFile uploadFile, Long userId) {
        String fileName = fileService.saveFile(uploadFile, FILE_DIR, userId);

        if(fileName == null){
            return new ResponseResult(500, "文件上传失败");
        }

        Team team = userService.getMostRecentUserTeam(userId);

        var row = ApplicationImage.builder()
                .applicantId(userId)
                .stage(PRIVATE_IMAGE)
                .imageLink(fileName)
                .time(LocalDateTime.now())
                .teamId(team.getId())
                .build();

        try{
            baseMapper.insert(row);
            return new ResponseResult(200, "上传成功");
        }catch (Exception e){
            return new ResponseResult(500, "文件录入失败");
        }
    }

    @Override
    public ResponseResult applyForMerit(Long imageId, Long userId) {

        UpdateWrapper<ApplicationImage> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", imageId);
        wrapper.eq("applicant_id", userId);
        wrapper.lt("stage", PUBLIC_IMAGE);
        wrapper.set("stage", PENDING);
        int rows = baseMapper.update(null, wrapper);
        if(rows == 0){
            return new ResponseResult(400, "申请失败");
        }

        //TODO: 添加记录

        return new ResponseResult(200, "申请成功");
    }

    @Override
    public ResponseResult pendingMerit(Long imageId, boolean pass, Long judgeId) {
        UpdateWrapper<ApplicationImage> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", imageId);
        wrapper.eq("stage", PENDING);
        if(pass){
            wrapper.set("stage", PUBLIC_IMAGE);
        }else {
            wrapper.set("stage", FAILED_POST);
        }
        int rows = baseMapper.update(null, wrapper);
        if(rows == 0){
            return new ResponseResult(400, "审批失败");
        }

        //TODO: 添加记录

        return new ResponseResult(200, "审批成功");
    }

    @Override
    public ResponseResult deleteImage(Long imageId, Long userId) {
        QueryWrapper<ApplicationImage> wrapper = new QueryWrapper<>();
        wrapper.eq("id", imageId);
        ApplicationImage image = baseMapper.selectOne(wrapper);
        if(!fileService.removeFile(image.getImageLink(), FILE_DIR)){
            return new ResponseResult(404, "图片不存在");
        }

        //TODO：删除所有跟这条记录相关的审批记录

        baseMapper.delete(wrapper);

        return new ResponseResult(200, "图片删除成功");
    }

    @Override
    public ResponseResult getTeamImages(Long userId, int pageNum, int pageSize) {

        Long teamId = userService.getMostRecentUserTeam(userId).getId();

        Page<ApplicationImage> page = new Page<>(pageNum, pageSize);

        IPage<ApplicationImage> images = baseMapper.getTeamImages(page, teamId);

        return new ResponseResult(200, "图片获取成功", images);
    }

    @Override
    public ResponseResult countTeamImages(Long userId) {
        Team team = userService.getMostRecentUserTeam(userId);
        QueryWrapper<ApplicationImage> wrapper = new QueryWrapper<>();
        wrapper.eq("team_id", team.getId());
        int count = baseMapper.selectList(wrapper).size();
        return new ResponseResult(200, "查询成功", count);
    }

    @Override
    public ResponseResult getPublicImages(int pageNum, int pageSize) {

        Page<ApplicationImage> page = new Page<>(pageNum, pageSize);

        IPage<ApplicationImage> images = baseMapper.getPublicImages(page);

        return new ResponseResult(404, "图片获取成功", images);
    }

    @Override
    public ResponseResult countPublicImages() {
        QueryWrapper<ApplicationImage> wrapper = new QueryWrapper<>();
        wrapper.eq("stage", PUBLIC_IMAGE);
        int count = baseMapper.selectList(wrapper).size();
        return new ResponseResult(200, "查询成功", count);
    }

    @Override
    public ResponseResult getPendingImages() {
        QueryWrapper<ApplicationImage> wrapper = new QueryWrapper<>();
        wrapper.eq("stage", PENDING);
        List<ApplicationImage> image = baseMapper.selectList(wrapper);

        return new ResponseResult(404, "图片获取成功", image);
    }


    @Override
    public ResponseEntity<Object> download(String fileName) throws FileNotFoundException {
        return fileService.sendFile(fileName, FILE_DIR);
    }
}
