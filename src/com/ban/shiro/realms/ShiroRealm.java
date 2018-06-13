package com.ban.shiro.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class ShiroRealm extends AuthorizingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("[FirstShiroRealm] doGetAuthenticationInfo");
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
		if("admin".equals(username)) {
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
		}else if("user".equals(username)) {
			credentials = "098d2c478e9c11555ce2823231e02ec1";
		}
		// realmName 当前realm的名称

		//盐值
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
		String hashAlgorithmName = "MD5";
		Object credentials = "123456";
		Object salt = ByteSource.Util.bytes("user");
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

	//授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1.从principals中获取登陆用户的信息  有顺序
		Object principal = principals.getPrimaryPrincipal();
		//2.利用登陆用户的信息来判断用户当前的角色（可能需要查询数据库）
		Set<String> roles = new HashSet<>();
		roles.add("user");
		if("admin".equals(principal)) {
			roles.add("admin");
		}
		//3.创建SimpleAuthenticationInfo，并设置reals属性
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles) ;
		//4.返回
		return info;
	}

}
