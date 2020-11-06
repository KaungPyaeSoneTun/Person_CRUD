package com.kpst.demo.dao.impl;

import org.springframework.stereotype.Repository;

import com.kpst.demo.dao.PersonDao;
import com.kpst.demo.domain.Person;
import com.kpst.demo.exception.ServiceUnavailableException;

@Repository
public class PersonDaoImpl extends AbstractDaoImpl<Person> implements PersonDao {
	
	protected PersonDaoImpl() {
		super(Person.class);
	}

	@Override
	public void save(Person person) throws ServiceUnavailableException {
		saveOrUpdate(person);
	}
}
