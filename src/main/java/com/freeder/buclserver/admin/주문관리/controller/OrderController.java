package com.freeder.buclserver.admin.주문관리.controller;

import com.freeder.buclserver.admin.주문관리.service.AdminOrderService;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/admin/orders")
@Tag(name = "관리자페이지 관련 order API", description = "주문 검색 관련 API")
public class OrderController {

    private final AdminOrderService service;

    @GetMapping("/search")
    public BaseResponse<?> searchOrderByCustomer(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return service.searchOrderByCustomer(name, phoneNumber,userDetails);
    }

}
