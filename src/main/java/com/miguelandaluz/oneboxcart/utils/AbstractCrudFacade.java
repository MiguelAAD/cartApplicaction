package com.miguelandaluz.oneboxcart.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Query;

/**
 * Permite realizar operaciones CRUD
 */

@Service
@SuppressWarnings("unchecked")
@PersistenceUnit
public abstract class AbstractCrudFacade<T> {

	private static final String QUERY_TIMEOUT = "javax.persistence.query.timeout";

	protected abstract EntityManager getEntityManager();

	protected Class<T> type;
           
	public AbstractCrudFacade() {
		super();
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class<T>) pt.getActualTypeArguments()[0];
	}

	protected Long getNextVal(String queryName) throws Exception { 
		List<Object[]> nextValList = findWithNamedNativeQuery(queryName, null);
        if (CollectionUtils.isEmpty(nextValList)) {
            System.out.println("No se ha recuperado correctamente la secuencia");
            throw new Exception("No se ha recuperado correctamente la secuencia");
        } else {
            Object nextValObject = nextValList.get(0);

            if (nextValObject == null) {
                System.out.println("No se ha recuperado correctamente la secuencia");
                throw new Exception("No se ha recuperado correctamente la secuencia");
            } else {
                if (nextValObject instanceof Long) {
                    return (Long) nextValObject;
                } else {
                    System.out.println("La secuencia recuperada no ha devuelto un Long");
                    throw new Exception("La secuencia recuperada no ha devuelto un Long");
                }
            }
        }
    }
	
	protected void persistWithNativeQuery(String queryName, List<Object> parameters) throws Exception {
    	EntityTransaction tx = getEntityManager().getTransaction();
    	tx.begin();
		try {

            getEntityManager().setProperty(QUERY_TIMEOUT, 90000);
            Query query = getEntityManager().createNamedQuery(queryName);
            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    Object parameter = parameters.get(i);
                    query.setParameter(i + 1, parameter);
                }
            }
            query.executeUpdate();
            tx.commit();
        } catch (PersistenceException pe) {
        	System.out.println("[@#INVOCACION_JPA|ERROR_EXCEPTION|PersistenceException en persistWithNativeQuery");
            tx.rollback();
            throw new Exception(pe);
        } catch (Exception e) {
        	System.out.println("[@#INVOCACION_JPA|ERROR_EXCEPTION|Exception en persistWithNativeQuery");
            tx.rollback();
            throw new Exception(e);
        }
	}
	
	protected List<Object[]> findWithNamedNativeQuery(String queryName, List<Object> parameters) throws Exception {
        try {
            getEntityManager().setProperty(QUERY_TIMEOUT, 90000);
            Query query = getEntityManager().createNamedQuery(queryName);
            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    Object parameter = parameters.get(i);
                    query.setParameter(i + 1, parameter);
                }
            }
            List<Object[]> listaResultados = query.getResultList();
            return listaResultados;
        } catch (PersistenceException pe) {
        	System.out.println("[@#INVOCACION_JPA|ERROR_EXCEPTION|PersistenceException en findWithNamedNativeQuery");
            throw new Exception(pe);
        } catch (Exception e) {
        	System.out.println("[@#INVOCACION_JPA|ERROR_EXCEPTION|Exception en findWithNamedNativeQuery");
            throw new Exception(e);
        }
    }
}
