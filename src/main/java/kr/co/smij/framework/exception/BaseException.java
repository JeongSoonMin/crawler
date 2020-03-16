/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.framework.exception;

/**
 * @Package : kr.co.smij.framework.exception
 * @File    : BaseException.java
 * 
 * @Title   :
 * @date    : 2018. 1. 2.
 */
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 8165688194985472600L;

	public BaseException() {

	}

	public BaseException(String message) {
		super(message);
	}
}
