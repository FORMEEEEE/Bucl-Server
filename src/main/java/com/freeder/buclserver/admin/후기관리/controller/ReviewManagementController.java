package com.freeder.buclserver.admin.후기관리.controller;

import com.freeder.buclserver.admin.후기관리.service.ReviewManagementService;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/admin/reviewManagement")
@Tag(name="관리자페이지 관련 후기관리 API", description = "관리자페이지 후기조회 및 삭제")
public class ReviewManagementController {

    private final ReviewManagementService service;


    @GetMapping("/searchRealTime")
    public BaseResponse<?> getRealTimeComment(
            @RequestParam(value = "realTimeComment", required = false) String realTimeComment,
            @RequestParam(value = "review" ,required = false) String review,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int pageSize
    ){
            return service.getRealTimeComment(realTimeComment,review, userDetails, page, pageSize);

    }

    @DeleteMapping("/deleteComment")
    public BaseResponse<?> deleteComment(
            @RequestParam(value = "commentId") Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){


        return service.deleteComment(commentId, userDetails);
    }

    @DeleteMapping("/deleteReview")
    public BaseResponse<?> deleteReview(
            @RequestParam(value = "reviewId") Long reviewId,
            @AuthenticationPrincipal CustomUserDetails userDetails

    ){

        return service.deleteReview(reviewId, userDetails);
    }


}
