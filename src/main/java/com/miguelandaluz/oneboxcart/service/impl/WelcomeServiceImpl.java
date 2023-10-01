package com.miguelandaluz.oneboxcart.service.impl;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.miguelandaluz.oneboxcart.constants.AppConstants;
import com.miguelandaluz.oneboxcart.constants.Texts;
import com.miguelandaluz.oneboxcart.service.WelcomeService;
import com.miguelandaluz.oneboxcart.utils.LngUtils;

public class WelcomeServiceImpl implements WelcomeService{
	
	private static String LNG;
	
	public static Scanner entry = new Scanner(System.in);
	
	@Override
	public void welcome(){
		String inputLng = "";
		
		while(inputLng.equals("")) {
			System.out.println(AppConstants.LNG_WELCOME);
			System.out.println(AppConstants.LNG_INST);
			
			inputLng = entry.nextLine().toLowerCase();
			
			if(!inputLng.equals(AppConstants.LNGSPANISH) && !inputLng.equals(AppConstants.LNGENGLISH)) {
				inputLng = "";
				System.out.println(AppConstants.LNG_NOT_SUPP);				
			}
		}
		
		LNG = inputLng;
		menu();
	}
	
	@Override
	public void menu() {
		int option = 0;
		String entryOption = "";
		
		System.out.println(LngUtils.getText(Texts.WELCOME, LNG));
		
		while(option == 0) {
			System.out.println(LngUtils.getText(Texts.MENU_QUESTION, LNG));
			System.out.println(LngUtils.getText(Texts.MENU_INSTRUCTION, LNG));
			System.out.println(LngUtils.getText(Texts.MENU_1, LNG));
			System.out.println(LngUtils.getText(Texts.MENU_2, LNG));
			System.out.println(LngUtils.getText(Texts.MENU_3, LNG));
			
			entryOption = entry.nextLine();
			
			if(!entryOption.equals("") && entryOption.chars().allMatch(Character::isDigit)) {
				option = Integer.valueOf(entryOption);
				if (option != 1 && option != 2 && option != 3) {
					option = 0;
					System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, LNG));	
				}
			} else {
				System.out.println(LngUtils.getText(Texts.NOT_AN_OPTION, LNG));	
			}
		}
		
		CartsServiceImpl cartsService = new CartsServiceImpl();
		
		if(AppConstants.OPTION_1 == option) {
			cartsService.createCart(LNG);
		} else if (AppConstants.OPTION_2 == option) {
			try {
				cartsService.cartManager(LNG, option);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(LngUtils.getText(Texts.CRIT_ERROR, LNG) + "[WelcomeService]menu: " + e);
			}
		} else if (AppConstants.OPTION_3 == option) {
			try {
				cartsService.cartManager(LNG, option);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(LngUtils.getText(Texts.CRIT_ERROR, LNG) + "[WelcomeService]menu: " + e);
			}
		}
	}
}
