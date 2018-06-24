package com.ban.shiro.services;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;


public class ShiroService {

	@RequiresRoles("admin")
	public void testService() {
		System.out.println("ShiroService...............");
	}
}
