package com.kpst.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpst.demo.dao.PersonDao;
import com.kpst.demo.domain.Person;
import com.kpst.demo.exception.ServiceUnavailableException;
import com.kpst.demo.service.PersonService;

@Service("personService")
@Transactional(readOnly = false)
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	PersonDao personDao;
	
	@Override
	public void hibernateInitialize(Person person) {
	}

	@Override
	public void hibernateInitializePersonList(List<Person> personList) {
	}

	@Override
	public void saveOrUpdate(Person person) throws ServiceUnavailableException {
		if(person != null) {
			personDao.save(person);
		}
	}

	@Override
	public long countPerson() {
		return personDao.getCount("select count(person) from Person person");
	}

	@Override
	public List<Person> findPersonById(long id) {
		if(id <= 0)
			return null;
		String queryString = "select person from Person person where person.id = :dataInput";
		List<Person> personList = personDao.findByLong(queryString, id);
		hibernateInitializePersonList(personList);
		return personList;
	}

	@Override
	public Person findPersonByIdSingle(long id) {
		if(id <= 0 )
			return null;
		List<Person> personList = findPersonById(id);
		if(CollectionUtils.isEmpty(personList))
			return null;
		Person person = personList.get(0);
		hibernateInitialize(person);
		return person;
	}

	@Override
	public List<Person> getAllPerson() {
		List<Person> personList = personDao.getAll("select person from Person person");
		if(CollectionUtils.isEmpty(personList))
			return null;
		hibernateInitializePersonList(personList);
		return personList;
	}

	@Override
	public boolean deletePerson(long id) {
		if(id <= 0)
			return false;
		return personDao.delete("delete from Person person where person.id = :dataInput", id);
	}

}
