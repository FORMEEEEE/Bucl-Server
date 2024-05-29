package com.freeder.buclserver;

import com.freeder.buclserver.admin.주문관리.dto.주문관리Dto;
import com.freeder.buclserver.admin.주문관리.service.AdminOrderService;
import com.freeder.buclserver.domain.shippingaddress.repository.ShippingAddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ttest {

    @Autowired
    AdminOrderService service;

    @Autowired
    ShippingAddressRepository repository;

//    @Test
//    void asdsda(){
//        String name = "울보";
//        List<주문관리Dto> ordersByName = repository.ordersByName(name);
//        System.out.println(ordersByName);
//    }
}
