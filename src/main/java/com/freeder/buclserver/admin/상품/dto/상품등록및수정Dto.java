package com.freeder.buclserver.admin.상품.dto;

import com.freeder.buclserver.app.payment.dto.ProductOptionDto;
import com.freeder.buclserver.domain.product.vo.ProductStatus;
import com.freeder.buclserver.domain.product.vo.TaxStatus;
import com.freeder.buclserver.domain.productoption.vo.OptionKey;

import java.time.LocalDateTime;
import java.util.List;

import static com.freeder.buclserver.domain.product.dto.ProductDTO.*;

public record 상품등록및수정Dto(
        Long categoryId,
        String name,
        Long productCode,
        String brandName,
        String manufacturerName,
        String supplierName,
        int supplyPrice,
        int consumerPrice,
        int salePrice,
        TaxStatus taxStatus,
        float marginRate,
        float taxRate,
        float discountRate,
        float consumerRewardRate,
        float businessRewardRate,
        ProductStatus productStatus,
        String skuCode,
        int productPriority,
        boolean isExposed,
        boolean isAvailableMultipleOption,
        String saleAlternatives,
        int commentReward,
        LocalDateTime deadline,
        long otherLowestPrice,
        List<상품옵션Dto> productOptions,
        ProductAiDto productAiData
) {
    public record 상품옵션Dto(
            String skuCode,
            OptionKey optionKey,
            String optionValue,
            int optionSequence,
            Integer productQty,
            int maxOrderQty,
            int minOrderQty,
            int optionExtraAmount,
            boolean isExposed
    ){}
}
