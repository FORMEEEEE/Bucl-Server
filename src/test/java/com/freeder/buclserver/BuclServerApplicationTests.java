package com.freeder.buclserver;

import com.freeder.buclserver.app.affiliates.AffiliateService;
import com.freeder.buclserver.app.auth.service.JwtTokenService;
import com.freeder.buclserver.core.security.JwtTokenProvider;
import com.freeder.buclserver.domain.openbanking.vo.BANK_CODE;
import com.freeder.buclserver.domain.product.entity.Product;
import com.freeder.buclserver.domain.productcomment.dto.CommentsDto;
import com.freeder.buclserver.domain.productcomment.entity.ProductComment;
import com.freeder.buclserver.domain.productcomment.repository.ProductCommentRepository;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.util.CryptoAes256;
import com.freeder.buclserver.global.util.PageUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootTest
class BuclServerApplicationTests {

    @Autowired JwtTokenService jwtTokenService;
    @Autowired JwtTokenProvider jwtTokenProvider;
    @Autowired AffiliateService service;
    @Autowired CryptoAes256 cryptoAes256;
    @Autowired ProductCommentRepository productCommentRepository;

    @Test
    void getToken() {
        String accessToken = jwtTokenProvider.createAccessToken(1L, Role.ROLE_USER);
        System.out.println(accessToken);
    }

    @Test
    void test() throws Exception {
        System.out.println(cryptoAes256.decrypt("LtNy26qNo1UXOaBtYd2OdQ=="));
    }

    @Test
    void bank(){
        System.out.println(BANK_CODE.valueOf("카카오뱅크").getBankCode()
        );
    }

    @Test
    void dsl(){
        Product product = new Product();
        product.setId(1L);
        Optional<Page<ProductComment>> byProduct = productCommentRepository.findByProduct(product, PageUtil.paging(1, 10));
        List<ProductComment> content = byProduct.get().getContent();
    }

    @Test
    void random(){
        if ("홍길동".equals("김말똥")){
            System.out.println("aaaaa");
        }
    }
}
