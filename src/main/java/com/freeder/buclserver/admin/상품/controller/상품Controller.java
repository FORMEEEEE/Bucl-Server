package com.freeder.buclserver.admin.상품.controller;

import com.freeder.buclserver.admin.상품.service.상품Service;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
