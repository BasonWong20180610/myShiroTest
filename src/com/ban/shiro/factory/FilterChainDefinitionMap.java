package com.ban.shiro.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMap {
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
//		/shiro/login = anon 
//				/shiro/logout = logout 
//				/user.jsp = roles[user] 
//				/admin.jsp = roles[admin]
		linkedHashMap.put("/login.jsp", "anon");
		linkedHashMap.put("/shiro/login", "anon");
		linkedHashMap.put("/shiro/logout", "logout");
		linkedHashMap.put("/user.jsp", "roles[user]");
		linkedHashMap.put("/admin.jsp", "roles[admin]");
		linkedHashMap.put("/**", "authc");
		return linkedHashMap;
	}

}
