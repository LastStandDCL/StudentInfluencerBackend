package com.backend.last_stand.service;

import com.backend.last_stand.entity.ApplicationTeam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService extends IService<ApplicationTeam> {
    /**
     *
     * @param file MultipartFile 文件对象
     * @param prefix 文件夹名字，用于分类存放文件，可以不写
     * @return 文件路径，保存失败返回 null
     */
    String saveFile(MultipartFile file, String prefix, Long userId);

    /**
     *
     * @param file MultipartFile 文件对象
     * @return 文件路径，保存失败返回 null
     */
    String saveFile(MultipartFile file, Long userId);

    /**
     *
     * @param filePath 文件路径
     * @return 文件响应报文
     */
    ResponseEntity<Object> sendFile(String fileName, String prefix) throws FileNotFoundException;
}
