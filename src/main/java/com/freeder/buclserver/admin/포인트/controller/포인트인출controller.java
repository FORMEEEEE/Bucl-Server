package com.freeder.buclserver.admin.포인트.controller;

import com.freeder.buclserver.admin.포인트.service.포인트인출service;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/point")
@Tag(name = "관리자 포인트 인출 관련 API", description = "관리자 포인트 인출 관련 API")
public class 포인트인출controller {

    private final 포인트인출service service;

    @GetMapping("/userAccount")
    public BaseResponse<?> getUserAccount(
            @AuthenticationPrincipal CustomUserDetails userDetails

    ){
        return service.getUserAccount(userDetails);

    }

    @GetMapping ("/userDetail")
    public BaseResponse<?> getUserDetail(
            @RequestParam(value = "userId") Long userId,
            @Parameter(name = "page",description = "현재페이지")
            @RequestParam(name = "page",defaultValue = "0") int page,
            @Parameter(name = "pageSize",description = "페이지사이즈")
            @RequestParam(name = "pageSize",defaultValue = "10") int pageSize,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        return service.getUserDetail(userId,page,pageSize,userDetails);

    }

    @PatchMapping("/rewardPay")
    public BaseResponse<?> payedReward(
            @RequestParam(value = "rewardWithDrawalId") Long rewardWithDrawalId

    ){


        return service.payedReward(rewardWithDrawalId);


    }
}