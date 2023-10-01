package com.miguelandaluz.oneboxcart.service;

public interface CartsService {

	public void createCart(String lng);
	
	public void cartManager(String lng, int option) throws Exception;
}
