package com.kpst.demo.dao;

import com.kpst.demo.domain.Person;
import com.kpst.demo.exception.ServiceUnavailableException;

public interface PersonDao extends AbstractDao<Person>{
	public void save(Person person) throws ServiceUnavailableException ;
}
