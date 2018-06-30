package com.ban.shiro.services;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;


public class ShiroService {

	@RequiresRoles("admin")
	public void testService() {
		System.out.println("ShiroService...............");
		Session session = SecurityUtils.getSubject().getSession();
		Object value = session.getAttribute("key");
		System.out.println(value);
	}
}
