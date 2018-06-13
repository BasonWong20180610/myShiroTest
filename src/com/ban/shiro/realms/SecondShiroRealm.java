package com.ban.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.ByteSource;

public class SecondShiroRealm extends AuthenticatingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("[SecondShiroRealm] doGetAuthenticationInfo");
		// 1.把authenticationToken转换为UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		// 2.获取用户名
		String username = upToken.getUsername();
		// 3.调用数据库方法,查询username对应的用户记录
		System.out.println("从数据库中获取用户:" + username + "所对应的用户信息");
		// 4.若用户不存在,则可以抛出unknownAccountException
		if ("unknown".equals(username)) {
			throw new UnknownAccountException("用户不存在!");
		}

		// 5.根据用户的情况,抛出其他异常
		if ("monster".equals(username)) {
			throw new LockedAccountException("用户不存在!");

		}
		// 6.根据用户的情况,构建AuthenticationInfo返回,通常使用的实现类是SimpleAuthenticationInfo

		// principals 用户的实体信息,可以是username
		Object principals = username;
		// credentials 数据库里获取的密码
		Object credentials = null;
		if ("admin".equals(username)) {
			credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
		} else if ("user".equals(username)) {
			credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718";
		}
		// realmName 当前realm的名称

		// 盐值
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		// 密码的比对通过AuthenticatingRealm的credentialsMatcher进行比对
		SimpleAuthenticationInfo info = null;
		info = new SimpleAuthenticationInfo(principals, credentials, credentialsSalt, getName());
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

	public static void main(String[] args) {
		String hashAlgorithmName = "SHA1";
		Object credentials = "123456";
		Object salt = ByteSource.Util.bytes("admin");
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

}
