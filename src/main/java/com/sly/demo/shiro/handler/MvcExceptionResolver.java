package com.sly.demo.shiro.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.sly.demo.shiro.common.BaseResult;

/**
 * _自定义异常解析
 * 
 * @author sly
 * @time 2019年2月28日
 */
@Component
public class MvcExceptionResolver implements HandlerExceptionResolver {
	private static Logger logger = LoggerFactory.getLogger(MvcExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String header = request.getHeader("X-Requested-With");
			boolean isAjax = "XMLHttpRequest".equals(header) ? true : false;

			if (ex instanceof AuthorizationException) {
				//shiro没有权限的一些,直接跳转至无权限页面
				if (isAjax) {
					//返回消息
					response.getWriter().write(JSON.toJSONString(new BaseResult(500, "没有权限!")));
					return new ModelAndView();
				} else {
					// 否则， 输出错误信息到自定义的无权限页面
					ModelAndView modelAndView = new ModelAndView("unpermission.html");
					return modelAndView;
				}
			} else {
				// 为非自定义异常
				logger.error(ExceptionUtils.getStackTrace(ex));
				if (isAjax) {
					//返回消息
					response.getWriter().write(JSON.toJSONString(new BaseResult(500, "系统错误!")));
					return new ModelAndView();
				}else {
					// 否则， 输出错误信息到自定义的无权限页面
					ModelAndView modelAndView = new ModelAndView("error.html");
					return modelAndView;
				}
			}
			
		} catch (IOException e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return new ModelAndView();
	}
}
