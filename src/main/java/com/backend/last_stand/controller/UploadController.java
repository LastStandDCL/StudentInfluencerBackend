package com.backend.last_stand.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.UploadService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 各种资源文件接口，图片，视频，文本
 */
@RestController
@RequestMapping("/files")
public class UploadController{
	private final UploadService uploadService;

	@Autowired
	public UploadController(UploadService uploadService){
		this.uploadService = uploadService;
	}

    /**
     * 视频文件上传
     *
     * @param file    the file
     * @param request the request
     * @return response result
     * @throws Exception the exception
     */
    @PostMapping("/uploadVideo")
	public ResponseResult uploadVideo(@RequestParam("file")MultipartFile file, HttpServletRequest request) throws Exception{
		return uploadService.uploadVideo(file, request);
	}

    /**
     * 图片文件上传
     *
     * @param file    the file
     * @param request the request
     * @return response result
     * @throws Exception the exception
     */
    @PostMapping("/uploadImage")
	public ResponseResult uploadImage(@RequestParam("file")MultipartFile file,HttpServletRequest request) throws Exception{
		return uploadService.uploadImage(file, request);
	}


    /**
     * 请求body中携带file， 返回内容会携带url，提供给前端预览使用
     *
     * @param multipartFile the multipart file
     * @param req           the req
     * @return response result
     */
    @PostMapping("/uploadfile")
	public ResponseResult fileUpload(@RequestParam("file")MultipartFile multipartFile, HttpServletRequest req) {
		return uploadService.uploadFile(multipartFile, req);
	}


    /**
     * Download.
     *
     * @param response the response
     */
    @GetMapping("/downloadfile")
	public void download(HttpServletResponse response) {
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition",
				"attachment;filename=file_" + System.currentTimeMillis() + ".hprof");

		// 从文件读到servlet response输出流中
		// 改这里路径就好
		File file = new File("/Users/laststand/download");
		try (FileInputStream inputStream = new FileInputStream(file);) {
			byte[] b = new byte[1024];
			int len;
			while ((len = inputStream.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

