package com.kpst.demo.service;

import java.util.List;

import com.kpst.demo.domain.Person;
import com.kpst.demo.exception.ServiceUnavailableException;

public interface PersonService {

	public void saveOrUpdate(Person person) throws ServiceUnavailableException;
	
	public long countPerson();
	
	public List<Person> findPersonById(long id);
	
	public Person findPersonByIdSingle(long id);
	
	public List<Person> getAllPerson();
	
	public void hibernateInitialize(Person person);
	
	public void hibernateInitializePersonList(List<Person> personList);
	
	public boolean deletePerson(long id);
}
