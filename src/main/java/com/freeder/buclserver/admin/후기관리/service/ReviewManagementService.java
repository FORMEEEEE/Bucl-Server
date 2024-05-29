package com.freeder.buclserver.admin.후기관리.service;

import com.freeder.buclserver.admin.후기관리.dto.리뷰관리Dto;
import com.freeder.buclserver.admin.후기관리.dto.후기관리Dto;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.productcomment.entity.ProductComment;
import com.freeder.buclserver.domain.productcomment.repository.ProductCommentRepository;
import com.freeder.buclserver.domain.productreview.entity.ProductReview;
import com.freeder.buclserver.domain.productreview.repository.ProductReviewRepository;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewManagementService {

    private final ProductCommentRepository repository;
    private final ProductReviewRepository reviewRepository;


    public BaseResponse<?> getRealTimeComment(String realTimeComment,String review, CustomUserDetails userDetails, int page, int pageSize) {
        권한검증(userDetails);

        Pageable pageable = PageRequest.of(page, pageSize);

        if(realTimeComment != null && !realTimeComment.isEmpty()){

            Page<ProductComment> commentList = repository.findByCommentContaining(realTimeComment, pageable)
                    .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, 404, "해당 댓글을 찾을수없음"));

            List<ProductComment> productComments = commentList.getContent();
            List<후기관리Dto> commentDtoList = commentsToDto(productComments);


            return new BaseResponse<>(commentDtoList, HttpStatus.OK, "데이터 조회 성공");

        }else if(review != null && !review.isEmpty()){

            Page<ProductReview> reviewList = reviewRepository.findByContentContaining(review, pageable)
                    .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, 404, "해당 댓글을 찾을수없음"));

            List<ProductReview> productReviews = reviewList.getContent();
            List<리뷰관리Dto> reviewDtoList = reviewListDto(productReviews);

            return new BaseResponse<>(reviewDtoList, HttpStatus.OK, "데이터 조회 성공");

        }

        return new BaseResponse<>(null, HttpStatus.NOT_FOUND, "데이터가 없습니다");
    }




    @Transactional
    public BaseResponse<?> deleteComment(Long commentId, CustomUserDetails userDetails) {
        권한검증(userDetails);


        ProductComment comment = repository.findById(commentId).orElse(null);

        if(comment == null){
            return new BaseResponse<>("null", HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다");
        }

        repository.delete(comment);

        return new BaseResponse<>(null, HttpStatus.OK, "댓글이 삭제됨");
    }


    @Transactional
    public BaseResponse<?> deleteReview(Long reviewId, CustomUserDetails userDetails) {
        권한검증(userDetails);

        ProductReview review = reviewRepository.findById(reviewId).orElse(null);

        if (review == null){
            return new BaseResponse<>("null", HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다");
        }

        reviewRepository.delete(review);

        return new BaseResponse<>("null", HttpStatus.OK, "댓글이 삭제됨");

    }








    private void 권한검증(CustomUserDetails userDetails) {
        if (!userDetails.getRole().equals(Role.ROLE_ADMIN.toString())) {
            throw new BaseException(HttpStatus.FORBIDDEN, 403, "권한이 없습니다.");
        }
    }


    private List<후기관리Dto> commentsToDto(List<ProductComment> commentList) {
        return commentList.stream()
                .map(comment -> {
                    Long userId = comment.getUser().getId();
                    List<후기관리Dto.상품댓글Dto> productComments = new ArrayList<>();

                    후기관리Dto.상품댓글Dto productCommentDto = new 후기관리Dto.상품댓글Dto(
                            comment.getId(),
                            comment.getProduct().getName(),
                            comment.getComment(),
                            comment.getCreatedAt()
                    );

                    productComments.add(productCommentDto);

                    // 후기 관리 Dto 생성
                    return new 후기관리Dto(userId, productComments);
                })
                .collect(Collectors.toList());
    }

    private List<리뷰관리Dto> reviewListDto(List<ProductReview> productReviews){
        return productReviews.stream()
                .map(productReview -> {
                    Long userId = productReview.getUser().getId();
                    List<리뷰관리Dto.상품댓글Dto> productReviewComments = new ArrayList<>();

                    리뷰관리Dto.상품댓글Dto productReviewDto = new 리뷰관리Dto.상품댓글Dto(
                            productReview.getId(),
                            productReview.getProduct().getName(),
                            productReview.getContent(),
                            productReview.getCreatedAt()
                    );
                    productReviewComments.add(productReviewDto);

                    return new 리뷰관리Dto(userId, productReviewComments);
                })
                .collect(Collectors.toList());
    }



}
