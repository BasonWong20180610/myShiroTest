package com.ban.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;

public class ShiroRealm extends AuthenticatingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		//1.把authenticationToken转换为UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		//2.获取用户名
		String username = upToken.getUsername();
		//3.调用数据库方法,查询username对应的用户记录
		System.out.println("从数据库中获取用户:"+username+"所对应的用户信息");
		//4.若用户不存在,则可以抛出unknownAccountException
		if("unknown".equals(username)) {
			throw new UnknownAccountException("用户不存在!");
		}
		
		//5.根据用户的情况,抛出其他异常
		if("monster".equals(username)) {
			throw new LockedAccountException("用户不存在!");
			
		}
		//6.根据用户的情况,构建AuthenticationInfo返回,通常使用的实现类是SimpleAuthenticationInfo
		
		//principals  用户的实体信息,可以是username
		Object principals = username;
		//credentials  数据库里获取的密码
		Object credentials = "123456";
		//realmName  当前realm的名称
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principals, credentials,getName());
		return info;
	}

	// @Override
	// public AuthenticationInfo getAuthenticationInfo(AuthenticationToken arg0)
	// throws AuthenticationException {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public String getName() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public boolean supports(AuthenticationToken arg0) {
	// // TODO Auto-generated method stub
	// return false;
	// }

}
