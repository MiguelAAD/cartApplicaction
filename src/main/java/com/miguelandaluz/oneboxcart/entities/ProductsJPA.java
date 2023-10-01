package com.miguelandaluz.oneboxcart.entities;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="PRODUCTS")
@Cacheable(false)
@EqualsAndHashCode
@NamedNativeQueries({
	@NamedNativeQuery(
			name = ProductsJPA.ALL_PRODUCTS,
			query = "SELECT * FROM products"
	)
})
public class ProductsJPA {

	public static final String ALL_PRODUCTS = "ProductsJPA.allProducts";
	
	@Id
	@Column(name="PRODUCT_ID")
	private int productId;
	
	@Column(name="ESP_NAME")
	private String espName;
	
	@Column(name="ENG_NAME")
	private String engName;
	
	@Column(name="ESP_DESC")
	private String espDesc;
	
	@Column(name="ENG_DESC")
	private String engDesc;
	
	@Column(name="VALUE")
	private float value;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getEspName() {
		return espName;
	}

	public void setEspName(String espName) {
		this.espName = espName;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getEspDesc() {
		return espDesc;
	}

	public void setEspDesc(String espDesc) {
		this.espDesc = espDesc;
	}

	public String getEngDesc() {
		return engDesc;
	}

	public void setEngDesc(String engDesc) {
		this.engDesc = engDesc;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
}
