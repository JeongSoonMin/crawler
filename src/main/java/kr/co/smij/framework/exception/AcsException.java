/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.framework.exception;

/**
 * @Package : kr.co.smij.framework.exception
 * @File    : AcsException.java
 * 
 * @Title   :
 * @date    : 2017. 12. 19.
 */
public class AcsException extends RuntimeException {
	private static final long serialVersionUID = -6881840921324053626L;

	public AcsException() {

	}

	public AcsException(String message) {
		super(message);
	}
}
