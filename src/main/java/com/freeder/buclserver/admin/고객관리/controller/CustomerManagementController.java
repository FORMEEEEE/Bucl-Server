package com.freeder.buclserver.admin.고객관리.controller;

import com.freeder.buclserver.admin.고객관리.service.CustomerManagementService;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/admin/customerManagement")
@Tag(name = "관리자페이지 관련 고객관리 API", description = "관리자페이지 회원조회 및 리워드 수정 API")
public class CustomerManagementController {

    private final CustomerManagementService service;


    @GetMapping("/search")
    public BaseResponse<?> getConsumerProfile(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "id", required = false) Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return service.getConsumerProfile(name,id,userDetails);

    }


    @PutMapping ("/Modify")
    public BaseResponse<?> getRewardRate(@RequestParam(value = "rewardRate") float rewardRate,
                                          @RequestParam(value = "userId")  Long id,
                                          @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return service.updateRewardRate(rewardRate, id, userDetails);

    }
}
