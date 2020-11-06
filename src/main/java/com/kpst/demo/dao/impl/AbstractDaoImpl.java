package com.kpst.demo.dao.impl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.CannotCreateTransactionException;

import com.kpst.demo.dao.AbstractDao;
import com.kpst.demo.domain.AbstractEntity;
import com.kpst.demo.exception.ServiceUnavailableException;

@Transactional
public abstract class AbstractDaoImpl<E extends AbstractEntity> implements AbstractDao<E>{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Class<E> entityClass;
	
	private static Logger logger = LogManager.getLogger(AbstractDaoImpl.class);
	
	protected AbstractDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}
	
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
		query.setParameter("dataInput", id);
		query.executeUpdate();
		return true;
	}
	
	public boolean delete(String queryString, long id) {
		Session session = getCurrentSession();
		Query query = session.createQuery(queryString);
		query.setParameter("dataInput", id);
		query.executeUpdate();
		return true;
	}
	
	public List<E> getAll(String queryString) {
		List<E> entityList;
		TypedQuery<E> query = getCurrentSession().createQuery(queryString, entityClass);
		entityList = query.getResultList();
		for (E entity : entityList) {
			Hibernate.initialize(entity);
		}
		return entityList;
	}
	
	@Override
	public long getCount(String queryString) {
		long count;
		Query query = getCurrentSession().createQuery(queryString);
		count = (long) query.uniqueResult();
		return count;
	}
	
	@Override
	public List<E> findByDate(String queryString, LocalDate date) {
		List<E> entityList;

		Query query = getCurrentSession().createQuery(queryString).setParameter("dateInput", date);

		entityList = query.list();
		for (E entity : entityList)
			Hibernate.initialize(entity);
		return entityList;
	}
	
	@Override
	public List<E> findByInteger(String queryString, int data) {
		List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString).setParameter("dataInput", data);
		entityList = query.list();
		for (E entity : entityList)
			Hibernate.initialize(entity);
		return entityList;
	}
	
	@Override
	public List<E> findByLong(String queryString, long data) {
		List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString).setParameter("dataInput", data);
		entityList = query.list();
		for (E entity : entityList)
			Hibernate.initialize(entity);
		return entityList;
	}

	public List<E> findByInteger(String queryString, int data, int data1) {
		List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString).setParameter("dataInput", data)
				.setParameter("dataInput1", data1);
		entityList = query.list();
		for (E entity : entityList)
			Hibernate.initialize(entity);
		return entityList;
	}
	
	@Override
	public List<E> findByString(String queryString, String data) {
		List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString)
				.setResultTransformer(RootEntityResultTransformer.INSTANCE).setParameter("dataInput", data);
		entityList = query.list();
		for (E entity : entityList)
			Hibernate.initialize(entity);
		return entityList;
	}
}
