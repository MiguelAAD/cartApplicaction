package com.miguelandaluz.oneboxcart.utils;

import com.miguelandaluz.oneboxcart.constants.AppConstants;
import com.miguelandaluz.oneboxcart.constants.Texts;

public class LngUtils {
	
	public static String getText(Texts text, String lng) {
		String result = "";
		
		if(lng.equals(AppConstants.LNGSPANISH)) {
			result = text.getEsp();
		} else if (lng.equals(AppConstants.LNGENGLISH)) {
			result = text.getEng();
		}
		
		return result;
	}
}
