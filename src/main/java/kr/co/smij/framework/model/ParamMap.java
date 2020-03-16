/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.framework.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import kr.co.smij.framework.util.MapUtil;
import kr.co.smij.framework.util.StringUtil;

/**
 * @Package : kr.co.smij.framework.model
 * @File    : ParamMap.java
 * 
 * @Title   :
 * @date    : 2018. 1. 2.
 */
public class ParamMap {

	private Map<String, Object> map = null;

	public ParamMap() {
		this.map = new HashMap<String, Object>();
	}

	public ParamMap(Map<String, Object> map) {
		this.map = map;
	}

	/**
	 * @Purpose : HashMap return
	 *
	 * @Method Name    : getMap
	 * @Return         : HashMap<String,Object>
	 * @essential data :
	 * @Description    :
	 */
	public Map<String, Object> getMap() {
		return map;
	}

	/**
	 * @Purpose : HashMap set
	 *
	 * @Method Name    : setMap
	 * @Return         : void
	 * @essential data :
	 * @Description    :
	 */
	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}

	/**
	 * @Purpose : HashMap key의 value Obejct 형태 return
	 *
	 * @Method Name    : get
	 * @Return         : Object
	 * @essential data :
	 * @Description    :
	 */
	public Object get(String key) {
		return map.get(key);
	}

	/**
	 * @Purpose : HashMap key의 value Object 타입 put
	 *
	 * @Method Name    : put
	 * @Return         : void
	 * @essential data :
	 * @Description    :
	 */
	public void put(String key, Object value) {
		this.map.put(key, value);
	}

	/**
	 * @Purpose : HashMap key의 value put all
	 *
	 * @Method Name    : putAll
	 * @Return         : void
	 * @essential data :
	 * @Description    :
	 */
	public void putAll(Map<String, Object> map) {
		this.map.putAll(map);
	}

	/**
	 * @Purpose : HashMap 초기화
	 *
	 * @Method Name    : clear
	 * @Return         : void
	 * @essential data :
	 * @Description    :
	 */
	public void clear() {
		this.map.clear();
	}

	/**
	 * @Purpose : HashMap key 삭제
	 *
	 * @Method Name    : remove
	 * @Return         : void
	 * @essential data :
	 * @Description    :
	 */
	public void remove(String key) {
		this.map.remove(key);
	}

	/**
	 * @Purpose : HashMap key의 value String 타입 put
	 *
	 * @Method Name    : setString
	 * @Return         : void
	 * @essential data :
	 * @Description    :
	 */
	public void setString(String key, Object value) {
		String putValue = null;

		if (value != null) {
			putValue = value.toString();
		}

		this.map.put(key, putValue);
	}

	/**
	 * @Purpose : HashMap key의 value String 타입 return
	 *
	 * @Method Name    : getString
	 * @Return         : String
	 * @essential data :
	 * @Description    :
	 */
	public String getString(String key) {
		return MapUtils.getString(this.map, key);
	}

	/**
	 * @Purpose : HashMap key의 value 값 null 이거나 빈스트링 인 경우 대체문자열로 return
	 *
	 * @Method Name    : getString
	 * @Return         : String
	 * @essential data :
	 * @Description    :
	 */
	public String getString(String key, String defaultValue) {
		String returnValue = getString(key);
		return StringUtil.hasText(returnValue) ? returnValue : defaultValue;
	}

	/**
	 * @Purpose : HashMap key의 value int 타입 return
	 *
	 * @Method Name    : getInt
	 * @Return         : int
	 * @essential data :
	 * @Description    :
	 */
	public int getInt(String key) {
		return MapUtils.getIntValue(map, key);
	}

	/**
	 * @Purpose : HashMap key의 value 값 null 이거나 빈스트링 인 경우 대체값으로 return
	 *
	 * @Method Name    : getInt
	 * @Return         : int
	 * @essential data :
	 * @Description    :
	 */
	public int getInt(String key, int defaultValue) {
		return MapUtils.getIntValue(map, key, defaultValue);
	}

	/**
	 * @Purpose : HashMap key의 value Integer 타입 return
	 *
	 * @Method Name    : getInteger
	 * @Return         : Integer
	 * @essential data :
	 * @Description    :
	 */
	public Integer getInteger(String key) {
		return MapUtils.getInteger(map, key);
	}

	/**
	 * @Purpose : HashMap key의 value 값 null 이거나 빈스트링 인 경우 대체값으로 return
	 *
	 * @Method Name    : getInteger
	 * @Return         : Integer
	 * @essential data :
	 * @Description    :
	 */
	public Integer getInteger(String key, Integer defaultValue) {
		return MapUtils.getInteger(map, key, defaultValue);
	}

	/**
	 * @Purpose : HashMap key의 value long 타입 return
	 *
	 * @Method Name    : getLongValue
	 * @Return         : long
	 * @essential data :
	 * @Description    :
	 */
	public long getLongValue(String key) {
		return MapUtils.getLongValue(map, key);
	}

	/**
	 * @Purpose : HashMap key의 value 값 null 이거나 빈스트링 인 경우 대체값으로 return
	 *
	 * @Method Name    : getLongValue
	 * @Return         : long
	 * @essential data :
	 * @Description    :
	 */
	public long getLongValue(String key, long defaultValue) {
		return MapUtils.getLongValue(map, key, defaultValue);
	}

	/**
	 * @Purpose : HashMap key의 value Long 타입 return
	 *
	 * @Method Name    : getLong
	 * @Return         : Long
	 * @essential data :
	 * @Description    :
	 */
	public Long getLong(String key) {
		return MapUtils.getLong(map, key);
	}

	/**
	 * @Purpose : HashMap key의 value 값 null 이거나 빈스트링 인 경우 대체값으로 return
	 *
	 * @Method Name    : getLong
	 * @Return         : Long
	 * @essential data :
	 * @Description    :
	 */
	public Long getLong(String key, Long defaultValue) {
		return MapUtils.getLong(map, key, defaultValue);
	}

	/**
	 * @Purpose : HashMap key의 value float 타입 return
	 *
	 * @Method Name    : getFloatValue
	 * @Return         : float
	 * @essential data :
	 * @Description    :
	 */
	public float getFloatValue(String key) {
		return MapUtils.getFloatValue(map, key);
	}

	/**
	 * @Purpose : HashMap key의 value 값 null 이거나 빈스트링 인 경우 대체값으로 return
	 *
	 * @Method Name    : getFloatValue
	 * @Return         : float
	 * @essential data :
	 * @Description    :
	 */
	public float getFloatValue(String key, float defalutValue) {
		return MapUtils.getFloatValue(map, key, defalutValue);
	}

	/**
	 * @Purpose : HashMap key의 value Float 타입 return
	 *
	 * @Method Name    : getFloat
	 * @Return         : Float
	 * @essential data :
	 * @Description    :
	 */
	public Float getFloat(String key) {
		return MapUtils.getFloat(map, key);
	}

	/**
	 * @Purpose : HashMap key의 value 값 null 이거나 빈스트링 인 경우 대체값으로 return
	 *
	 * @Method Name    : getFloat
	 * @Return         : Float
	 * @essential data :
	 * @Description    :
	 */
	public Float getFloat(String key, Float defaultValue) {
		return MapUtils.getFloat(map, key, defaultValue);
	}

	/**
	 * @Purpose : HashMap key의 value double 타입 return
	 *
	 * @Method Name    : getDoubleValue
	 * @Return         : double
	 * @essential data :
	 * @Description    :
	 */
	public double getDoubleValue(String key) {
		return MapUtils.getDoubleValue(map, key);
	}

	/**
	 * @Purpose : HashMap key의 value 값 null 이거나 빈스트링 인 경우 대체값으로 return
	 *
	 * @Method Name    : getDoubleValue
	 * @Return         : double
	 * @essential data :
	 * @Description    :
	 */
	public double getDoubleValue(String key, double defaultValue) {
		return MapUtils.getDoubleValue(map, key, defaultValue);
	}

	/**
	 * @Purpose : HashMap key의 value Double 타입 return
	 *
	 * @Method Name    : getDouble
	 * @Return         : Double
	 * @essential data :
	 * @Description    :
	 */
	public Double getDouble(String key) {
		return MapUtils.getDouble(map, key);
	}

	/**
	 * @Purpose : HashMap key의 value 값 null 이거나 빈스트링 인 경우 대체값으로 return
	 *
	 * @Method Name    : getDouble
	 * @Return         : Double
	 * @essential data :
	 * @Description    :
	 */
	public Double getDouble(String key, Double defaultValue) {
		return MapUtils.getDouble(map, key, defaultValue);
	}

	/**
	 * @Purpose : HashMap key의 value boolean 타입 return
	 *
	 * @Method Name    : getBoolean
	 * @Return         : boolean
	 * @essential data :
	 * @Description    :
	 */
	public boolean getBoolean(String key) {
		return MapUtils.getBooleanValue(map, key);
	}

	/**
	 * @Purpose : HashMap key의 value String[] 타입 return
	 *
	 * @Method Name    : getStringArray
	 * @Return         : String[]
	 * @essential data :
	 * @Description    :
	 */
	public String[] getStringArray(String key) {
		return MapUtil.getStringArray(map, key);
	}

	/**
	 * @Purpose : HashMap key의 value Integer[] 타입 return
	 *
	 * @Method Name    : getIntegerArray
	 * @Return         : Integer[]
	 * @essential data :
	 * @Description    :
	 */
	public Integer[] getIntegerArray(String key) {
		return MapUtil.getIntegerArray(map, key);
	}

	/**
	 * @Purpose : Map 배열 데이터 ',' 구분하여 문자열 변환
	 *
	 * @Method Name    : stringArrayConvert
	 * @Return         : void
	 * @essential data :
	 * @Description    :
	 */
	public void stringArrayConvert(String key) {
		MapUtil.stringArrayConvert(map, key);
	}

	/**
	 * @Purpose : Map 배열 데이터 구분자로 구분하여 문자열 변환
	 *
	 * @Method Name    : stringArrayConvert
	 * @Return         : void
	 * @essential data :
	 * @Description    :
	 */
	public void stringArrayConvert(String key, String delimiter) {
		MapUtil.stringArrayConvert(map, key, delimiter);
	}

	/**
	 * @Purpose : HashMap key 존재 여부
	 *
	 * @Method Name    : containsKey
	 * @Return         : boolean
	 * @essential data :
	 * @Description    :
	 */
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	/**
	 * @Purpose : Map key value에 데이터 포함 여부 (포함 true)
	 *
	 * @Method Name    : isContainValue
	 * @Return         : boolean
	 * @essential data :
	 * @Description    :
	 */
	public boolean isContainValue(String key, String search) {
		return MapUtil.isContainValue(map, key, search);
	}

	/**
	 * @Purpose : HashMap key value 값 존재 여부
	 *
	 * @Method Name    : isValue
	 * @Return         : boolean
	 * @essential data :
	 * @Description    :
	 */
	public boolean isValue(String key) {
		return StringUtil.hasText(getString(key));
	}

	/**
	 * @Purpose : HashMap key의 value List<ParamMap> 타입 return
	 *
	 * @Method Name    : getListMap
	 * @Return         : List<ParamMap>
	 * @essential data :
	 * @Description    :
	 */
	@SuppressWarnings("unchecked")
	public List<ParamMap> getListMap(String key) {
		return get(key) == null ? null : (List<ParamMap>) map.get(key);
	}
}
