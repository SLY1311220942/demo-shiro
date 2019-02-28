package com.sly.demo.shiro.authorizing;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.sly.demo.shiro.model.User;

/**
 * 
 * @author sly
 * @time 2019年2月25日
 */
public class MyShiroRealm extends AuthorizingRealm {
	
	
	/**
	 * _授予用户权限
	 * @param principals
	 * @return
	 * @author sly
	 * @time 2019年2月25日
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取用户
		//User user = (User) SecurityUtils.getSubject().getPrincipal();

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获取用户角色
		Set<String> roleSet = new HashSet<String>();
		roleSet.add("business");
		roleSet.add("system");
		info.setRoles(roleSet);

		// 获取用户权限
		Set<String> permissionSet = new HashSet<String>();
		permissionSet.add("权限添加");
		permissionSet.add("权限删除");
		info.setStringPermissions(permissionSet);

		return info;

	}

	/**
	 * _验证用户身份
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 * @author sly
	 * @time 2019年2月25日
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());

        User user = new User();
        user.setId("test01");
        user.setUsername(username);
        user.setPassword(password);

        return new SimpleAuthenticationInfo(user, password, getName());

	}
	
}
