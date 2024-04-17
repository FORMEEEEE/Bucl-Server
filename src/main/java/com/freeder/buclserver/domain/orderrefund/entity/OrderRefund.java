package com.freeder.buclserver.domain.orderrefund.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.freeder.buclserver.global.mixin.TimestampMixin;

import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "order_refund")
public class OrderRefund extends TimestampMixin {
	@Id
	@Column(name = "order_refund_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "refund_amount")
	private int refundAmount;

	@Column(name = "reward_use_amount")
	private int rewardUseAmount;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;
}
