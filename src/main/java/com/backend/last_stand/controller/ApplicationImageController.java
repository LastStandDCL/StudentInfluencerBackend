package com.backend.last_stand.controller;

import com.backend.last_stand.entity.EnhancedUser;
import com.backend.last_stand.entity.PageParam;
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
    ResponseResult uploadImage(@RequestParam("file") MultipartFile file,
                               @RequestParam("userToken") String token){
        System.out.println(token);
        Long userId = JwtUtils.extractUserId(token);
        return applicationImageService.uploadImage(file, userId);
    }

    @PostMapping("/apply")
    ResponseResult applyForMerit(@RequestBody Long imageId,
                                 @RequestHeader("Authorization")String token) {
        System.out.println(token);
        token = token.split(" ")[1];
        Long userId = JwtUtils.extractUserId(token);
        return applicationImageService.applyForMerit(imageId, userId);
    }

    @PostMapping("/pending")
    ResponseResult pendingMerit(@RequestParam Long imageId,
                                @RequestParam boolean pass,
                                @RequestHeader("Authorization")String token) {
        System.out.println(token);
        token = token.split(" ")[1];
        Long userId = JwtUtils.extractUserId(token);
        return applicationImageService.pendingMerit(imageId, pass, userId);
    }

    @GetMapping("/rm-image")
    ResponseResult deleteImage(Long imageId,
                               @RequestHeader("Authorization")String token) {
        System.out.println(token);
        token = token.split(" ")[1];
        Long userId = JwtUtils.extractUserId(token);
        return applicationImageService.deleteImage(imageId, userId);
    }

    @PostMapping("/team-images-by-page")
    ResponseResult getTeamImages(@RequestHeader("Authorization")String token,
                                 @RequestBody PageParam param) {
        System.out.println(token);
        token = token.split(" ")[1];
        Long userId = JwtUtils.extractUserId(token);
        return applicationImageService.getTeamImages(userId, param.getPageNum(), param.getPageSize());
    }

    @GetMapping("count-team-images")
    ResponseResult countTeamImages(@RequestHeader("Authorization")String token) {
        System.out.println(token);
        token = token.split(" ")[1];
        Long userId = JwtUtils.extractUserId(token);
        return applicationImageService.countTeamImages(userId);
    }

    @PostMapping("/public-images-by-page")
    ResponseResult getPublicImages(@RequestBody PageParam param){
        return applicationImageService.getPublicImages(param.getPageNum(), param.getPageSize());
    }

    @GetMapping("/count-public-images")
    ResponseResult countPublicImages(){
        return applicationImageService.countPublicImages();
    }

    @GetMapping("/pending-images")
    ResponseResult getPendingImages(){
        return applicationImageService.getPendingImages();
    }


    @GetMapping("/download/{fileName}")
    ResponseEntity<Object> download(
            @PathVariable(name = "fileName")String fileName) throws FileNotFoundException {
        return applicationImageService.download(fileName);
    }
}
