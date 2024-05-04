package com.freeder.buclserver.domain.product.dto;

import com.freeder.buclserver.domain.productai.entity.ProductAi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class ProductDTO {
	private Long productId;
	private Long productCode;
	private String name;
	private String brandName;
	private String imagePath;
	private int salePrice;
	private int consumerPrice;
	private float reward;
	private boolean wished;
	private Object listCount;
	private Object suggestionCount;
	private ProductAiDto productAiDatas;

	@Getter
	@AllArgsConstructor
	@Setter
	@NoArgsConstructor
	public static class ProductAiDto {
		private float average;
		private String mdComment;
		private String summary;
		private Long totalCnt;
	}

	public static ProductAiDto convertDto(Optional<ProductAi> productAi){
		return productAi.map(ai -> new ProductAiDto(
				ai.getAverage(),
				ai.getMdComment(),
				ai.getSummary(),
				ai.getTotalCnt()
		)).orElseGet(ProductAiDto::new);
	}

}

