package com.miguelandaluz.oneboxcart.repository.dao;

import java.util.List;

import com.miguelandaluz.oneboxcart.entities.ProductsJPA;

public interface ProductsDAO {

	public List<ProductsJPA> getAllProducts() throws Exception;
	
}
