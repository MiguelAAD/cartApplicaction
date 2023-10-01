package com.miguelandaluz.oneboxcart.repository.dao.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.miguelandaluz.oneboxcart.constants.AppConstants;
import com.miguelandaluz.oneboxcart.dto.CartProductsDTO;
import com.miguelandaluz.oneboxcart.entities.CartProductsJPA;
import com.miguelandaluz.oneboxcart.entities.ProductsJPA;
import com.miguelandaluz.oneboxcart.repository.dao.ProductsDAO;
import com.miguelandaluz.oneboxcart.utils.AbstractCrudFacade;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ProductsDAOImpl extends AbstractCrudFacade<ProductsJPA> implements ProductsDAO{
	
	@PersistenceContext(unitName = AppConstants.DB)
	private EntityManager em = jakarta.persistence.Persistence.createEntityManagerFactory(AppConstants.DB).createEntityManager();
	
	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}
	
	@Override
	public List<ProductsJPA> getAllProducts() throws Exception {
		List<ProductsJPA> products = new ArrayList<ProductsJPA>();
		
		List<Object> parametres = new LinkedList<Object>();
		
		List<Object[]> regs = findWithNamedNativeQuery(ProductsJPA.ALL_PRODUCTS, parametres);

		if(!regs.isEmpty()) {
			for(Object[] registry : regs) {
				int i = 0;
				ProductsJPA product = new ProductsJPA();
				product.setProductId(Integer.valueOf(registry[i++].toString()));
				product.setEspName(registry[i++].toString());
				product.setEngName(registry[i++].toString());
				product.setEspDesc(registry[i++].toString());
				product.setEngDesc(registry[i++].toString());
				product.setValue(Float.valueOf(registry[i++].toString()));
				
				products.add(product);
			}
		}
		
		return products;
	}


}
