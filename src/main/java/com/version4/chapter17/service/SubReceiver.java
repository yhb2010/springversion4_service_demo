package com.version4.chapter17.service;

import org.springframework.stereotype.Service;
import com.version4.chapter15.domain.RemoteUser;

/**订阅者
 * @author dell
 *
 */
@Service
public class SubReceiver {

	public void receive(RemoteUser member) {
		System.out.println(member.getUserID());
        System.out.println(member.getUserName());
    }

}
