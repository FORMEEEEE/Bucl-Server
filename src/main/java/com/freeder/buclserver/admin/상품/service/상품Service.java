package com.freeder.buclserver.admin.상품.service;

import com.freeder.buclserver.admin.상품.dto.상품등록및수정Dto;
import com.freeder.buclserver.admin.상품.dto.상품조회및수정시작화면Dto;
import com.freeder.buclserver.app.products.ProductsReviewService;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.product.dto.ProductDTO;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.product.repository.ProductRepository;
import com.freeder.buclserver.domain.productai.entity.ProductAi;
import com.freeder.buclserver.domain.productai.repository.ProductAiRepository;
import com.freeder.buclserver.domain.productcategory.entity.ProductCategory;
import com.freeder.buclserver.domain.productcomment.entity.ProductComment;
import com.freeder.buclserver.domain.productoption.entity.ProductOption;
import com.freeder.buclserver.domain.productoption.repository.ProductOptionRepository;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.freeder.buclserver.admin.상품.dto.상품등록및수정Dto.*;
import static com.freeder.buclserver.domain.product.dto.ProductDTO.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class 상품Service {
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductAiRepository productAiRepository;
    private final ProductsReviewService productsReviewService;

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

    @Transactional
    public BaseResponse<?> 상품등록(CustomUserDetails userDetails, 상품등록및수정Dto productdata, List<MultipartFile> imagepath, List<MultipartFile> detailimage, List<String> imagepathlist, List<String> detailimagelist) {
        if (!userDetails.getRole().equals(Role.ROLE_ADMIN.toString())) {
            throw new BaseException(HttpStatus.FORBIDDEN, 403, "권한이 없습니다.");
        }

        Product 새로운상품 = productRepository.save(상품엔티티로변환(productdata,imagepathlist,detailimagelist));
        List<상품옵션Dto> 상품옵션들 = productdata.productOptions();
        List<ProductOption> list = 상품옵션들.stream().map(productOption -> 상품옵션엔티티로변환(productOption, 새로운상품)).toList();
        ProductAiDto productAiDto = productdata.productAiData();

        productOptionRepository.saveAll(list);
        productAiRepository.save(상품AI엔티티로변환(productAiDto, 새로운상품));

        //S3에 이미지저장
//        productsReviewService.uploadImagesToS3(imagepath,imagepathlist);
//        productsReviewService.uploadImagesToS3(detailimage,detailimagelist);

        return new BaseResponse<>(null,HttpStatus.OK, "새상품등록완료");
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

    private Product 상품엔티티로변환(상품등록및수정Dto productdata,List<String> imagepathlist, List<String> detailimagelist){
        return Product.builder()
                .productCategory(ProductCategory.builder().id(productdata.categoryId()).build())
                .name(productdata.name())
                .productCode(productdata.productCode())
                .brandName(productdata.brandName())
                .manufacturerName(productdata.manufacturerName())
                .supplierName(productdata.supplierName())
                .supplyPrice(productdata.supplyPrice())
                .consumerPrice(productdata.consumerPrice())
                .salePrice(productdata.salePrice())
                .taxStatus(productdata.taxStatus())
                .marginRate(productdata.marginRate())
                .taxRate(productdata.taxRate())
                .discountRate(productdata.discountRate())
                .consumerRewardRate(productdata.consumerRewardRate())
                .businessRewardRate(productdata.businessRewardRate())
                .imagePath(String.join(" ",imagepathlist))
                .detailImagePath(String.join(" ",detailimagelist))
                .productStatus(productdata.productStatus())
                .skuCode(productdata.skuCode())
                .productPriority(productdata.productPriority())
                .isExposed(productdata.isExposed())
                .isAvailableMultipleOption(productdata.isAvailableMultipleOption())
                .saleAlternatives(productdata.saleAlternatives())
                .commentReward(productdata.commentReward())
                .deadline(productdata.deadline())
                .otherLowestPrice(productdata.otherLowestPrice())
                .build();
    }

    private ProductOption 상품옵션엔티티로변환(상품옵션Dto productOption, Product product){
        return ProductOption.builder()
                .product(product)
                .skuCode(product.getSkuCode())
                .optionKey(productOption.optionKey())
                .optionValue(productOption.optionValue())
                .optionSequence(productOption.optionSequence())
                .productQty(productOption.productQty())
                .maxOrderQty(productOption.maxOrderQty())
                .minOrderQty(productOption.minOrderQty())
                .optionExtraAmount(productOption.optionExtraAmount())
                .isExposed(productOption.isExposed())
                .build();
    }

    private ProductAi 상품AI엔티티로변환(ProductAiDto productAiDto, Product product){
        return ProductAi.builder()
                .product(product)
                .average(productAiDto.getAverage())
                .mdComment(productAiDto.getMdComment())
                .summary(productAiDto.getSummary())
                .totalCnt(productAiDto.getTotalCnt())
                .build();
    }
}
