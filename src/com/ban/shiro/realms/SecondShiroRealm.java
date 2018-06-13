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
		if ("admin".equals(username)) {
			credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
		} else if ("user".equals(username)) {
			credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718";
		}
		// realmName ��ǰrealm������

		// ��ֵ
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
		String hashAlgorithmName = "SHA1";
		Object credentials = "123456";
		Object salt = ByteSource.Util.bytes("admin");
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

}
