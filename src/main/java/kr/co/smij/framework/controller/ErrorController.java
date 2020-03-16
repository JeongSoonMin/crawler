/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.framework.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import kr.co.smij.framework.exception.AcsException;
import kr.co.smij.framework.exception.BaseException;

/**
 * @Package : kr.co.smij.framework.controller
 * @File : ErrorController.java
 * 
 * @Title : 기본 에러 Controller
 * @date : 2017. 12. 18.
 */
@ControllerAdvice
public class ErrorController extends BaseController {
	private final Logger log = LoggerFactory.getLogger(ErrorController.class);

	/**
	 * @Purpose : 미지정 에러 핸들러
	 *
	 * @Method Name : defaultErrorHandler
	 * @Author : 정순민
	 * @Date : 2018. 1. 2.
	 * @Return : ModelAndView
	 * @essentialData :
	 * @Description :
	 */
	@ExceptionHandler(value = Exception.class)
	public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		// 에러 출력
		log.error(e.getMessage());

		String url = req.getRequestURI();

		Map<String, Object> error = new HashMap<String, Object>();
		error.put("exception", e.getMessage());
		error.put("url", url);

		return errorView(url, "/error/error500", error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * @Purpose : 공통 기본 에러 핸들러
	 *
	 * @Method Name : errorBaseHandler
	 * @Author : 정순민
	 * @Date : 2018. 1. 2.
	 * @Return : ModelAndView
	 * @essentialData :
	 * @Description :
	 */
	@ExceptionHandler(value = BaseException.class)
	public Object errorBaseHandler(HttpServletRequest req, BaseException e) throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		// 에러 출력
		log.error(e.getMessage());

		String url = req.getRequestURI();

		Map<String, Object> error = new HashMap<String, Object>();
		error.put("exception", e.getMessage());
		error.put("url", url);

		return errorView(url, "/error/error500", error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * @Purpose : 미지정 URL 호출 에러 핸들러
	 *
	 * @Method Name : error404Handler
	 * @Author : 정순민
	 * @Date : 2018. 1. 2.
	 * @Return : ModelAndView
	 * @essentialData :
	 * @Description :
	 */
	@ExceptionHandler(value = NoHandlerFoundException.class)
	public Object error404Handler(HttpServletRequest req, NoHandlerFoundException e) throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		// 에러 출력
		log.error(e.getMessage());

		String url = req.getRequestURI();

		Map<String, Object> error = new HashMap<String, Object>();
		error.put("exception", e.getMessage());
		error.put("url", url);

		return errorView(url, "/error/error404", error, HttpStatus.NOT_FOUND);
	}

	/**
	 * @Purpose : 잘못된 URL 호출 에러 핸들러
	 *
	 * @Method Name : errorAcsHandler
	 * @Author : 정순민
	 * @Date : 2018. 1. 2.
	 * @Return : ModelAndView
	 * @essentialData :
	 * @Description :
	 */
	@ExceptionHandler(value = AcsException.class)
	public Object errorAcsHandler(HttpServletRequest req, AcsException e) throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		// 에러 출력
		log.error(e.getMessage());

		String url = req.getRequestURI();

		Map<String, Object> error = new HashMap<String, Object>();
		error.put("exception", e.getMessage());
		error.put("url", url);

		return errorView(url, "/error/error404", error, HttpStatus.NOT_FOUND);
	}
	

}
