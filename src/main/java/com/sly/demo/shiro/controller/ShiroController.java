package com.sly.demo.shiro.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sly.demo.shiro.common.BaseResult;

/**
 * 
 * @author sly
 * @time 2019年2月28日
 */
@Controller
@RequestMapping("/shiro")
public class ShiroController {
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;
	
	/**
	 * _获取shiro当前拦截器
	 * @param request
	 * @param response
	 * @return
	 * @author sly
	 * @time 2019年2月28日
	 */
	@ResponseBody
	@RequestMapping("/getShiroFilterChainDefinitionMap")
	public Object getShiroFilterChainDefinitionMap(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
		return filterChainDefinitionMap;
	}
	
	/**
	 * _重置shiro拦截器map
	 * @return
	 * @author sly
	 * @time 2019年2月28日
	 */
	@ResponseBody
	@RequestMapping("/resetShiroFilterChainDefinitionMap")
	public Object resetShiroFilterChainDefinitionMap() {
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/**/*.js", "anon");
		filterChainDefinitionMap.put("/**/*.css", "anon");
		filterChainDefinitionMap.put("/login/login", "anon");
		
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		
		//这里是系统功能的权限,可以通过数据库获取
		filterChainDefinitionMap.put("/business/**", "roles[business]");
		filterChainDefinitionMap.put("/system/**", "roles[system,business]");
		filterChainDefinitionMap.put("/shiro/**", "roles[system,business]");
		
		// 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 :这是一个坑呢，一不小心代码就不好使了;
		// authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
		filterChainDefinitionMap.put("/**", "authc");
		
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login/toLogin");
		
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/login/toHome");

		// 未授权界面;(springboot里设了没效果只能用异常处理器处理一下AuthorizationException异常)
		shiroFilterFactoryBean.setUnauthorizedUrl("/login/toUnpermission");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return new BaseResult(200, "重置成功!");
	}
}

