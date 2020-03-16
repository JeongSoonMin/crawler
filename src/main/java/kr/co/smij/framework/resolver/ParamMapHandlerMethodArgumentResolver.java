/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.framework.resolver;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.smij.framework.constants.BaseConstants;
import kr.co.smij.framework.model.ParamMap;
import kr.co.smij.framework.util.StringUtil;

/**
 * @Package : kr.co.smij.framework.resolver
 * @File    : ParamMapHandlerMethodArgumentResolver.java
 * 
 * @Title   :
 * @date    : 2018. 1. 2.
 */
public class ParamMapHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	private final Logger log = LoggerFactory.getLogger(ParamMapHandlerMethodArgumentResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().equals(ParamMap.class);
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
			NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

		HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();

		Map<String, Object> reqMap = new HashMap<String, Object>();
		Enumeration<?> enumeration = request.getParameterNames();
		boolean typeChange = false;

		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();

			// 숫자로만 넘어오는 파라미터 제외(파일 업로드 일 경우 파일에 관한 parameter가 넘어오는 경우)
			if (!StringUtil.isNumber(key)) {
				typeChange = false;

				String[] values = request.getParameterValues(key);

				if (values != null) {
					if (values.length == 1) {
						/*
						 * DB에 컬럼 타입이 number형 이면서 pk일 때 int형으로 형변환 시켜준다. DB index 태우기 위해서(Map 사용 시 개발자들이
						 * 많이 놓치는 부분...)
						 */
						for (String colType : BaseConstants.DB_COL_TYPE_NUM_PK) {
							if (key.toLowerCase().endsWith(colType)) {
								typeChange = true;

								break;
							}
						}

						if (typeChange) {
							try {
								reqMap.put(key, Integer.parseInt(values[0]));
							} catch (NumberFormatException nfe) {
								// error 발생 시 넘어오는 값 그대로 넘겨준다.
								if (StringUtil.hasText(values[0])) {
									reqMap.put(key, values[0]);
								} else {
									reqMap.put(key, null);
								}

								log.warn(
										"request parameter value casting error >>> " + key + "<<< " + nfe.getMessage());
							}
						} else {
							reqMap.put(key, values[0]);
						}
					} else {
						reqMap.put(key, (values.length > 1) ? values : values[0]);
					}

				} else {
					reqMap.put(key, "");
				}
			}
		}

		String clientIP = request.getHeader("X-Forwarded-For");

		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getHeader("Proxy-Client-IP");
		}

		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getHeader("WL-Proxy-Client-IP");
		}

		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getHeader("HTTP_CLIENT_IP");
		}

		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getRemoteAddr();
		}

		reqMap.put("reg_ip", clientIP);
		reqMap.put("mod_ip", clientIP);
		reqMap.put(BaseConstants.URL_PARAM_KEY, request.getRequestURI());

		/*
		 * 파일 upload 진행(첨부된 파일이 한개라도 있을 경우 진행)
		 */
		if (request.getContentType() != null
				&& request.getContentType().toLowerCase().indexOf("multipart/form-data") > -1) {

			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
			List<MultipartFile> fileList = new ArrayList<MultipartFile>();

			Iterator<String> iter = mreq.getFileNames();
			while (iter.hasNext()) {
				fileList.addAll(mreq.getFiles(iter.next()));
			}

			if (fileList.size() > 0) {
				List<MultipartFile> inList = new ArrayList<MultipartFile>();

				for (MultipartFile file : fileList) {
					if (StringUtil.hasText(file.getOriginalFilename()) && file.getSize() > 0) {
						inList.add(file);
					}
				}

				if (inList.size() > 0) {
					reqMap.put(BaseConstants.UPLOAD_PARAM_KEY, fileList);
				}
			}
		}

		log.debug("join url >>> " + MapUtils.getString(reqMap, BaseConstants.URL_PARAM_KEY));
		log.debug("Parameters >>> " + reqMap);

		return new ParamMap(reqMap);
	}

}
