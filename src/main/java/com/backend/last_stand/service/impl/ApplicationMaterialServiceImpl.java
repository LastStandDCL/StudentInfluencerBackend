package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.ApplicationMaterial;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Team;
import com.backend.last_stand.mapper.ApplicationMaterialMapper;
import com.backend.last_stand.service.ApplicationMaterialService;
import com.backend.last_stand.service.FileService;
import com.backend.last_stand.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bowen
 * @since 2023-06-29
 */
@Service
public class ApplicationMaterialServiceImpl extends ServiceImpl<ApplicationMaterialMapper, ApplicationMaterial> implements ApplicationMaterialService {

    private static final String FILE_DIR = "material";

    private static final int REJECTED = 0;

    private static final int PASS = 1;

    private static final int PENDING = 2;

    private final FileService fileService;

    private final UserService userService;

    @Autowired
    @SuppressWarnings("all")
    public ApplicationMaterialServiceImpl(FileService fileService, UserService userService){
        this.fileService = fileService;
        this.userService = userService;
    }

    @Override
    public ResponseResult uploadMaterial(MultipartFile uploadFile, Long userId) {

        String fileName = fileService.saveFile(uploadFile, FILE_DIR, userId);

        if(fileName == null){
            return new ResponseResult(500, "材料上传失败");
        }

        Team team = userService.getMostRecentUserTeam(userId);

        var row = ApplicationMaterial.builder()
                .applicantId(userId)
                .stage(PENDING)
                .teamId(team.getId())
                .fileLink(fileName)
                .submitTime(LocalDateTime.now())
                .build();

        //TODO: 上传日志

        try{
            baseMapper.insert(row);
            return new ResponseResult(200, "材料上传成功");
        }catch (Exception e){
            return new ResponseResult(500, "材料录入失败");
        }
    }

    @Override
    public ResponseResult pending(Long materialId, boolean pass, Long judgeId) {
        UpdateWrapper<ApplicationMaterial> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", materialId);
        wrapper.eq("stage", PENDING);
        if(pass){
            wrapper.set("stage", PASS);
        }else {
            wrapper.set("stage", REJECTED);
        }
        int rows = baseMapper.update(null, wrapper);
        if(rows == 0){
            return new ResponseResult(400, "审批失败");
        }

        //TODO: 添加记录

        return new ResponseResult(200, "审批成功");
    }

    @Override
    public ResponseResult deleteMaterial(Long materialId, Long userId) {
        QueryWrapper<ApplicationMaterial> wrapper = new QueryWrapper<>();
        wrapper.eq("id", materialId);
        ApplicationMaterial material = baseMapper.selectOne(wrapper);
        if(!fileService.removeFile(material.getFileLink(), FILE_DIR)){
            return new ResponseResult(404, "材料不存在");
        }

        //TODO：删除所有跟这条记录相关的审批记录

        baseMapper.delete(wrapper);

        return new ResponseResult(200, "材料删除成功");
    }

    @Override
    public ResponseResult getTeamMaterial(Long userId) {
        Team team = userService.getMostRecentUserTeam(userId);

        QueryWrapper<ApplicationMaterial> wrapper = new QueryWrapper<>();
        wrapper.eq("team_id", team.getId());
        List<ApplicationMaterial> materials = baseMapper.selectList(wrapper);

        return new ResponseResult(404, "材料获取成功", materials);
    }

    @Override
    public ResponseResult getPendingMaterial() {
        QueryWrapper<ApplicationMaterial> wrapper = new QueryWrapper<>();
        wrapper.eq("stage", PENDING);
        List<ApplicationMaterial> materials = baseMapper.selectList(wrapper);

        return new ResponseResult(404, "材料获取成功", materials);
    }

    @Override
    public ResponseEntity<Object> download(String fileName) throws FileNotFoundException {
        return fileService.sendFile(fileName, FILE_DIR);
    }
}
