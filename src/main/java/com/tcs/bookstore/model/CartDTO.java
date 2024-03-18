package com.tcs.bookstore.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartDTO {
	@NotNull
	private Integer productId;
	
	private String productName;
	
	private Double price;
	
	@Min(1)
	private Integer quantity;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
