package com.miguelandaluz.oneboxcart.repository.dao;

import java.util.List;

import com.miguelandaluz.oneboxcart.dto.CartProductsDTO;

public interface CartProductsDAO {
	
	public List<CartProductsDTO> getProductsInCart(int cartId, String lng) throws Exception;
	
	public void addProduct(int cartId, int productId, int quantity) throws Exception;
	
	public void updateProductInCart(int cartId, int productId, int quantity) throws Exception;
	
}
