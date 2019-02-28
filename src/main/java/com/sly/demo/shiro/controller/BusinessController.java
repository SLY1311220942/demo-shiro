package com.sly.demo.shiro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sly.demo.shiro.model.User;

/**
 * business controller class
 * @author sly
 * @time 2019年2月25日
 */
@Controller
@RequestMapping("/business")
public class BusinessController {
	/**
	 * 
	 * @return
	 * @author sly
	 * @time 2019年2月27日
	 */
	//@RequiresRoles(value= {"business"},logical=Logical.OR)
	@RequestMapping("/business1")
	public String business_01(HttpServletRequest request,HttpServletResponse response) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		request.setAttribute("user", user);
		return "business1.html";
	}
	
	/**
	 * 
	 * @return
	 * @author sly
	 * @time 2019年2月27日
	 */
	//@RequiresRoles(value= {"business"},logical=Logical.OR)
	@RequestMapping("/business2")
	public String business_02(HttpServletRequest request,HttpServletResponse response) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		request.setAttribute("user", user);
		return "business2.html";
	}
}
