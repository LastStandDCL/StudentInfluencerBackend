package com.backend.last_stand.controller;

import com.backend.last_stand.entity.EnhancedUser;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.ApplicationMaterialService;
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
 * @since 2023-06-29
 */
@RestController
@RequestMapping("/application-material")
public class ApplicationMaterialController {
    private final ApplicationMaterialService applicationMaterialService;

    public ApplicationMaterialController(ApplicationMaterialService applicationMaterialService) {
        this.applicationMaterialService = applicationMaterialService;
    }
    @PostMapping("/upload")
    ResponseResult uploadImage(@RequestParam("file") MultipartFile file){
        //获取authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //从authentication中取出用户信息
        EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();
        Long userId = enhancedUser.getUser().getId();
        return applicationMaterialService.uploadMaterial(file, userId);
    }

    @PostMapping("/pending")
    ResponseResult pending(@RequestBody Long materialId, boolean pass) {
        //获取authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //从authentication中取出用户信息
        EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();
        Long userId = enhancedUser.getUser().getId();
        return applicationMaterialService.pending(materialId, pass, userId);
    }

    @GetMapping("/rm-material")
    ResponseResult deleteImage(Long materialId) {
        //获取authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //从authentication中取出用户信息
        EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();
        Long userId = enhancedUser.getUser().getId();
        return applicationMaterialService.deleteMaterial(materialId, userId);
    }

    @GetMapping("/get-team-material")
    ResponseResult getTeamMaterial() {
        //获取authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //从authentication中取出用户信息
        EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();
        Long userId = enhancedUser.getUser().getId();
        return applicationMaterialService.getTeamMaterial(userId);
    }

    @GetMapping("/get-pending-material")
    ResponseResult getPendingMaterial() {
        return applicationMaterialService.getPendingMaterial();
    }


    @GetMapping("/download")
    ResponseEntity<Object> download(String fileName) throws FileNotFoundException {
        return applicationMaterialService.download(fileName);
    }
}
