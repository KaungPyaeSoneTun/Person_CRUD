package com.kpst.demo.dao;

import java.time.LocalDate;
import java.util.List;

import com.kpst.demo.exception.ServiceUnavailableException;

public interface AbstractDao<E> {
	
	public void saveOrUpdate(E e) throws ServiceUnavailableException;

	public void delete(E e) throws ServiceUnavailableException;
	
	public boolean delete(String queryString, int id);

	public long getCount(String queryString);
	
	public List<E> getAll(String queryString);

	public List<E> findByDate(String queryString, LocalDate date);

	public List<E> findByDateRange(String queryString, LocalDate startDate, LocalDate endDate);

	public List<E> findByInteger(String queryString, int data);

	public List<E> findByInteger(String queryString, int data, int data1);

	public List<E> findByString(String queryString, String data);
}
