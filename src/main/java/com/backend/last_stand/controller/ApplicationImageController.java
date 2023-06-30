package com.backend.last_stand.controller;

import com.backend.last_stand.entity.EnhancedUser;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.ApplicationImageService;
import com.backend.last_stand.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bowen
 * @since 2023-06-28
 */
@RestController
@RequestMapping("/application-image")
public class ApplicationImageController {

    private final ApplicationImageService applicationImageService;

    @Autowired
    public ApplicationImageController(ApplicationImageService applicationImageService) {
        this.applicationImageService = applicationImageService;

    }
    @PostMapping("/upload")
    ResponseResult uploadImage(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization")String token){
        System.out.println(token);
        token = token.split(" ")[1];
        Long userId = JwtUtils.extractUserId(token);
        return applicationImageService.uploadImage(file, userId);
    }

    @PostMapping("/apply")
    ResponseResult applyForMerit(@RequestBody Long imageId, @RequestHeader("Authorization")String token) {
        System.out.println(token);
        token = token.split(" ")[1];
        Long userId = JwtUtils.extractUserId(token);
        return applicationImageService.applyForMerit(imageId, userId);
    }

    @PostMapping("/pending")
    ResponseResult pendingMerit(@RequestBody Long imageId, boolean pass, @RequestHeader("Authorization")String token) {
        System.out.println(token);
        token = token.split(" ")[1];
        Long userId = JwtUtils.extractUserId(token);
        return applicationImageService.pendingMerit(imageId, pass, userId);
    }

    @GetMapping("/rm-image")
    ResponseResult deleteImage(Long imageId, @RequestHeader("Authorization")String token) {
        System.out.println(token);
        token = token.split(" ")[1];
        Long userId = JwtUtils.extractUserId(token);
        return applicationImageService.deleteImage(imageId, userId);
    }

    @GetMapping("/team-images")
    ResponseResult getTeamImages(@RequestHeader("Authorization")String token) {
        System.out.println(token);
        token = token.split(" ")[1];
        Long userId = JwtUtils.extractUserId(token);
        applicationImageService.getTeamImages(userId);
        return null;
    }

    @GetMapping("/public-images")
    ResponseResult getPublicImages(){
        return applicationImageService.getPublicImages();
    }

    @GetMapping("/pending-images")
    ResponseResult getPendingImages(){
        return applicationImageService.getPendingImages();
    }


    @GetMapping("/download")
    ResponseEntity<Object> download(String fileName) throws FileNotFoundException {
        return applicationImageService.download(fileName);
    }
}
