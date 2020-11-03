package com.kpst.demo.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.CannotCreateTransactionException;

import com.kpst.demo.dao.AbstractDao;
import com.kpst.demo.domain.AbstractEntity;
import com.kpst.demo.exception.ServiceUnavailableException;

public abstract class AbstractDaoImpl<E extends AbstractEntity> implements AbstractDao<E>{

	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = LogManager.getLogger(AbstractDaoImpl.class);
	
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void saveOrUpdate(E e) throws ServiceUnavailableException {
		try {
			Session session = getCurrentSession();
			session.saveOrUpdate(e);
			session.flush();
		} catch (CannotCreateTransactionException exception) {
			logger.error("Exception while saving: " + e);
			logger.error(exception);
			throw new ServiceUnavailableException(exception);
		} catch (Exception exception) {
			logger.error("Exception while saving: " + e);
			throw new ServiceUnavailableException(exception);
		} finally {
		}
	}
	
	@Override
	public void delete(E e) throws ServiceUnavailableException {
		try {
			Session session = getCurrentSession();
			session.delete(e);
			session.flush();
		} catch (CannotCreateTransactionException exception) {
			logger.error(exception);
			throw new ServiceUnavailableException(exception);
		} catch (Exception exception) {
			logger.error(exception);
			throw new ServiceUnavailableException(exception);
		}
	}

	public boolean delete(String queryString, int id) {
		Session session = getCurrentSession();
		Query query = session.createQuery(queryString);
		query.setParameter("id", id);
		query.executeUpdate();
		return true;
	}
	
	public List<E> getAll(String queryString) {
		List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString);
		entityList = query.list();
		for (E entity : entityList) {
			Hibernate.initialize(entity);
		}
		return entityList;
	}
	
	
}
