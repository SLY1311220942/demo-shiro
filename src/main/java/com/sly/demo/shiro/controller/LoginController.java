package com.sly.demo.shiro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sly.demo.shiro.common.BaseResult;
import com.sly.demo.shiro.model.User;

/**
 * login controller class
 * @author sly
 * @time 2019年2月25日
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	/**
	 * _去登录页面
	 * @param request
	 * @param response
	 * @return
	 * @author sly
	 * @time 2019年2月25日
	 */
	@RequestMapping("/toLogin")
	public String toLogin(HttpServletRequest request,HttpServletResponse response) {
		return "login.html";
	}
	
	/**
	 * _用户登录
	 * @param request
	 * @param response
	 * @param username
	 * @param password
	 * @param rememberMe
	 * @return
	 * @author sly
	 * @time 2019年2月25日
	 */
	@ResponseBody
	@RequestMapping("/login")
	public BaseResult login(HttpServletRequest request,HttpServletResponse response, String username,String password) {
		boolean rememberMe = false;
		UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
		SecurityUtils.getSubject().login(token);
		
		
		return new BaseResult(200, "登录成功!");
	}
	
	/**
	 * _去首页
	 * @return
	 * @author sly
	 * @time 2019年2月25日
	 */
	@RequestMapping("/toHome")
	public String toHome(HttpServletRequest request,HttpServletResponse response) {
		User user = (User)SecurityUtils.getSubject().getPrincipal();
		request.setAttribute("user", user);
        return "home.html";
	}
	
	/**
	 * 无权限页面
	 * @return
	 * @author sly
	 * @time 2019年2月25日
	 */
	@RequestMapping("/toUnpermission")
	public String unPermission() {
		return "unpermission.html";
	}
}
