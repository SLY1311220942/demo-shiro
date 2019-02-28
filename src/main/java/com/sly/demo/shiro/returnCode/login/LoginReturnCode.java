package com.sly.demo.shiro.returnCode.login;

import com.sly.demo.shiro.returnCode.IReturnCode;

/**
 * login返回状态码00001000-00001999
 * @author sly
 * @time 2019年2月25日
 */
public enum LoginReturnCode implements IReturnCode {
	/**
	 * 登录失败
	 */
	LOGIN_FAILE(10001000,"登录失败!"),
	/**
	 * 登录成功
	 */
	LOGIN_SUCCESS(10001001,"登录成功!"),
	;
	
	/**
	 * 错误code
	 */
	private int code;
	/**
	 * 错误信息
	 */
	private String msg;

	/**
	 * 枚举构造方法
	 * 
	 * @param code
	 * @param msg
	 */
	private LoginReturnCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 获取错误code
	 * 
	 * @return
	 * @author sly
	 * @time 2018年11月14日
	 */
	@Override
	public int getCode() {
		return this.code;
	}

	/**
	 * 获取错误信息
	 * 
	 * @return
	 * @author sly
	 * @time 2018年11月14日
	 */
	@Override
	public String getMsg() {
		return this.msg;
	}

	/**
	 * 获取枚举名称
	 * 
	 * @return
	 * @author sly
	 * @time 2018年11月14日
	 */
	@Override
	public String getName() {
		return this.name();
	}
}
