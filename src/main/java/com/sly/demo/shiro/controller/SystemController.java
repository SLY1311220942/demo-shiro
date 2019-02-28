package com.sly.demo.shiro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sly.demo.shiro.model.User;

/**
 * 
 * @author sly
 * @time 2019年2月28日
 */
@Controller
@RequestMapping("/system")
public class SystemController {
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author sly
	 * @time 2019年2月28日
	 */
	//@RequiresRoles(value= {"system"},logical=Logical.OR)
	@RequestMapping("/system1")
	public String system_01(HttpServletRequest request,HttpServletResponse response) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		request.setAttribute("user", user);
		return "system1.html";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author sly
	 * @time 2019年2月28日
	 */
	//@RequiresRoles(value= {"system"},logical=Logical.OR)
	@RequestMapping("/system2")
	public String system_02(HttpServletRequest request,HttpServletResponse response) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		request.setAttribute("user", user);
		return "system2.html";
	}


}

