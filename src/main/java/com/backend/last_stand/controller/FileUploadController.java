package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import freemarker.template.SimpleDate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author chenhong
 * @version 1.0
 * @description 文件上传接口
 * @date 2023/6/7 18:47
 */
@RestController
public class FileUploadController {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("/yyyy/MM/dd/");

    @PostMapping("/files/upload")
    public ResponseResult fileUpload(@RequestParam("file")MultipartFile multipartFile, HttpServletRequest req) {
        if (multipartFile.isEmpty()) {
            return new ResponseResult<>(400, "文件内容为空");
        }

        ResponseResult result = new ResponseResult();
        String originName = multipartFile.getOriginalFilename();
        //判断是不是上传pdf
        if (!originName.endsWith(".pdf")) {
            result.setCode(400);
            result.setMsg("文件类型不符合要求");
            return result;
        }

        //获取项目当前运行路径
        String realPath = req.getServletContext().getRealPath("/") + simpleDateFormat.format(new Date());
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        //防止重名，给文件重新命名
        String newName = UUID.randomUUID().toString() + ".pdf";
        try {
            multipartFile.transferTo(new File(file, newName));
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/"
                    + simpleDateFormat.format(new Date()) + newName;

            System.out.println(url);
            //文件上传成功给前端返回提示信息，并且返回重命名的文件名称
            result.setCode(200);
            result.setMsg("上传文件成功");
            HashMap<String, Object> mp = new HashMap<>();
            mp.put("newFileName", newName);
            mp.put("url", url);
            result.setData(mp);
        } catch (IOException e) {
            result.setCode(400);
            result.setMsg("文件上传失败");
        }

        return result;
    }
}
