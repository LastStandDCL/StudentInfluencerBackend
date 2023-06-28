package com.backend.last_stand.controller;

import com.backend.last_stand.entity.EnhancedUser;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.ApplicationImageService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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

    public ApplicationImageController(ApplicationImageService applicationImageService) {
        this.applicationImageService = applicationImageService;
    }
    @PostMapping("/upload")
    ResponseResult uploadImage(@RequestParam("file") MultipartFile file){
        //获取authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //从authentication中取出用户信息
        EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();
        Long userId = enhancedUser.getUser().getId();
        return applicationImageService.uploadImage(file, userId);
    }

    @PostMapping("/apply")
    ResponseResult applyForMerit(@RequestBody Long imageId) {
        //获取authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //从authentication中取出用户信息
        EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();
        Long userId = enhancedUser.getUser().getId();
        return applicationImageService.applyForMerit(imageId, userId);
    }

    @PostMapping("/pending")
    ResponseResult pendingMerit(@RequestBody Long imageId, boolean pass) {
        //获取authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //从authentication中取出用户信息
        EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();
        Long userId = enhancedUser.getUser().getId();
        return applicationImageService.pendingMerit(imageId, pass, userId);
    }

    @GetMapping("/rm-image")
    ResponseResult deleteImage(Long imageId) {
        //获取authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //从authentication中取出用户信息
        EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();
        Long userId = enhancedUser.getUser().getId();
        return applicationImageService.deleteImage(imageId, userId);
    }

    @GetMapping("/team-images")
    ResponseResult getTeamImages() {
        //获取authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //从authentication中取出用户信息
        EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();
        Long userId = enhancedUser.getUser().getId();
        return applicationImageService.getTeamImages(userId);
    }

    @GetMapping("/public-images")
    ResponseResult getPublicImages(){
        return applicationImageService.getPublicImages();
    }

    @GetMapping("/download")
    ResponseResult download(String fileName){
        return applicationImageService.download(fileName);
    }
}
