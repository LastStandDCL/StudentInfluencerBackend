package com.backend.last_stand.service;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.backend.last_stand.entity.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	/**
	 * 视频文件上传
	 * @param request
	 * @return
	 */
	ResponseResult uploadVideo(MultipartFile file, HttpServletRequest request) throws Exception;
	
	/**
	 * 图片文件上传
	 * @param request
	 * @return
	 */
	ResponseResult uploadImage(MultipartFile file,HttpServletRequest request) throws Exception;

	ResponseResult uploadFile(MultipartFile multipartFile, jakarta.servlet.http.HttpServletRequest req);
}

