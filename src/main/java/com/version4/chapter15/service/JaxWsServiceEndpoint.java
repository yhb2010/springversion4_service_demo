package com.version4.chapter15.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.version4.chapter15.domain.RemoteUser;


@Component
@WebService(serviceName="JaxWsService")
public class JaxWsServiceEndpoint {

	@Autowired
	private JaxWsUserService jaxWsUserService;

	@WebMethod
	public RemoteUser getUser(int userID) {
		return jaxWsUserService.getUser(userID);
	}

	@WebMethod
	public List<RemoteUser> getAllUser() {
		return jaxWsUserService.getAllUser();
	}

}
