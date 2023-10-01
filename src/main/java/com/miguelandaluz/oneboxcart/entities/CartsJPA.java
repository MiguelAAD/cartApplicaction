package com.miguelandaluz.oneboxcart.entities;

import java.sql.Timestamp;
import java.util.Objects;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;

@Entity
@Table(name="CARTS")
@Cacheable
@NamedNativeQueries({
	@NamedNativeQuery(
			name = CartsJPA.NEXT_VAL,
			query = "SELECT NEXTVAL(CART_ID_SEQ)"
	),
	@NamedNativeQuery(
			name = CartsJPA.NEW_CART,
			query = "INSERT INTO carts (cart_id, mod_date) VALUES (? , ?)"
	),
	@NamedNativeQuery(
			name = CartsJPA.ALL_CARTS,
			query = "SELECT * FROM carts"
	),
	@NamedNativeQuery(
			name = CartsJPA.GET_CART,
			query = "SELECT * FROM carts WHERE cart_id = ?"
	),
	@NamedNativeQuery(
			name = CartsJPA.DELETE_CART,
			query = "DELETE FROM carts WHERE cart_Id = ?"
	),
	@NamedNativeQuery(
			name = CartsJPA.UPDATE_CART,
			query = "UPDATE carts SET mod_date = CURRENT_TIMESTAMP WHERE cart_Id = ?"
	),
})
public class CartsJPA {
	
	public static final String NEXT_VAL = "CartsJPA.nextval";
	
	public static final String NEW_CART = "CartsJPA.newCart";
	
	public static final String ALL_CARTS = "CartsJPA.allCarts";
	
	public static final String GET_CART = "CartsJPA.getCart";
	
	public static final String DELETE_CART = "CartsJPA.deleteCart";
	
	public static final String UPDATE_CART = "CartsJPA.updateCart";
	
	@Id
	@Column(name="CART_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartID;
	
	@Column(name="MOD_DATE")
	private Timestamp modDate;
	
	public int getCartID() {
		return cartID;
	}

	public void setCartID(int cartID) {
		this.cartID = cartID;
	}

	public Timestamp getModDate() {
		return modDate;
	}

	public void setModDate(Timestamp modDate) {
		this.modDate = modDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartID, modDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartsJPA other = (CartsJPA) obj;
		return cartID == other.cartID && Objects.equals(modDate, other.modDate);
	}

	@Override
	public String toString() {
		return "CartsJPA [cartID=" + cartID + ", modDate=" + modDate + "]";
	}
}
