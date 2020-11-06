package com.kpst.demo.controller.resource.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kpst.demo.controller.resource.PersonServiceResource;
import com.kpst.demo.domain.Person;
import com.kpst.demo.service.PersonService;

@Controller
@CrossOrigin
@RequestMapping("/person")
public class PersonServiceResourceImpl extends AbstractServiceResourceImpl implements PersonServiceResource {
	
	@Autowired
	PersonService personService;

	@Override
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> savePerson (
			@RequestHeader(value = "userId") String userId, 
			@RequestBody Person person) throws Exception {
		if(person == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		personService.saveOrUpdate(person);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody Person getPerson(
			@RequestHeader(value = "userId") String userId, 
			@PathVariable(value = "id") String id) throws Exception {
		int personId = Integer.parseInt(id);
		if(personId <= 0)
			return null;
		Person person = personService.findPersonByIdSingle(personId);
		if(person == null)
			return null;
		return person;
	}

	@Override
	@RequestMapping(method = RequestMethod.GET, value = "")
	public String getAllPerson (
			@RequestHeader(value = "userId") String userId,Model model) throws Exception {
		List<Person> personList = personService.getAllPerson();
		model.addAttribute("personList",personList);
		return "personList";
	}

	@Override
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> deletePerson (
			@RequestHeader(value = "userId") String userId, 
			@PathVariable(value = "id") String id) throws Exception {
		int personId = Integer.parseInt(id);
		if(personId <= 0)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		boolean flag=personService.deletePerson(personId);
		return new ResponseEntity<>(flag? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}

	@Override
	@RequestMapping(method = RequestMethod.GET,value="/form")
	public String getForm() {
		return "newPersonForm";
	}
	
	

}
