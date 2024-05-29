package com.freeder.buclserver.admin.고객관리.service;


import com.freeder.buclserver.admin.고객관리.dto.유저프로필Dto;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.user.entity.User;
import com.freeder.buclserver.domain.user.repository.UserRepository;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerManagementService {

    private final UserRepository repository;


    public BaseResponse<?> getConsumerProfile(String name, Long id, CustomUserDetails userDetails) {
        권한검증(userDetails);

        if (name != null && !name.isEmpty()){
            List<유저프로필Dto> profileByName = repository.UserName(name);
            return new BaseResponse<>(profileByName, HttpStatus.OK, "조회성공");

        } else if (id != null) {
            List<유저프로필Dto> profileById = repository.UserId(id);
            return new BaseResponse<>(profileById, HttpStatus.OK, "조회성공");

        }

        return new BaseResponse<>(null, HttpStatus.NOT_FOUND, "이름 또는 아이디를 정확히 입력하세요");
    }

    @Transactional
    public BaseResponse<?> updateRewardRate(float rewardRate, Long id, CustomUserDetails userDetails) {
        권한검증(userDetails);

        if(id == null){
            return new BaseResponse<>(null, HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다");
        }
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setBusinessRewardRate(rewardRate);
//            repository.save(user);

            return new BaseResponse<>("", HttpStatus.OK, "수정완료");
        } else {
            return new BaseResponse<>(null, HttpStatus.NOT_FOUND, "해당 ID의 사용자를 찾을 수 없습니다");
        }

    }




// private


    private void 권한검증(CustomUserDetails userDetails) {
        if (!userDetails.getRole().equals(Role.ROLE_ADMIN.toString())) {
            throw new BaseException(HttpStatus.FORBIDDEN, 403, "권한이 없습니다.");
        }
    }




}
