package com.kpst.demo.controller.resource;

import java.util.List;

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
import com.kpst.demo.domain.Person;

@Controller
@CrossOrigin
@RequestMapping("/person")
public interface PersonServiceResource extends AbstractServiceResource{

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> savePerson (
			@RequestHeader(value = "userId") String userId, 
			@RequestBody Person person) throws Exception;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public @ResponseBody Person getPerson(
			@RequestHeader(value = "userId") String userId, 
			@PathVariable(value = "id") String id) throws Exception;
	
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public String getAllPerson (
			@RequestHeader(value = "userId") String userId,Model model) throws Exception;
	
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> deletePerson (
			@RequestHeader(value = "userId") String userId, 
			@PathVariable(value = "id") String id) throws Exception;
	
	@RequestMapping(method = RequestMethod.GET,value="/form")
	public String getForm();
}
