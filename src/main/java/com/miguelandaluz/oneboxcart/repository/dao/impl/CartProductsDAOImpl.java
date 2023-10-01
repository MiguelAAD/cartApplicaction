package com.miguelandaluz.oneboxcart.repository.dao.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import com.miguelandaluz.oneboxcart.constants.AppConstants;
import com.miguelandaluz.oneboxcart.constants.Texts;
import com.miguelandaluz.oneboxcart.dto.CartProductsDTO;
import com.miguelandaluz.oneboxcart.entities.CartProductsJPA;
import com.miguelandaluz.oneboxcart.entities.CartsJPA;
import com.miguelandaluz.oneboxcart.repository.dao.CartProductsDAO;
import com.miguelandaluz.oneboxcart.utils.AbstractCrudFacade;
import com.miguelandaluz.oneboxcart.utils.LngUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CartProductsDAOImpl extends AbstractCrudFacade<CartProductsJPA> implements CartProductsDAO{

	@PersistenceContext(unitName = AppConstants.DB)
	private EntityManager em  =  jakarta.persistence.Persistence.createEntityManagerFactory(AppConstants.DB).createEntityManager();
	
	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}
	
	@Override
	public List<CartProductsDTO> getProductsInCart(int cartId, String lng) throws Exception {
		List<CartProductsDTO> cartProductsList = new ArrayList<CartProductsDTO>();
		
		List<Object> parametres = new LinkedList<Object>();
		parametres.add(cartId);
		
		List<Object[]> regs = findWithNamedNativeQuery(CartProductsJPA.SELECT_CURRENT_CART_PRODUCTS, parametres);
		
		if(!regs.isEmpty()) {
			for(Object[] registry : regs) {
				CartProductsDTO cartProduct = new CartProductsDTO();
				cartProduct.setCartId(Integer.valueOf(registry[0].toString()));
				cartProduct.setProductId(Integer.valueOf(registry[1].toString()));
				cartProduct.setQuantity(Integer.valueOf(registry[2].toString()));
				cartProduct.setName((lng.equals(AppConstants.LNGSPANISH) ? registry[3].toString() : registry[4].toString()));
				cartProduct.setDesc((lng.equals(AppConstants.LNGSPANISH) ? registry[5].toString() : registry[6].toString()));
				cartProduct.setTotalAmount(Float.valueOf(registry[7].toString()) * cartProduct.getQuantity());
				
				cartProductsList.add(cartProduct);
			}
		}
		
		return cartProductsList;
	}

	@Override
	public void addProduct(int cartId, int productId, int quantity) throws Exception {
		List<Object> parametres = new LinkedList<Object>();
		parametres.add(cartId);
		parametres.add(productId);
		parametres.add(quantity);
		
		persistWithNativeQuery(CartProductsJPA.ADD_PRODUTC_CART, parametres);;
	}

	@Override
	public void updateProductInCart(int cartId, int productId, int quantity) throws Exception {
		List<Object> parametres = new LinkedList<Object>();
		parametres.add(quantity);
		parametres.add(cartId);
		parametres.add(productId);
		
		persistWithNativeQuery(CartProductsJPA.UPDATE_PRODUCT_CART, parametres);;
	}
}
