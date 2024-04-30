package com.freeder.buclserver.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}

