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
		// 1.��authenticationTokenת��ΪUsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		// 2.��ȡ�û���
		String username = upToken.getUsername();
		// 3.�������ݿⷽ��,��ѯusername��Ӧ���û���¼
		System.out.println("�����ݿ��л�ȡ�û�:" + username + "����Ӧ���û���Ϣ");
		// 4.���û�������,������׳�unknownAccountException
		if ("unknown".equals(username)) {
			throw new UnknownAccountException("�û�������!");
		}

		// 5.�����û������,�׳������쳣
		if ("monster".equals(username)) {
			throw new LockedAccountException("�û�������!");

		}
		// 6.�����û������,����AuthenticationInfo����,ͨ��ʹ�õ�ʵ������SimpleAuthenticationInfo

		// principals �û���ʵ����Ϣ,������username
		Object principals = username;
		// credentials ���ݿ����ȡ������
		Object credentials = null;
		if("admin".equals(username)) {
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
		}else if("user".equals(username)) {
			credentials = "098d2c478e9c11555ce2823231e02ec1";
		}
		// realmName ��ǰrealm������

		//��ֵ
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		// ����ıȶ�ͨ��AuthenticatingRealm��credentialsMatcher���бȶ�
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

	//��Ȩ����
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1.��principals�л�ȡ��½�û�����Ϣ  ��˳��
		Object principal = principals.getPrimaryPrincipal();
		//2.���õ�½�û�����Ϣ���ж��û���ǰ�Ľ�ɫ��������Ҫ��ѯ���ݿ⣩
		Set<String> roles = new HashSet<>();
		roles.add("user");
		if("admin".equals(principal)) {
			roles.add("admin");
		}
		//3.����SimpleAuthenticationInfo��������reals����
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles) ;
		//4.����
		return info;
	}

}
