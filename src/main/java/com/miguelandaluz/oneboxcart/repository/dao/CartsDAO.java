package com.miguelandaluz.oneboxcart.repository.dao;

import java.sql.Timestamp;
import java.util.List;

import com.miguelandaluz.oneboxcart.entities.CartsJPA;

public interface CartsDAO {

	public int createCart(Timestamp currentDate) throws Exception;
	
	public CartsJPA getCart(int id) throws Exception;
	
	public List<CartsJPA> getAllCarts() throws Exception;
	
	public void deleteCart(int cartId) throws Exception;
	
	public void updateCart(int cartId) throws Exception;
}
