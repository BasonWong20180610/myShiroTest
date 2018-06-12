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
		// 获取当前的subject
		Subject currentUser = SecurityUtils.getSubject();

		// 测试使用session

		// 获取session
		Session session = currentUser.getSession();
		session.setAttribute("someKey", "aValue");
		String value = (String) session.getAttribute("someKey");
		if (value.equals("aValue")) {
			log.info("=================》》》》》》》》》》》》》Retrieved the correct value! [" + value + "]");
		}

		// 测试当前用户是否登陆，调用subject的isAuthenticated
		if (!currentUser.isAuthenticated()) {
			// 将用户名和密码封装成token
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(true);
			try {
				// 调用subject的登陆方法
				currentUser.login(token);
			}
			// ... catch more exceptions here (maybe custom ones specific to your
			// application?
			catch (AuthenticationException ae) {
				// unexpected condition? error?
				// 所有认证时异常的父类
				System.out.println("登陆失败！。。。。" + ae);
			}
		}

		return "redirect:/list.jsp";
	}

}
