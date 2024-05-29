package com.freeder.buclserver.admin.주문관리.service;

import com.freeder.buclserver.admin.주문관리.dto.주문관리Dto;
import com.freeder.buclserver.admin.주문관리.dto.주문관리가공전Dto;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.shippingaddress.repository.ShippingAddressRepository;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminOrderService {

    private final ShippingAddressRepository repository;


    public BaseResponse<?> searchOrderByCustomer(String name, String phoneNumber, CustomUserDetails userDetails) {
        권한검증(userDetails);


        if (name != null && !name.isEmpty()) {
            List<주문관리가공전Dto> ordersByName = repository.ordersByName(name);

            List<주문관리Dto> lists = converNametDto(ordersByName);


            return new BaseResponse<>(lists, HttpStatus.OK, "조회완료");

        } else if (phoneNumber != null && !phoneNumber.isEmpty()) {
            List<주문관리가공전Dto> ordersByNumber = repository.findByContactNumber(phoneNumber);

            List<주문관리Dto> lists = convertNumberDto(ordersByNumber);

            return new BaseResponse<>(lists, HttpStatus.OK, "조회완료");
        }
        return new BaseResponse<>(null, HttpStatus.BAD_REQUEST, "정보를 조회할 수 없습니다");
    }


    private void 권한검증(CustomUserDetails userDetails) {
        if (!userDetails.getRole().equals(Role.ROLE_ADMIN.toString())) {
            throw new BaseException(HttpStatus.FORBIDDEN, 403, "권한이 없습니다.");
        }
    }

    private List<주문관리Dto> converNametDto(List<주문관리가공전Dto> ordersByName){

        return ordersByName.stream()
                .map(주문관리가공전Dto -> {
                    Long consumerOrderId = 주문관리가공전Dto.getConsumerOrderId();
                    Long productId = 주문관리가공전Dto.getProductId();
                    String name = 주문관리가공전Dto.getName();
                    List<주문관리Dto.주문관리주소Dto> orderShippingAddress = new ArrayList<>();

                    주문관리Dto.주문관리주소Dto orderShippingAddressDto = new 주문관리Dto.주문관리주소Dto(
                            주문관리가공전Dto.getAddress(),
                            주문관리가공전Dto.getAddressDetail(),
                            주문관리가공전Dto.getRecipientName(),
                            주문관리가공전Dto.getContactNumber(),
                            주문관리가공전Dto.getCreatedAt()
                    );

                    orderShippingAddress.add(orderShippingAddressDto);


                    return new 주문관리Dto(consumerOrderId,productId,name,orderShippingAddress);
                })
                .collect(Collectors.toList());

    }


    private List<주문관리Dto> convertNumberDto(List<주문관리가공전Dto> ordersByNumber){

        return ordersByNumber.stream()
                .map(주문관리가공전Dto -> {
                    Long consumerOrderId = 주문관리가공전Dto.getConsumerOrderId();
                    Long productId = 주문관리가공전Dto.getProductId();
                    String name = 주문관리가공전Dto.getName();
                    List<주문관리Dto.주문관리주소Dto> orderShippingAddress = new ArrayList<>();

                    주문관리Dto.주문관리주소Dto orderShippingAddressDto = new 주문관리Dto.주문관리주소Dto(
                            주문관리가공전Dto.getAddress(),
                            주문관리가공전Dto.getAddressDetail(),
                            주문관리가공전Dto.getRecipientName(),
                            주문관리가공전Dto.getContactNumber(),
                            주문관리가공전Dto.getCreatedAt()
                    );

                    orderShippingAddress.add(orderShippingAddressDto);


                    return new 주문관리Dto(consumerOrderId,productId,name,orderShippingAddress);
                })
                .collect(Collectors.toList());

    }

}






//    private List<주문관리Dto> ordersByNameDtoMapper(List<ConsumerOrder> ordersByName) {
//        return ordersByName.stream()
//                .map(this::주문관리변환)
//                .toList();
//
//    }
//
//    private 주문관리Dto 주문관리변환(ConsumerOrder consumerOrder) {
//        List<Shipping> shippings = consumerOrder.getShippings();
//        List<ConsumerPayment> payments = consumerOrder.getConsumerPayments();
//
//
//        List<String> address = shippings.stream()
//                .map(shipping -> shipping.getShippingAddress().getAddress())
//                .toList();
//
//        List<String> addressDetail = shippings.stream()
//                .map(shipping -> shipping.getShippingAddress().getAddressDetail())
//                .toList();
//
//        List<String> recipientName = shippings.stream()
//                .map(shipping -> shipping.getShippingAddress().getRecipientName())
//                .toList();
//        List<String> contactNumber = shippings.stream()
//                .map(shipping -> shipping.getShippingAddress().getContactNumber())
//                .toList();
//
//        LocalDateTime paidAt = payments.stream()
//                .map(ConsumerPayment::getPaidAt)
//                .findFirst()
//                .orElse(null);
//
//        List<String> name = new ArrayList<>();
//        name.add(consumerOrder.getProduct().getName());
//
//        return new 주문관리Dto(
//                consumerOrder.getId(),
//                consumerOrder.getProduct().getId(),
//                name,
//                address,
//                addressDetail,
//                recipientName,
//                contactNumber,
//                paidAt
//        );
//    }



