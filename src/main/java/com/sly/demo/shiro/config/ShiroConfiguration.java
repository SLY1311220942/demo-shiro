package com.sly.demo.shiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import com.sly.demo.shiro.authorizing.MyShiroRealm;
import com.sly.demo.shiro.filter.CustomRolesAuthorizationFilter;

/**
 * Shiro配置类型
 * 
 * @author sly
 * @time 2019年2月25日
 */
@Configuration
public class ShiroConfiguration {
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 自定义拦截器
		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
		filters.put("roles", new CustomRolesAuthorizationFilter());
		shiroFilterFactoryBean.setFilters(filters);

		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/**/*.js", "anon");
		filterChainDefinitionMap.put("/**/*.css", "anon");
		filterChainDefinitionMap.put("/login/login", "anon");

		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");

		// 这里是系统功能的权限,可以通过数据库获取
		filterChainDefinitionMap.put("/business/**", "roles[business]");
		filterChainDefinitionMap.put("/system/**", "roles[system,business]");
		filterChainDefinitionMap.put("/shiro/**", "roles[system]");

		// 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 :这是一个坑呢，一不小心代码就不好使了;
		// authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
		filterChainDefinitionMap.put("/**", "authc");

		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login/toLogin");

		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/login/toHome");

		// 未授权界面
		shiroFilterFactoryBean.setUnauthorizedUrl("/login/toUnpermission");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 
	 * @return
	 * @author sly
	 * @time 2019年2月25日
	 */
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		return myShiroRealm;
	}

	/**
	 * 
	 * @return
	 * @author sly
	 * @time 2019年2月25日
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		return securityManager;
	}

	/**
	 * _加入注解的使用，不加入这个注解不生效
	 * 
	 * @param securityManager
	 * @return
	 * @author sly
	 * @time 2019年2月25日
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
		app.setProxyTargetClass(true);
		return app;
	}

}
