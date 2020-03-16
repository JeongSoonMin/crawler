/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.framework.controller;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.smij.framework.constants.BaseConstants;
import kr.co.smij.framework.model.ParamMap;
import kr.co.smij.framework.util.StringUtil;

/**
 * @Package : kr.co.smij.framework.controller
 * @File : BaseController.java
 * 
 * @Title : 기본 상속 Controller
 * @date : 2017. 12. 20.
 */
public class BaseController {
	private final Logger log = LoggerFactory.getLogger(BaseController.class);

	protected Object view(ParamMap paramMap, ModelMap modelMap) {
		if (paramMap != null && paramMap.isValue(BaseConstants.URL_PARAM_KEY)) {
			String action = paramMap.getString(BaseConstants.URL_PARAM_KEY);

			if (action.indexOf(".page") > -1 || action.indexOf(".json") > -1) {

				if (action.indexOf(".page") > -1) {

					ModelAndView mav = new ModelAndView();

					StringBuffer page = new StringBuffer();
					page.append(action.substring(0, action.lastIndexOf('.')));

					String rtPage = page.toString();
					
					log.debug("page call >>> " + rtPage);
					
					mav.setViewName(rtPage);

					return mav;
				} else if (action.indexOf(".json") > -1) {
					Iterator<String> keys = modelMap.keySet().iterator();

					while (keys.hasNext()) {
						String key = (String) keys.next();

						if(modelMap.get(key) != null && "java.lang.String".equals(modelMap.get(key).getClass().getName())) {
							modelMap.put(key, StringUtil.convertNewlineToBR(String.valueOf(modelMap.get(key))));
						}
					}

					ModelAndView mav = new ModelAndView("jsonView");
					mav.addAllObjects(modelMap);
					return mav;
				}
			}
		}

		return null;
	}

	protected Object errorView(String callUrl, String viewPage, Map<String, Object> errorData, HttpStatus errorCode) {
		if (callUrl.indexOf(".json") == -1) {

			ModelAndView mav = new ModelAndView();

			Iterator<String> keys = errorData.keySet().iterator();

			while (keys.hasNext()) {
				String key = (String) keys.next();

				mav.addObject(key, errorData.get(key));
			}

			mav.setViewName(viewPage);

			return mav;
		} else if (callUrl.indexOf(".json") > -1) {
			try {
				return new ResponseEntity<String>(new ObjectMapper().writeValueAsString(errorData), errorCode);
			} catch (JsonProcessingException e) {
				log.error(e.getMessage());
			}
		}

		return null;
	}
}
