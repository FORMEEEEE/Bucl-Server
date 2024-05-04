package com.freeder.buclserver.admin.상품.service;

import com.freeder.buclserver.admin.상품.dto.상품조회및수정시작화면Dto;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.product.repository.ProductRepository;
import com.freeder.buclserver.domain.productcomment.entity.ProductComment;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class 상품Service {
    private final ProductRepository productRepository;

    public BaseResponse<?> 상품조회및수정시작화면(CustomUserDetails userDetails) {
        if (!userDetails.getRole().equals(Role.ROLE_ADMIN.toString())) {
            throw new BaseException(HttpStatus.FORBIDDEN, 403, "권한이 없습니다.");
        }

        List<Product> all = productRepository.findAll();
        List<상품조회및수정시작화면Dto> list = all.stream()
                .map(this::Dto변환)
                .toList();

        return new BaseResponse<>(list, HttpStatus.OK, "상품조회및수정시작화면조회성공");
    }

    private 상품조회및수정시작화면Dto Dto변환(Product product) {
        List<ProductComment> productComments = product.getProductComments();
        int count = (int) productComments.stream().filter(ProductComment::isSuggestion).count();
        return new 상품조회및수정시작화면Dto(
                product.getId(),
                product.getName(),
                productComments.size(),
                count,
                productComments.size() - count
        );
    }

}
