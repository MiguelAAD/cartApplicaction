package com.miguelandaluz.oneboxcart.entities;

import java.util.Objects;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;

@Entity
@Table(name="CART_PRODUCTS")
@Cacheable(false)
@NamedNativeQueries({
	@NamedNativeQuery(
			name = CartProductsJPA.SELECT_CURRENT_CART_PRODUCTS,
			query = "SELECT CART_PRODUCTS.CART_ID, CART_PRODUCTS.PRODUCT_ID, CART_PRODUCTS.QUANTITY, PRODUCTS.ESP_NAME, "
					+ "PRODUCTS.ENG_NAME, PRODUCTS.ESP_DESC, PRODUCTS.ENG_DESC, PRODUCTS.VALUE FROM cart_products "
					+ "JOIN PRODUCTS ON CART_PRODUCTS.PRODUCT_ID = PRODUCTS.PRODUCT_ID WHERE CART_PRODUCTS.CART_ID=?"
	),
	@NamedNativeQuery(
			name = CartProductsJPA.ADD_PRODUTC_CART,
			query = "INSERT INTO cart_products (cart_id, product_id, quantity) VALUES (? , ?, ?)"
	),
	@NamedNativeQuery(
			name = CartProductsJPA.UPDATE_PRODUCT_CART,
			query = "UPDATE cart_products SET QUANTITY = ? WHERE cart_Id = ? AND product_Id = ?"
	),
})
public class CartProductsJPA {

	public static final String SELECT_CURRENT_CART_PRODUCTS = "CartProductsJPA.currentCartProducts";
	
	public static final String ADD_PRODUTC_CART = "CartProductsJPA.addCartProducts";

	public static final String UPDATE_PRODUCT_CART = "CartProductsJPA.updateCartProducts";

	@Id
	@Column(name="CART_ID")
	private int cartId;
	
	@Column(name="PRODUCT_ID")
	private int productId;
	
	@Column(name="QUANTITY")
	private int quantity;

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId, productId, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartProductsJPA other = (CartProductsJPA) obj;
		return cartId == other.cartId && productId == other.productId && quantity == other.quantity;
	}

	@Override
	public String toString() {
		return "CartProductsJPA [cartId=" + cartId + ", productId=" + productId + ", quantity=" + quantity + "]";
	}
}
