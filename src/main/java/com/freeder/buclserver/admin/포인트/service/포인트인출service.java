package com.freeder.buclserver.admin.포인트.service;

import com.freeder.buclserver.admin.포인트.dto.포인트적립내역Dto;
import com.freeder.buclserver.admin.포인트.dto.회원정보Dto;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.reward.entity.Reward;
import com.freeder.buclserver.domain.reward.repository.RewardRepository;
import com.freeder.buclserver.domain.rewardwithdrawal.entity.RewardWithdrawal;
import com.freeder.buclserver.domain.rewardwithdrawal.repository.RewardWithdrawalRepository;
import com.freeder.buclserver.domain.rewardwithdrawal.vo.WithdrawalStatus;
import com.freeder.buclserver.domain.user.vo.Role;
import com.freeder.buclserver.global.exception.BaseException;
import com.freeder.buclserver.global.response.BaseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class 포인트인출service {

    private final RewardWithdrawalRepository repository;

    private final RewardRepository rewardRepository;

    /** 포인트 인출 신청한 User 출력
     *
     * @param userDetails
     *
     * @return 회원정보Dto
     */
    public BaseResponse<?> getUserAccount(CustomUserDetails userDetails) {
        권한검증(userDetails);


        List<RewardWithdrawal> list = repository.findAll();

        if(list.isEmpty()){
            return new BaseResponse<>(null, HttpStatus.NOT_FOUND, "조회할 회원이 없습니다");
        }

        List<회원정보Dto> lists = rewardDrawalDto(list);


        return new BaseResponse<>(lists, HttpStatus.OK, "포인트 인출 회원 정보 조회 성공");
    }


    /**
     *  Pagenation 구현된 상세보기 클릭시 해당 user의 rewardHistory 출력
     *
     * @param userId
     * @param page
     * @param pageSize
     * @param userDetails
     *
     * @return 포인트적립내역Dto
     */
    public BaseResponse<?> getUserDetail(Long userId, int page, int pageSize, CustomUserDetails userDetails) {
        권한검증(userDetails);

        Pageable pageable = PageRequest.of(page, pageSize);

        List<Reward> rewardHistory = rewardRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, 404, "적립 내역을 찾을 수 없음"));

        if (rewardHistory.isEmpty()){
            throw new BaseException(HttpStatus.NOT_FOUND, 404, "적립 내역을 찾을 수 없음");
        }




        List<포인트적립내역Dto> lists = rewardDetailDto(rewardHistory);



        return new BaseResponse<>(lists, HttpStatus.OK, "조회성공");
    }



    private void 권한검증(CustomUserDetails userDetails) {
        if (!userDetails.getRole().equals(Role.ROLE_ADMIN.toString())) {
            throw new BaseException(HttpStatus.FORBIDDEN, 403, "권한이 없습니다.");
        }
    }


    private List<회원정보Dto> rewardDrawalDto(List<RewardWithdrawal> list) {

        return list.stream()
                .map(rewardWithdrawal -> {
                    Long userId = rewardWithdrawal.getUser().getId();
                    Long rewardWithDrawalId = rewardWithdrawal.getId();
                    String userName = rewardWithdrawal.getUser().getUserName();
                    String cellPhone = rewardWithdrawal.getUser().getCellPhone();
                    String accountNum = rewardWithdrawal.getAccountNum();
                    String bankName = rewardWithdrawal.getBankName();
                    Integer rewardWithdrawalAmount = rewardWithdrawal.getRewardWithdrawalAmount();

                    return new 회원정보Dto(userId,rewardWithDrawalId,userName,cellPhone,accountNum,bankName,rewardWithdrawalAmount);
                })
                .collect(Collectors.toList());
    }



    private List<포인트적립내역Dto> rewardDetailDto(List<Reward> rewardHistory){

        return rewardHistory.stream()
                .map(reward -> {
                    String brandName = reward.getProductBrandName();
                    String name = reward.getProductName();
                    Integer receivedRewardAmount = reward.getReceivedRewardAmount();
                    Integer spentRewardAmount = reward.getSpentRewardAmount();
                    LocalDateTime createdAt = reward.getCreatedAt();

                    return new 포인트적립내역Dto(brandName,name, receivedRewardAmount,spentRewardAmount,createdAt);
                })
                .collect(Collectors.toList());

    }


    /**
     * 완료버튼을 누를시 RewardWithdrawal의 지급완료 변경
     *
     * @param rewardWithDrawalId
     *
     * @return new BaseResponse "Message"
     */
    @Transactional
    public BaseResponse<?> payedReward(Long rewardWithDrawalId) {

        Optional<RewardWithdrawal> list = repository.findById(rewardWithDrawalId);
        RewardWithdrawal rewardWithdrawal = list.orElse(null);

        if (rewardWithdrawal == null){
            return new BaseResponse<>(null, HttpStatus.NOT_FOUND, "해당 리워드를 찾을 수 없습니다");
        }

        RewardWithdrawal rewardWithdrawals = RewardWithdrawal.builder()
                .id(rewardWithDrawalId)
                .withdrawalStatus(WithdrawalStatus.WTHDR_CMPLT)
                .lastUsedDate(LocalDateTime.now())
                .isWithdrawn(true)
                .build();

        repository.save(rewardWithdrawals);


        return new BaseResponse<>(null, HttpStatus.OK, "인출을 완료했습니다");
    }
}


