package com.miguelandaluz.oneboxcart.service;

import java.util.List;

import com.miguelandaluz.oneboxcart.dto.CartProductsDTO;
import com.miguelandaluz.oneboxcart.entities.ProductsJPA;

public interface ProductsService {

	public void addProducts(int cartId, String lng);
	
	public List<CartProductsDTO> getCartProducts(int cartId, String lng);
	
	public List<ProductsJPA> getAllProducts(String lng);
}
