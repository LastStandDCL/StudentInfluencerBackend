package com.backend.last_stand.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.UploadService;
import com.backend.last_stand.util.FrameGrabberKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;



@Transactional
@Service()
public class UploadServiceImpl implements UploadService, WebMvcConfigurer {

	/**
	 * 时间格式化使用
	 */
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");

	@Autowired
	private static String FILE_ADDRESS;

	@Value("${virtualUploadPath}")
	private String virtualUploadPath;

	@Value("${file_address}")//保存地址，写在application.yml
	public void setfILE_ADDRESS(String fILE_ADDRESS) {
		FILE_ADDRESS = fILE_ADDRESS;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(virtualUploadPath + "/Text/**").addResourceLocations("file:" + FILE_ADDRESS + "Text/");
		registry.addResourceHandler(virtualUploadPath + "/Img/**").addResourceLocations("file:" + FILE_ADDRESS + "Img/");
		registry.addResourceHandler(virtualUploadPath + "/Video/**").addResourceLocations("file:" + FILE_ADDRESS + "Video/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}

	
	/**
	 * 视频文件上传
	 */
	@Override
	public ResponseResult uploadVideo(MultipartFile multipartFile, HttpServletRequest req){
		if (multipartFile.isEmpty()) {
			return new ResponseResult(400, "文件内容为空");
		}
		ResponseResult result = new ResponseResult();
		//获取客户端系统中文件的原始名称
		String originName = multipartFile.getOriginalFilename();
		System.out.println("用户上传文件originName:  " + originName);

		//从最后一个.开始截取截取fileName的后缀名
		String end = originName.substring(originName.lastIndexOf("."));


		//判断结尾是不是上传pdf，html, md
		if (!originName.endsWith(".mp4")) {
			result.setCode(400);
			result.setMsg("视频类型不符合要求");
			return result;
		}


		//防止重名，给文件重新命名，生成一个随机id
		String newName = UUID.randomUUID().toString().replace("-", "") + end;
		System.out.println("文件重命名名称为:" + newName);

		//路径不存在就创建
		File file = new File(FILE_ADDRESS + "Video/", newName);

		//如果存放路径的父目录不存在，就创建它
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		System.out.println(file.getAbsoluteFile());

		try {
			multipartFile.transferTo(file.getAbsoluteFile());
			//获取url
			String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
					+ virtualUploadPath + "/Video/" + newName;

			System.out.println("视频存放路径" + url);
			//视频封面图处理
			String newImgName = UUID.randomUUID().toString().replace("-", "") +".jpg";
			String framefile = FILE_ADDRESS +  "Img/" + newImgName;
			System.out.println("封面图片地址:" +  framefile);
			String Imggeturl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
					+ virtualUploadPath + "/Img/" + newImgName;
			System.out.println("Imggeturl" + Imggeturl);

			//视频截取封面图
			String imgUrl= FrameGrabberKit.getVedioImg(url, framefile, framefile);

			//文件上传成功给前端返回提示信息，并且返回重命名的文件名称
			result.setCode(200);
			result.setMsg("上传视频成功");
			HashMap<String, Object> mp = new HashMap<>();
			mp.put("newFileName", newName);
			mp.put("url", url);
			mp.put("Imggeturl", Imggeturl);
			result.setData(mp);
		} catch (IOException e) {
			result.setCode(400);
			result.setMsg("视频上传失败");
		}
		return result;


//		Map<String, Object> resultMap=new HashMap<String, Object>();
//
//		String basePath = request.getScheme() + "://" + request.getServerName()
//        + ":" + request.getServerPort() + FILE_ADDRESS;
//
//		//获取当前时间
//		Long time = System.currentTimeMillis();
//
//		ResponseResult result = new ResponseResult();
//
//		//文件原始名称
//		String fileName = file.getOriginalFilename();
//
//		//从最后一个.开始截取截取fileName的后缀名
//		String suffixName = fileName.substring(fileName.lastIndexOf("."));
//
//		//文件新名称,使用时间戳加上后缀命名，这里加上上传人的uid比较好
//		String newFileName = time+suffixName;
//
//		String filePath = FILE_ADDRESS + newFileName;
//		File newFile = new File(filePath);
//		//判断目标文件所在目录是否存在
//		if(!newFile.getParentFile().exists()){
//			//如果目标文件所在的目录不存在，则创建父目录
//			newFile.getParentFile().mkdirs();
//		}
//
//		//视频上传保存url
//		String videoUrl = basePath + newFileName;
//
//		//视频封面图处理
//		String newImgName = time+".jpg";
//		String framefile = FILE_ADDRESS + newImgName;
//		//图片最终位置路径
//		String imgUrlSave = basePath+newImgName;
//		//视频截取封面图
//		String imgUrl= FrameGrabberKit.getVedioImg(videoUrl, framefile, imgUrlSave);
//
//		//将内存中的数据写入磁盘
//		try {
//			file.transferTo(newFile);
//			result.setCode(200);
//			result.setMsg("视频上传成功");
//			resultMap.put("videoUrl", videoUrl);
//			resultMap.put("imgUrl", imgUrl);
//			System.out.println("上传的文件名为："+fileName+",后缀名为："+newFileName);
//
//		} catch (IOException e) {
//			result.setCode(202);
//			result.setMsg("视频上传失败");
//		}
//		return result;
	}

	/**
	 * 图片文件上传
	 */
	@Override
	public ResponseResult uploadImage(MultipartFile multipartFile, HttpServletRequest req) throws Exception {
		if (multipartFile.isEmpty()) {
			return new ResponseResult(400, "文件内容为空");
		}
		ResponseResult result = new ResponseResult();
		//获取客户端系统中文件的原始名称
		String originName = multipartFile.getOriginalFilename();
		System.out.println("用户上传文件originName:  " + originName);

		//从最后一个.开始截取截取fileName的后缀名
		String end = originName.substring(originName.lastIndexOf("."));

		//判断结尾是不是上传pdf，html, md
		if (!originName.endsWith(".jpg") && !originName.endsWith(".jpeg") && !originName.endsWith(".png")) {
			result.setCode(400);
			result.setMsg("图片类型不符合要求");
			return result;
		}

		//防止重名，给文件重新命名，生成一个随机id
		String newName = UUID.randomUUID().toString().replace("-", "") + end;
		System.out.println("文件重命名名称为:" + newName);

		//路径不存在就创建
		File file = new File(FILE_ADDRESS + "Img/", newName);

		//如果存放路径的父目录不存在，就创建它
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		System.out.println(file.getAbsoluteFile());
		try {
			multipartFile.transferTo(file.getAbsoluteFile());
			//获取url
			String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
					+ virtualUploadPath + "/Img/" + newName;

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

	@Override
	public ResponseResult uploadFile(MultipartFile multipartFile, jakarta.servlet.http.HttpServletRequest req) {
		if (multipartFile.isEmpty()) {
			return new ResponseResult(400, "文件内容为空");
		}
		ResponseResult result = new ResponseResult();
		//获取客户端系统中文件的原始名称
		String originName = multipartFile.getOriginalFilename();
		System.out.println("用户上传文件originName:  " + originName);

		//从最后一个.开始截取截取fileName的后缀名
		String end = originName.substring(originName.lastIndexOf("."));

		//判断结尾是不是上传pdf，html, md
        if (!originName.endsWith(".pdf") && !originName.endsWith(".md") && !originName.endsWith(".html")) {
            result.setCode(400);
            result.setMsg("文件类型不符合要求");
            return result;
        }


		//防止重名，给文件重新命名，生成一个随机id
		String newName = UUID.randomUUID().toString().replace("-", "") + end;
		System.out.println("文件重命名名称为:" + newName);

		//路径不存在就创建
		File file = new File(FILE_ADDRESS + "Text/", newName);

		//如果存放路径的父目录不存在，就创建它
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		System.out.println(file.getAbsoluteFile());
		try {
			multipartFile.transferTo(file.getAbsoluteFile());
			//获取url
			String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
					+ virtualUploadPath + "/Text/" + newName;

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

