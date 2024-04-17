package com.freeder.buclserver.app.rewards;

import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.domain.reward.dto.RewardDto;
import com.freeder.buclserver.domain.rewardwithdrawal.dto.WithdrawalHistoryDto;
import com.freeder.buclserver.domain.rewardwithdrawal.dto.WithdrawalRequestDto;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/rewards")
@Tag(name = "rewards API", description = "적립금 관련 API")
public class RewardsController {

	private final RewardsService rewardsService;
	private final RewardsWithdrawalService rewardsWithdrawalService;

	@Autowired
	public RewardsController(RewardsService rewardsService, RewardsWithdrawalService rewardsWithdrawalService) {
		this.rewardsService = rewardsService;
		this.rewardsWithdrawalService = rewardsWithdrawalService;
	}

	@GetMapping("/crnt-amt")
	@Transactional(readOnly = true)
	public BaseResponse<Integer> getUserRewards(
		// @AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long userId = 1L;
		int currentRewardAmount = rewardsService.getUserRewardCrntAmount(userId);

		return new BaseResponse<>(currentRewardAmount, HttpStatus.OK, "리워드 조회 성공");
	}

	@GetMapping("")
	@Transactional(readOnly = true)
	public BaseResponse<List<RewardDto>> getRewardHistory(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "5") int pageSize
		// @AuthenticationPrincipal CustomUserDetails userDetails
	) {
		// Long userId = getUserIdFromUserDetails(userDetails);
		Long userId = 1L;
		List<RewardDto> rewardHistory = rewardsService.getRewardHistoryPageable(userId, page, pageSize);

		return new BaseResponse<>(rewardHistory, HttpStatus.OK, "적립금 내역 조회 성공");
	}

	private Long getUserIdFromUserDetails(CustomUserDetails userDetails) {
		return Long.parseLong(userDetails.getUserId());
	}

	@PostMapping("/withdrawals")
	@Transactional
	public BaseResponse<String> withdrawReward(
		// @AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody WithdrawalRequestDto withdrawalRequestDto
	) {
		// Long userId = getUserIdFromUserDetails(userDetails);
		Long userId = 1L;
		rewardsWithdrawalService.withdrawReward(
			userId,
			withdrawalRequestDto.getBankCodeStd(),
			withdrawalRequestDto.getBankName(),
			withdrawalRequestDto.getWithdrawalAmount(),
			withdrawalRequestDto.getAccountNum(),
			withdrawalRequestDto.getAccountHolderName()
		);

		return new BaseResponse<>("Success", HttpStatus.OK, "리워드 인출 성공");
	}

	@GetMapping("/withdrawals")
	@Transactional(readOnly = true)
	public BaseResponse<List<WithdrawalHistoryDto>> getWithdrawalHistory(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int pageSize
		// @AuthenticationPrincipal CustomUserDetails userDetails,
	) {
		// Long userId = getUserIdFromUserDetails(userDetails);
		Long userId = 1L;
		List<WithdrawalHistoryDto> withdrawalHistory = rewardsWithdrawalService.getWithdrawalHistory(userId, page,
			pageSize);

		return new BaseResponse<>(withdrawalHistory, HttpStatus.OK, "인출내역 조회 성공");
	}
}