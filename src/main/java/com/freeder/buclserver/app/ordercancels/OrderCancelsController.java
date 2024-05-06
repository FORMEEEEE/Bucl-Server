package com.freeder.buclserver.app.ordercancels;

import com.freeder.buclserver.app.ordercancels.dto.OrderCancelResponseDto;
import com.freeder.buclserver.core.security.CustomUserDetails;
import com.freeder.buclserver.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/order-cancels")
@Tag(name = "order-cancels 관련 API", description = "주문취소 관련 API")
public class OrderCancelsController {

	private String testSocialId = "3195839289"; //"3895839289";
	private final OrderCancelsService orderCancelsService;

	@PostMapping(path = "/{order_code}")
	public BaseResponse<OrderCancelResponseDto> addOrderCancel(
		@PathVariable(name = "order_code") String orderCode
	) {
		OrderCancelResponseDto orderCancelResponseDto = orderCancelsService.createOrderCancel(testSocialId, orderCode);
		return new BaseResponse<>(orderCancelResponseDto, HttpStatus.CREATED, "주문 취소 됐습니다.");
	}

	@PutMapping(path = "/{order_code}/approval")
	public BaseResponse<String> modifyOrderCancelApproval(@PathVariable(name = "order_code") String orderCode) {
		orderCancelsService.updateOrderCancelApproval(testSocialId, orderCode);
		return new BaseResponse<>(orderCode, HttpStatus.OK, orderCode + " 주문 취소 승인 완료했습니다.");
	}

	@GetMapping("/api/v1/order-cancels")
	public BaseResponse<?> 주문취소목록조회(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int pageSize
	){
		return orderCancelsService.주문취소목록조회(userDetails,page,pageSize);
	}
}
