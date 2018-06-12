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

		//1.��authenticationTokenת��ΪUsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		//2.��ȡ�û���
		String username = upToken.getUsername();
		//3.�������ݿⷽ��,��ѯusername��Ӧ���û���¼
		System.out.println("�����ݿ��л�ȡ�û�:"+username+"����Ӧ���û���Ϣ");
		//4.���û�������,������׳�unknownAccountException
		if("unknown".equals(username)) {
			throw new UnknownAccountException("�û�������!");
		}
		
		//5.�����û������,�׳������쳣
		if("monster".equals(username)) {
			throw new LockedAccountException("�û�������!");
			
		}
		//6.�����û������,����AuthenticationInfo����,ͨ��ʹ�õ�ʵ������SimpleAuthenticationInfo
		
		//principals  �û���ʵ����Ϣ,������username
		Object principals = username;
		//credentials  ���ݿ����ȡ������
		Object credentials = "123456";
		//realmName  ��ǰrealm������
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
