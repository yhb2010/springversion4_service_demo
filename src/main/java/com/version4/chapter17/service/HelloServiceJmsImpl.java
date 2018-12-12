package com.version4.chapter17.service;

import org.springframework.stereotype.Service;
import com.version4.chapter15.domain.RemoteUser;

@Service
public class HelloServiceJmsImpl implements HelloService {

	@Override
	public String sayHello(String name) {
		return null;
	}

	@Override
	public RemoteUser sayHello2(String name) {
		System.out.println("jms service Hello " + name);
		return new RemoteUser(100, name);
	}

}
