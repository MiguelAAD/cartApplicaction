package com.miguelandaluz.oneboxcart.service.impl; 

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import com.miguelandaluz.oneboxcart.constants.AppConstants;
import com.miguelandaluz.oneboxcart.constants.Texts;
import com.miguelandaluz.oneboxcart.dto.CartProductsDTO;
import com.miguelandaluz.oneboxcart.entities.CartsJPA;
import com.miguelandaluz.oneboxcart.repository.dao.CartsDAO;
import com.miguelandaluz.oneboxcart.repository.dao.impl.CartsDAOImpl;
import com.miguelandaluz.oneboxcart.service.CartsService;
import com.miguelandaluz.oneboxcart.service.ProductsService;
import com.miguelandaluz.oneboxcart.service.WelcomeService;
import com.miguelandaluz.oneboxcart.utils.LngUtils;

public class CartsServiceImpl implements CartsService{

	private ProductsService productsService = new ProductsServiceImpl();
	
	private WelcomeService welcomeService = new WelcomeServiceImpl();
	
	private Scanner entry = new Scanner(System.in);
	
	private CartsDAO cartsDAO = new CartsDAOImpl();

	@Override
	public void createCart(String lng) {
		//Creamos nuevo caro
		System.out.println(LngUtils.getText(Texts.CART_CRT_WLC, lng));
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		int cartId = 0;
		
		try {
			cartId = cartsDAO.createCart(timestamp);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(LngUtils.getText(Texts.CRIT_ERROR, lng) + "[CartsService]createCart: " + e);
			welcomeService.menu();
		}
		
		System.out.println(LngUtils.getText(Texts.CART_CRT_END, lng) + cartId);		
		System.out.println(LngUtils.getText(Texts.MENU_QUESTION, lng));
		
		exit(cartId, lng);
	}

	@Override
	public void cartManager(String lng, int option) throws Exception {
		//Obtenemos los carros activos
		List<CartsJPA> cartList = cartsDAO.getAllCarts();
		
		if(cartList.isEmpty()) { //Si no hay carros activos, mensaje y vuelta al menu
			System.out.println(LngUtils.getText(Texts.CART_SRC_BCK, lng));
			welcomeService.menu();
		} else {
			if(AppConstants.OPTION_2 == option) { //Ver carros
				searchCart(lng, cartList);
			} else if (AppConstants.OPTION_3 == option) { //Eliminar
				deleteCart(lng);
			}
		}
	}
	
	private void searchCart(String lng, List<CartsJPA> cartList) throws Exception {
		
		System.out.println(LngUtils.getText(Texts.CART_SRC_WLC, lng));		
		System.out.println(LngUtils.getText(Texts.CART_SRC_LST, lng));
		//mostramoos cestas disponibles
		cartList.forEach(cart -> System.out.println("Id: " + cart.getCartID() + 
				LngUtils.getText(Texts.CART_SRC_DAT, lng) + cart.getModDate()));
		
		boolean validId = false;
		int cartId = 0;
		
		//Obtenemos el id de la cesta
		while(!validId) {
			System.out.println(LngUtils.getText(Texts.CART_SRC_INS, lng));
			
			String entryCartId = entry.nextLine();
			
			if(!entryCartId.equals("") && entryCartId.chars().allMatch(Character::isDigit)) { //Camprobamos que es un id valido
				if(Integer.valueOf(entryCartId) > 0) {
					cartId = Integer.valueOf(entryCartId);
						for(CartsJPA cart : cartList) {
							if(cart.getCartID() == cartId) {//Si ews un id existente, mostramos sus productos
								
								List<CartProductsDTO> cartProducts = productsService.getCartProducts(cartId, lng);
								cartProducts.forEach(cartProduct -> System.out.println(cartProduct.getName() + " - " + cartProduct.getDesc() + " - " +
										cartProduct.getQuantity() + " - " + cartProduct.getTotalAmount() + " €"));
								validId = true;
								break;
							}
						}
						if(!validId) {
							System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));
						}
				} else {
					System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));
				}
			} else {
				System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));
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
		
		exit(cartId, lng);
	}

	private void deleteCart(String lng) throws Exception {
		System.out.println(LngUtils.getText(Texts.CART_DEL_WLC, lng));		
		
		String entryCartId = "";
		int cartId = 0;
		boolean validId = false;
		boolean exit = false;
		int option = 0;
		
		while(!validId) {
			System.out.println(LngUtils.getText(Texts.CART_DEL_INS, lng));

			entryCartId = entry.nextLine();
			
			validId = delentingIdValidator(entryCartId, lng);
		}
		
		if(validId) {
			cartId  = Integer.valueOf(entryCartId);
		}
			
		System.out.println(LngUtils.getText(Texts.CART_DEL_CPR, lng) + entryCartId);
		
		String entryOption = "";
		
		while(option == 0) {
			System.out.println(LngUtils.getText(Texts.CART_DEL_QTN, lng));
			System.out.println(LngUtils.getText(Texts.CART_DEL_AN1, lng));
			System.out.println(LngUtils.getText(Texts.CART_DEL_AN2, lng));
			System.out.println(LngUtils.getText(Texts.CART_DEL_AN3, lng));
			
			entryOption = entry.nextLine();
			
			if(!entryOption.equals("") &&entryOption.chars().allMatch(Character::isDigit)) {
				option = Integer.valueOf(entryOption);
				if (option != 1 && option != 2 && option != 3) {
					option = 0;
					System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));	
				}
			} else {
				System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));	
			}
		}
			
		if(AppConstants.OPTION_1 == option) {
			deleteCart(lng);
		} else if (AppConstants.OPTION_3 == option) {
			exit = true;
		}
		
		if(!exit) {
			cartsDAO.deleteCart(cartId);
			System.out.println(LngUtils.getText(Texts.CART_DEL_TED, lng));
			exit = true;
		}
		
		if(exit) {
			welcomeService.menu();
		}
	}
	
	private void exit(int cartId, String lng) {
		int option = 0;
		String entryOption = "";
		
		while(option == 0) {

			System.out.println(LngUtils.getText(Texts.MENU_INSTRUCTION, lng));
			System.out.println(LngUtils.getText(Texts.CART_CRT_1, lng));
			System.out.println(LngUtils.getText(Texts.CART_CRT_2, lng));
			
			entryOption = entry.nextLine();
			
			if(!entryOption.equals("") && entryOption.chars().allMatch(Character::isDigit)) {
				option = Integer.valueOf(entryOption);
				if (option != 1 && option != 2) {
					option = 0;
					System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));	
				}
			} else {
				System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));	
			}
		}

		if(AppConstants.OPTION_1 == option) {
			productsService.addProducts(cartId, lng);
		} else if (AppConstants.OPTION_2 == option) {
			welcomeService.menu();
		}
	}
	
	private boolean delentingIdValidator(String entryCartId, String lng) throws Exception {
		int cartId = 0;
		
		if(!entryCartId.equals("") && entryCartId.chars().allMatch(Character::isDigit)) {
			cartId = Integer.valueOf(entryCartId);
			if(cartId > 0) {
				CartsJPA cart = cartsDAO.getCart(cartId);
				if (cart != null && cart.getCartID() == cartId){
					return true;
				} else {
					System.out.println(LngUtils.getText(Texts.CART_DEL_NEX, lng));
					return false;
				}
			} else {
				System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));	
				return false;
			}
		} else {
			System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, lng));
			return false;
		}
	}
}
