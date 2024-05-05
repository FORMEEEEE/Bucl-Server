package com.freeder.buclserver.admin.상품.controller;

import com.freeder.buclserver.admin.상품.dto.상품등록및수정Dto;
import com.freeder.buclserver.admin.상품.service.상품Service;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.productreview.dto.ReviewRequestDTO;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/admin")
@Tag(name = "상품 관련 관리자 API", description = "상품 관련 관리자 API")
public class 상품Controller {

    private final 상품Service service;

    @GetMapping("/product")
    public BaseResponse<?> 상품조회및수정시작화면(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return service.상품조회및수정시작화면(userDetails);
    }

    @PostMapping("/product")
    public BaseResponse<?> 상품등록(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestPart("productdata") 상품등록및수정Dto productdata,
            @RequestPart("imagepath") List<MultipartFile> imagepath,
            @RequestPart("detailimage") List<MultipartFile> detailimage
    ){

        List<String> imagepathlist = new ArrayList<>();
        List<String> detailimagelist = new ArrayList<>();

        for (int i = 0; i < imagepath.size(); i++) {
            imagepathlist.add("assets/images/imagepath/" + UUID.randomUUID() + ".png");
        }
        for (int i = 0; i < detailimage.size(); i++) {
            detailimagelist.add("assets/images/detailimagelist/" + UUID.randomUUID() + ".png");
        }

        return service.상품등록(userDetails,productdata,imagepath,detailimage,imagepathlist,detailimagelist);

    }
}
