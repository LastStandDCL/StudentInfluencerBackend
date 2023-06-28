package com.backend.last_stand.service.impl;

import com.backend.last_stand.config.FileConfig;
import com.backend.last_stand.entity.ApplicationTeam;
import com.backend.last_stand.mapper.ApplicationTeamMapper;
import com.backend.last_stand.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Service
public class FileServiceImpl
        extends ServiceImpl<ApplicationTeamMapper, ApplicationTeam> implements FileService {

    private static final String FILE_EMPTY = null;
    private static final String FILE_EXIST = null;
    private static final String INSERTION_ERROR = null;

    private final String filePath;

    @Autowired
    public FileServiceImpl(@NotNull FileConfig fileConfig){
        filePath = fileConfig.getRootPath();
    }

    @Override
    public String saveFile(@NotNull MultipartFile file, String prefix) {
        // 检查请求中是否有文件
        if (file.isEmpty()) {
            return FILE_EMPTY;
        }

        // 重命名文件
        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String suffix = Objects.requireNonNull(file
                .getOriginalFilename()).split("\\.")[1];
        String time = sdf.format(new Date());
        String link = "/" + prefix + "/" + time + "." + suffix;
        String pathAndName = filePath + link;

        // 检查文件是否存在，目录是否创建
        File dest = new File(pathAndName);
        if (!dest.getParentFile().exists()) {
            boolean mk = dest.getParentFile().mkdir();
            if(!mk){
                return INSERTION_ERROR;
            }
        }
        if (dest.exists()) {
            return FILE_EXIST;
        }
        try {
            boolean wf = dest.createNewFile();
            if(!wf){
                return INSERTION_ERROR;
            }
            file.transferTo(dest);
            return pathAndName;
        } catch (IOException e) {
            e.printStackTrace();
            return INSERTION_ERROR;
        }
    }

    @Override
    public String saveFile(MultipartFile file) {
        return saveFile(file, "files");
    }

    @Override
    public ResponseEntity<Object> sendFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath, filePath);
        if (!file.isFile()) {
            return  ResponseEntity.ok(null);
        }
        InputStreamResource resource =
                new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition"
                , String.format("attachment;filename=\"%s", filePath));
        headers.add("Cache-Control"
                , "max-age=600");
        headers.add("Pragma", "no-cache");
        //headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt"))
                .body(resource);
    }
}
