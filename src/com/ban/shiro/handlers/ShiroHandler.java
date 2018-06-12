package com.ban.shiro.handlers;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shiro")
public class ShiroHandler {
	private static final transient Logger log = LoggerFactory.getLogger(ShiroHandler.class);

	@RequestMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		// ��ȡ��ǰ��subject
		Subject currentUser = SecurityUtils.getSubject();

		// ����ʹ��session

		// ��ȡsession
		Session session = currentUser.getSession();
		session.setAttribute("someKey", "aValue");
		String value = (String) session.getAttribute("someKey");
		if (value.equals("aValue")) {
			log.info("=================��������������������������Retrieved the correct value! [" + value + "]");
		}

		// ���Ե�ǰ�û��Ƿ��½������subject��isAuthenticated
		if (!currentUser.isAuthenticated()) {
			// ���û����������װ��token
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(true);
			try {
				// ����subject�ĵ�½����
				currentUser.login(token);
			}
			// ... catch more exceptions here (maybe custom ones specific to your
			// application?
			catch (AuthenticationException ae) {
				// unexpected condition? error?
				// ������֤ʱ�쳣�ĸ���
				System.out.println("��½ʧ�ܣ���������" + ae);
			}
		}

		return "redirect:/list.jsp";
	}

}
