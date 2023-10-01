package com.miguelandaluz.oneboxcart.repository.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.miguelandaluz.oneboxcart.constants.AppConstants;
import com.miguelandaluz.oneboxcart.entities.CartsJPA;
import com.miguelandaluz.oneboxcart.repository.dao.CartsDAO;
import com.miguelandaluz.oneboxcart.utils.AbstractCrudFacade;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CartsDAOImpl extends AbstractCrudFacade<CartsJPA> implements CartsDAO{

	@PersistenceContext(unitName = AppConstants.DB)
	private EntityManager em = jakarta.persistence.Persistence.createEntityManagerFactory(AppConstants.DB).createEntityManager();
	
	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}
	
	@Override
	public int createCart(Timestamp currentDate) throws Exception {
		int cartId = getNextVal(CartsJPA.NEXT_VAL).intValue();
		
		List<Object> parametres = new LinkedList<Object>();
		parametres.add(cartId);
		parametres.add(currentDate);
		
		persistWithNativeQuery(CartsJPA.NEW_CART, parametres);;
		
		return cartId;
	}

	@Override
	public CartsJPA getCart(int id) throws Exception {
		List<Object> parametres = new LinkedList<Object>();
		parametres.add(id);
		
		List<Object[]> regs = findWithNamedNativeQuery(CartsJPA.GET_CART, parametres);
		
		CartsJPA carts = new CartsJPA();
		
		if(!regs.isEmpty()) {
				int i = 0;
				carts.setCartID(Integer.valueOf(regs.get(0)[i++].toString()));
				carts.setModDate((Timestamp) regs.get(0)[i++]);
		}
		
		return carts;
	}

	@Override
	public List<CartsJPA> getAllCarts() throws Exception {
		List<CartsJPA> cartsList = new ArrayList<CartsJPA>();
		
		List<Object> parametres = new LinkedList<Object>();
		
		List<Object[]> regs = findWithNamedNativeQuery(CartsJPA.ALL_CARTS, parametres);
		
		if(!regs.isEmpty()) {
			for(Object[] registry : regs) {
				int i = 0;
				CartsJPA carts = new CartsJPA();
				carts.setCartID(Integer.valueOf(registry[i++].toString()));
				carts.setModDate((Timestamp) registry[i]);
				
				cartsList.add(carts);
			}
		}
		
		return cartsList;
	}

	@Override
	public void deleteCart(int cartId) throws Exception {
		
		List<Object> parametres = new LinkedList<Object>();
		parametres.add(cartId);
		
		persistWithNativeQuery(CartsJPA.DELETE_CART, parametres);
	}

	@Override
	public void updateCart(int cartId) throws Exception {
		
		List<Object> parametres = new LinkedList<Object>();
		parametres.add(cartId);
		
		persistWithNativeQuery(CartsJPA.UPDATE_CART, parametres);
	}
}
