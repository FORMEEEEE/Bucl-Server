package com.freeder.buclserver.app.products;

import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.productcomment.dto.CommentsDto;
import com.freeder.buclserver.domain.productcomment.dto.ProductCommentDto;
import com.freeder.buclserver.domain.productcomment.dto.SaveComment;
import com.freeder.buclserver.domain.productcomment.entity.ProductComment;
import com.freeder.buclserver.domain.productcomment.repository.ProductCommentRepository;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import com.freeder.buclserver.global.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCommentService {
    private final ProductCommentRepository productCommentRepository;

    @Transactional(readOnly = true)
    public BaseResponse<?> getComment(long productId, int page, int pageSize) {

        Product product = new Product();
        product.setId(productId);

        List<ProductComment> byProduct = productCommentRepository.findByProduct(product, PageUtil.paging(page, pageSize))
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, 400, "잘못된ProductId 또는 댓글이 없습니다.")).toList();

        Object[] counts = productCommentRepository.getCounts(productId).get(0);

        List<CommentsDto> list = byProduct.stream()
                .map(CommentsDto::setDto)
                .toList();

        ProductCommentDto<List<CommentsDto>> listProductCommentDto = new ProductCommentDto<>(counts[0], counts[1], list);

        return new BaseResponse<>(
                listProductCommentDto,
                HttpStatus.OK,
                "실시간댓글 전송성공."
        );
    }

    @Transactional
    public BaseResponse<?> saveComment(CustomUserDetails userDetails, SaveComment saveComment) {

        ProductComment productComment =
                ProductComment.setEntity(Long.valueOf(userDetails.getUserId()),saveComment);

        productCommentRepository.save(productComment);

        return new BaseResponse<>(null,HttpStatus.OK,"실시간댓글 저장성공.");
    }

}
