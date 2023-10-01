package com.miguelandaluz.oneboxcart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguelandaluz.oneboxcart.OneboxcartApplication;
import com.miguelandaluz.oneboxcart.constants.AppConstants;
import com.miguelandaluz.oneboxcart.constants.Texts;
import com.miguelandaluz.oneboxcart.dto.CartProductsDTO;
import com.miguelandaluz.oneboxcart.entities.ProductsJPA;
import com.miguelandaluz.oneboxcart.repository.dao.CartProductsDAO;
import com.miguelandaluz.oneboxcart.repository.dao.CartsDAO;
import com.miguelandaluz.oneboxcart.repository.dao.ProductsDAO;
import com.miguelandaluz.oneboxcart.repository.dao.impl.CartProductsDAOImpl;
import com.miguelandaluz.oneboxcart.repository.dao.impl.CartsDAOImpl;
import com.miguelandaluz.oneboxcart.repository.dao.impl.ProductsDAOImpl;
import com.miguelandaluz.oneboxcart.service.ProductsService;
import com.miguelandaluz.oneboxcart.service.WelcomeService;
import com.miguelandaluz.oneboxcart.utils.LngUtils;

public class ProductsServiceImpl implements ProductsService{

	private CartsDAO cartsDAO = new CartsDAOImpl();
	
	private CartProductsDAO cartProductsDAO = new CartProductsDAOImpl();
	
	private WelcomeService welcomeService = new WelcomeServiceImpl();
	
	private Scanner entry = new Scanner(System.in);

	
	@Override
	public void addProducts(int cartId, String lng) {
		System.out.println(LngUtils.getText(Texts.PRD_ADD_WLC, lng));
		System.out.println(LngUtils.getText(Texts.MENU_INSTRUCTION, lng));
		
		List<ProductsJPA> products = getAllProducts(lng);
		
		//Recuperamos y mostramos todos los productos
		List<Integer> productIndexList = new ArrayList<Integer>();
		int productIndex = 1;
		for(ProductsJPA product : products) {
			System.out.println((lng.equals(AppConstants.LNGENGLISH) ? product.getEngName() + " - " + product.getEngDesc()
			: product.getEspName()  + " - " + product.getEspDesc()) + " - " + product.getValue() + " €");
			System.out.println((lng.equals(AppConstants.LNGENGLISH) ? product.getEngName() : product.getEspName()) + 
					LngUtils.getText(Texts.PRD_ADD_SEL, lng) + productIndex);
			productIndex++;
			productIndexList.add(productIndex);
		}
		
		//Comenzamos a añadir productos
		int addMoreProds = 0;
		
		do {
			//El usuario introduce y obtenemos el producto
			int productId = 0;
			
			boolean validOption = false;
			while(!validOption) {
				System.out.println(LngUtils.getText(Texts.PRD_ADD_INS, lng));
				String provIntro = entry.nextLine();
				
				if(!provIntro.equals("") && provIntro.chars().allMatch(Character::isDigit)) {
					int selectedProduct = Integer.valueOf(provIntro);
					
					if(selectedProduct < 1 || selectedProduct > productIndexList.size()) {
						System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));
					} else {
						productId = products.get(selectedProduct - 1).getProductId();
						validOption = true;
					}
				} else {
					System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));
				}
			}
			//El usuario introduce y obtenemos la cantidad
			int productAmount = 0;
			
			validOption = false;
			while(!validOption) {
				System.out.println(LngUtils.getText(Texts.PRD_ADD_AMO, lng));
				
				String provIntro = entry.nextLine();
				
				if(!provIntro.equals("") && provIntro.chars().allMatch(Character::isDigit)) {
					if(Integer.valueOf(provIntro) > 0) {
						productAmount = Integer.valueOf(provIntro);
						validOption = true;
					} else {
						System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));
					}
				} else {
					System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));
				}
			}
			
			//Añadimos el producto y la cantidad al carro
			addProductsToCart(cartId, productId, productAmount, lng);
			
			//Preguntamos si se quiere añadir más productos
			System.out.println(LngUtils.getText(Texts.PRD_ADD_MOR, lng));
			System.out.println(LngUtils.getText(Texts.MENU_INSTRUCTION, lng));
			
			validOption = false;
			while(!validOption) {
				System.out.println(LngUtils.getText(Texts.PRD_ADD_CONT, lng));
				String provIntro = entry.nextLine();
				
				if(!provIntro.equals("") && provIntro.chars().allMatch(Character::isDigit)) {
					if(Integer.valueOf(provIntro) != 0 && Integer.valueOf(provIntro) != 1) {
						System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));				
					} else {
						addMoreProds = Integer.valueOf(provIntro);
						validOption = true;
					}
				} else {
					System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));
				}
			}
			
		} while(addMoreProds == 1);

		//Redirigimos al menu principal para que el usuario decida como continuar
		welcomeService.menu();
	}

	@Override
	public List<CartProductsDTO> getCartProducts(int cartId, String lng) {
		//Método para obtener los productos en el carro
		List<CartProductsDTO> cartProductsJPAList = new ArrayList<CartProductsDTO>();
		try {
			CartProductsDAO cartProductsDAO = new CartProductsDAOImpl();
			
			cartProductsJPAList = cartProductsDAO.getProductsInCart(cartId, lng);			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(LngUtils.getText(Texts.CRIT_ERROR, lng) + "[ProductsServiceImpl]getCartProducts");
			welcomeService.menu();
		}

		return cartProductsJPAList;
	}

	@Override
	public List<ProductsJPA> getAllProducts(String lng) {
		//Método para obtener los productos disponibles para seleccionar
		List<ProductsJPA> products = new ArrayList<ProductsJPA>();
		try {
			ProductsDAO productsDAO = new ProductsDAOImpl();
			
			products = productsDAO.getAllProducts();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(LngUtils.getText(Texts.CRIT_ERROR, lng) + "[ProductsServiceImpl]addProductsToCart");
			welcomeService.menu();
		}
		return products;
	}
	
	private void addProductsToCart(int cartId, int productId, int productAmmount, String lng) {
		//Método para añadir los productos al carro
		//Comprobamos si se ha añadido el producto con anterioridad
		List<CartProductsDTO> productsInCart = getCartProducts(cartId, lng);
		boolean productInList = false;
		int amountInCart = 0;

		if(!productsInCart.isEmpty()) {
			for(CartProductsDTO cartProduct : productsInCart) {
				if(productId == cartProduct.getProductId()) {
					amountInCart = cartProduct.getQuantity();
					productInList = true;
					break;
				}
			}
		} 
		
		if(productInList) { //Si ya hay productos del seleccionado, actualizamos la entrada para evitar duplicidades
			try {
				cartProductsDAO.updateProductInCart(cartId, productId, productAmmount + amountInCart);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(LngUtils.getText(Texts.CRIT_ERROR, lng) + "[ProductsServiceImpl]addProductsToCart: no se han podido actualizar productos");
				welcomeService.menu();
			}
		} else { //Si no estaba previamente, solo se añade
			try {
				cartProductsDAO.addProduct(cartId, productId, productAmmount);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(LngUtils.getText(Texts.CRIT_ERROR, lng) + "[ProductsServiceImpl]addProductsToCart: no se han podido insertar productos");
				welcomeService.menu();
			}
		}
		//Actualizamos fecha modificación del carro
		try {
			cartsDAO.updateCart(cartId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(LngUtils.getText(Texts.CRIT_ERROR, lng) + "[ProductsServiceImpl]addProductsToCart: No se ha podido actualizar fecha cesta");
			welcomeService.menu();
		}
	}
}
