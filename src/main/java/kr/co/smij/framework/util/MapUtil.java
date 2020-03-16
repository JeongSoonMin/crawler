/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.framework.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

/**
 * @Package : kr.co.smij.framework.util
 * @File    : MapUtil.java
 * 
 * @Title   : map 관련 util
 * @date    : 2017. 12. 18.
 */
public class MapUtil {

	/**
	 * @Purpose : white space(빈 공백 스트링) 일 경우 기본값 변경
	 *
	 * @Method Name    : getStringWt
	 * @Return         : String
	 * @essential data :
	 * @Description    :
	 */
	public static String getStringWt(Map<String, Object> paramMap, String key, String defaultValue) {
		String value = MapUtils.getString(paramMap, key);
		return StringUtil.hasText(value) ? value : defaultValue;
	}

	/**
	 * @Purpose : Map key에 값 존재여부 (true : 있음, false : 없음) 빈 공백 스트링 일 경우 false
	 *
	 * @Method Name    : isValue
	 * @Return         : boolean
	 * @essential data :
	 * @Description    :
	 */
	public static boolean isValue(Map<String, Object> paramMap, String key) {
		return StringUtil.hasText(MapUtils.getString(paramMap, key));
	}

	/**
	 * @Purpose : Map key value에 데이터 포함 여부 (포함 true)
	 *
	 * @Method Name    : isContainValue
	 * @Return         : boolean
	 * @essential data :
	 * @Description    :
	 */
	public static boolean isContainValue(Map<String, Object> paramMap, String key, String search) {
		if (isValue(paramMap, key) && MapUtils.getString(paramMap, key).indexOf(search) > -1) {
			return true;
		}

		return false;
	}

	/**
	 * @Purpose :  Map 배열 데이터 ',' 구분하여 문자열 변환 
	 *
	 * @Method Name    : stringArrayConvert
	 * @Return         : void
	 * @essential data :
	 * @Description    : String[] test = {"01", "02", "03"} == > String test = "01,02,03" 변환
	 */
	public static void stringArrayConvert(Map<String, Object> paramMap, String key) {
		stringArrayConvert(paramMap, key, null);
	}

	/**
	 * @Purpose : Map 배열 데이터 구분자로 구분하여 문자열 변환
	 *
	 * @Method Name    : stringArrayConvert
	 * @Return         : void
	 * @essential data :
	 * @Description    :
	 */
	public static void stringArrayConvert(Map<String, Object> paramMap, String key, String delimiter) {
		Object obj = MapUtils.getObject(paramMap, key);
		String dmt = ",";

		if (delimiter != null) {
			dmt = delimiter;
		}

		if (obj != null) {
			if (obj instanceof String) {
				paramMap.put(key, obj.toString());
			} else if (obj instanceof String[]) {
				String[] values = (String[]) obj;
				StringBuilder joinStr = new StringBuilder();

				for (int i = 0; i < values.length; i++) {
					joinStr.append(values[i]);

					if (i != values.length - 1) {
						joinStr.append(dmt);
					}
				}

				paramMap.put(key, joinStr.toString());
			}
		}
	}

	/**
	 * @Purpose : Map data Integer[] return
	 *
	 * @Method Name    : getIntegerArray
	 * @Return         : Integer[]
	 * @essential data :
	 * @Description    :
	 */
	public static Integer[] getIntegerArray(Map<String, Object> paramMap, String key) {
		Object obj = MapUtils.getObject(paramMap, key);
		Integer[] result = null;

		if (obj != null && StringUtil.hasText(obj.toString())) {
			if (obj instanceof Integer) {
				result = new Integer[1];
				result[0] = (Integer) obj;
			} else if (obj instanceof Integer[]) {
				result = (Integer[]) obj;
			} else if (obj instanceof String) {
				result = new Integer[1];
				result[0] = Integer.parseInt(obj.toString());
			} else if (obj instanceof String[]) {
				String[] values = (String[]) obj;

				result = new Integer[values.length];

				for (int i = 0; i < values.length; i++) {
					result[i] = Integer.parseInt(values[i]);
				}
			}
		}

		return result;
	}

	/**
	 * @Purpose : Map data String[] return
	 *
	 * @Method Name    : getStringArray
	 * @Return         : String[]
	 * @essential data :
	 * @Description    :
	 */
	public static String[] getStringArray(Map<String, Object> paramMap, String key) {
		Object obj = MapUtils.getObject(paramMap, key);
		String[] result = null;

		if (obj != null) {
			if (obj instanceof String) {
				result = new String[1];
				result[0] = (String) obj;
			} else if (obj instanceof String[]) {
				result = (String[]) obj;
			} else if (obj instanceof Integer) {
				result = new String[1];
				result[0] = obj.toString();
			} else if (obj instanceof Integer[]) {
				Integer values[] = (Integer[]) obj;

				result = new String[values.length];

				for (int i = 0; i < values.length; i++) {
					result[i] = values[i].toString();
				}
			}
		}

		return result;
	}

	/**
	 * @Purpose : Map data List<Map<String, Object>> return
	 *
	 * @Method Name    : getListMap
	 * @Return         : List<Map<String,Object>>
	 * @essential data :
	 * @Description    :
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getListMap(Map<String, Object> paramMap, String id) {
		return (List<Map<String, Object>>) paramMap.get(id);
	}

}
