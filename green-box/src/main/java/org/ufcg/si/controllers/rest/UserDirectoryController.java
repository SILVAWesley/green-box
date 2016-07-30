package org.ufcg.si.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ufcg.si.util.ServerConstants;

@RestController
@RequestMapping(ServerConstants.ACCESS_PATH + ServerConstants.USERDIRECTORY_PATH)
public class UserDirectoryController {
	
	@RequestMapping(value = "/test", 
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> testMethod() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
