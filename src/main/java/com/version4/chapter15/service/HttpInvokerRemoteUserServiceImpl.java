package com.version4.chapter15.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import com.version4.chapter15.domain.RemoteUser;

@Service
public class HttpInvokerRemoteUserServiceImpl implements HttpInvokerRemoteUserService {

	private List<RemoteUser> userList = new ArrayList<RemoteUser>();

	public HttpInvokerRemoteUserServiceImpl(){
		IntStream.range(1, 10).forEach(i -> userList.add(new RemoteUser(i, "user" + i)));
	}

	@Override
	public RemoteUser getUser(int userID) {
		System.out.println("httpInvoker" + userID);
		return userList.stream().filter(user -> user.getUserID() == userID).findFirst().orElse(null);
	}

	@Override
	public List<RemoteUser> getAllUser() {
		System.out.println("httpInvoker");
		return userList;
	}

}
