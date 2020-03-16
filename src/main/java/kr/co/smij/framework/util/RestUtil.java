/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.framework.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import kr.co.smij.framework.exception.BaseException;

/**
 * @Package : kr.co.smij.framework.util
 * @File : RestUtil.java
 * 
 * @Title :
 * @date : 2016. 5. 31.
 */
public class RestUtil {
	private final static Logger log = LoggerFactory.getLogger(RestUtil.class);

	/**
	 * @Purpose : Rest 호출하여 이미지 다운로드
	 *
	 * @Method Name : callRestImg
	 * @Author : 정순민
	 * @Date : 2018. 6. 20.
	 * @Return : Map<String,Object>
	 * @essentialData :
	 * @Description :
	 */
	public static Map<String, Object> callRestImg(String url) throws BaseException {
		if (!StringUtils.hasText(url)) {
			throw new BaseException();
		}

		Map<String, Object> rtMap = new HashMap<String, Object>();

		boolean result = true;
		long staTime = 0;
		long endTime = 0;

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(30000);
		factory.setConnectTimeout(30000);
		HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(100).setMaxConnPerRoute(5).build();
		factory.setHttpClient(httpClient);
		RestTemplate tmpl = new RestTemplate(factory);
		HttpStatus sts = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<byte[]> response = null;

		try {
			staTime = System.currentTimeMillis();
			
			response = tmpl.exchange(url, HttpMethod.GET, entity, byte[].class);

			if (response != null) {
				sts = response.getStatusCode();

				if (sts.is2xxSuccessful()) {
					rtMap.put("data", response.getBody());
				} else {
					result = false;
				}
			} else {
				result = false;
			}

			rtMap.put("stsCd", sts.toString());

		} catch (Exception e) {
			log.error(e.getMessage());
			result = false;
			rtMap.put("stsCd", "404");
		} finally {
			endTime = System.currentTimeMillis();
		}

		rtMap.put("result", result); // 결과
		rtMap.put("chkTime", endTime - staTime); // 응답 시간

		return rtMap;
	}

	/**
	 * @Purpose : Rest 호출하여 Body 정보 가져오기
	 *
	 * @Method Name : callRestHtml
	 * @Author : 정순민
	 * @Date : 2018. 6. 20.
	 * @Return : Map<String,Object>
	 * @essentialData :
	 * @Description :
	 */
	public static Map<String, Object> callRestHtml(String url) throws BaseException {
		if (!StringUtils.hasText(url)) {
			throw new BaseException();
		}

		Map<String, Object> rtMap = new HashMap<String, Object>();
		boolean result = true;
		long staTime = 0;
		long endTime = 0;

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(30000);
		factory.setConnectTimeout(30000);
		HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(100).setMaxConnPerRoute(5).build();
		factory.setHttpClient(httpClient);
		RestTemplate tmpl = new RestTemplate(factory);
		HttpStatus sts = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(null, headers);

		ResponseEntity<String> response = null;

		try {
			staTime = System.currentTimeMillis();

			response = tmpl.exchange(url, HttpMethod.GET, entity, String.class);

			if (response != null) {
				sts = response.getStatusCode();

				if (sts.is2xxSuccessful()) {
					rtMap.put("data", response.getBody());
				} else {
					result = false;
				}
			} else {
				result = false;
			}

			rtMap.put("stsCd", sts.toString());

		} catch (Exception e) {
			log.error(e.getMessage());
			result = false;
			rtMap.put("stsCd", "404");
		} finally {
			endTime = System.currentTimeMillis();
		}

		rtMap.put("result", result);
		rtMap.put("chkTime", endTime - staTime);

		return rtMap;
	}

	public static Map<String, Object> callRest(String url) throws BaseException {
		if (url.endsWith("png") || url.endsWith("jpg")) {
			return callRestImg(url);
		} else {
			return callRestHtml(url);
		}
	}
}
