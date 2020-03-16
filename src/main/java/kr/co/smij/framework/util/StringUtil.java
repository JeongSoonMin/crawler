/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.framework.util;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @Package : kr.co.smij.framework.util
 * @File    : StringUtil.java
 * 
 * @Title   : 문자열 관련 util
 * @date    : 2017. 12. 18.
 */
public class StringUtil extends StringUtils {
	private static final Logger log = LoggerFactory.getLogger(StringUtil.class);

	public StringUtil() {

	}

	/**
	 * @Purpose : null data 변환 후 return
	 *
	 * @Method Name 	: nvl
	 * @Return        	: String
	 * @Description
	 */
	public static String nvl(Object object, String replaceStr) {
		return object == null ? replaceStr : object.toString();
	}

	/**
	 * @Purpose : null data 빈스트링 변환 후 return
	 *
	 * @Method Name 	: nvlStr
	 * @Return        	: String
	 * @Description
	 */
	public static String nvlStr(Object object) {
		return nvl(object, "");
	}

	/**
	 * @Purpose : minNum ~ maxNum 사이 랜덤으로 번호 return
	 *
	 * @Method Name    : getRandomRangeNum
	 * @Return         : int
	 * @essential data :
	 * @Description    :
	 */
	public static int getRandomRangeNum(int minNum, int maxNum) {
		return (int) (Math.random() * (maxNum + minNum + 1)) - minNum;
	}

	/**
	 * @Purpose : 숫자, 영문 대소문자 랜덤 값 return
	 *
	 * @Method Name    : randomValue
	 * @Return         : String
	 * @essential data :
	 * @Description    :
	 */
	public static String randomValue(int size) {
		StringBuffer result = new StringBuffer();

		do {
			// 아스키 코드값 기준 숫자, 영문 대소문자 영역
			int checkNum = getRandomRangeNum(48, 122);
			if ((checkNum >= 48 && checkNum <= 57) || (checkNum >= 65 && checkNum <= 90)
					|| (checkNum >= 97 && checkNum <= 122)) {

				result.append((char) checkNum);
			}

		} while (result.length() < size);

		return result.toString();
	}

	/**
	 * @Purpose : random key size length 만큼 생성해서 return
	 *
	 * @Method Name 	: randomKey
	 * @Return        	: String
	 * @Description
	 */
	public static String randomKey(String name, int size) {
		if ("".equals(nvlStr(name))) {
			name = "abc23456deEFGHIJRSTfghijYZ17klmnvKLMNOPQwxyzABCDUVWopqrstuX890";
		}

		StringBuilder randomStr = new StringBuilder();
		Random random = new Random(System.currentTimeMillis());
		int rmNum = 0;
		char ch = 'A';

		for (int i = 0; i < size; i++) {
			random.setSeed(System.currentTimeMillis() * Math.round(Math.random() * 1000000000));
			rmNum = random.nextInt(25);
			ch += rmNum;
			randomStr.append(ch);
			ch = 'A';
		}

		return randomStr.toString();
	}

	/**
	 * @Purpose : random key size length 만큼 생성해서 return
	 *
	 * @Method Name 	: randomKey
	 * @Return        	: String
	 * @Description
	 */
	public static String randomKey(int size) {
		return randomKey("", size);
	}

	/**
	 * @Purpose : 날짜 포함 랜덤키 생성
	 *
	 * @Method Name 	: randomDateKey
	 * @Return          : String
	 * @essential data  : 
	 * @Description 	: 
	 * 		기본 셋팅 : 141224_asdfghjk 리턴.
	 * 		기본값 : 년월일 6자리 + "_" + 랜덤키 8자리
	 * 		format : yyyyMMdd 형식으로 받음
	 * 		size
	 */
	public static String randomDateKey(String format, int size) {
		return DateUtil.getToday(format) + "_" + randomKey(size);
	}

	/**
	 * @Purpose : 날짜 포함 랜덤키 생성(자리수만 입력)
	 *
	 * @Method Name 	: randomDateKey
	 * @Return          : String
	 * @essential data  : 
	 * @Description 	: 
	 */
	public static String randomDateKey(int size) {
		return randomDateKey("yyMMdd", size);
	}

	/**
	 * @Purpose : 날짜 포함 랜덤키 생성(날짜 포맷만 입력)
	 *
	 * @Method Name 	: randomDateKey
	 * @Return          : String
	 * @essential data  : 
	 * @Description 	: 
	 */
	public static String randomDateKey(String format) {
		return randomDateKey(format, 8);
	}

	/**
	 * @Purpose : 날짜 포함 랜덤키 생성(기본셋팅으로 생성)
	 *
	 * @Method Name 	: randomDateKey
	 * @Return          : String
	 * @essential data  : 
	 * @Description 	: 
	 */
	public static String randomDateKey() {
		return randomDateKey("yyMMdd", 8);
	}

	/**
	 * @Purpose : Object 파라미터 null이나 ""값 체크 후 데이터 변경 후 return (기본 trim 처리)
	 *
	 * @Method Name 	: defaultStr
	 * @Return        	: String
	 * @Description
	 */
	public static String defaultStr(Object obj, String changeValue) {
		return (obj == null || "".equals(obj.toString().trim())) ? changeValue : obj.toString().trim();
	}

	/**
	 * @Purpose : Object 데이터 null 일 경우 "" 으로 return
	 *
	 * @Method Name 	: defaultStr
	 * @Return        	: String
	 * @Description
	 */
	public static String defaultStr(Object obj) {
		return defaultStr(obj, "");
	}

	/**
	 * @Purpose : 공백이나 빈스트링 일 경우 null return
	 *
	 * @Method Name 	: defaultStrToNull
	 * @Return        	: String
	 * @Description
	 */
	public static String defaultStrToNull(Object obj) {
		return obj == null ? null : ("".equals(obj.toString().trim()) ? null : obj.toString());
	}

	/**
	 * @Purpose : int 형 데이터 null 일 경우 0 return
	 *
	 * @Method Name 	: defaultInt
	 * @Return        	: int
	 * @Description
	 */
	public static int defaultInt(Integer i) {
		return (Object) i == null ? 0 : i;
	}

	/**
	 * @Purpose : Object 형 데이터 null 일 경우 0 return
	 *
	 * @Method Name : defaultInt
	 * @Return      : int
	 * @Description :
	 */
	public static int defaultInt(Object obj) {
		int value = 0;

		if (obj != null) {
			try {
				value = Integer.parseInt(obj.toString());
			} catch (NumberFormatException nfe) {
				log.error("number format exception >>> " + nfe);
			}
		}

		return value;
	}

	/**
	 * @Purpose : int 형 데이터가 null 이거나 0 일 경우 데이터 변경 후 return
	 *
	 * @Method Name 	: defaultInt
	 * @Return        	: int
	 * @Description
	 */
	public static int defaultInt(Integer i, int changeValue) {
		return defaultInt(i) == 0 ? changeValue : i;
	}

	/**
	 * @Purpose :  long 형 데이터 null 일 경우 0 return
	 *
	 * @Method Name 	: defaultLong
	 * @Return        	: Long
	 * @Description
	 * 
	 */
	public static Long defaultLong(Long l) {
		return (Object) l == null ? 0 : l;
	}

	/**
	 * @Purpose : Object 형 데이터 null 일 경우 0 return
	 *
	 * @Method Name : defaultLong
	 * @Return      : Long
	 * @Description
	 */
	public static Long defaultLong(Object obj) {
		return (Object) obj == null ? 0 : Long.parseLong((String) obj);
	}

	/**
	 * @Purpose : long 형 데이터가 null 이거나 0 일 경우 데이터 변경 후 return
	 *
	 * @Method Name 	: defaultLong
	 * @Return        	: Long
	 * @Description
	 */
	public static Long defaultLong(Long l, long changeValue) {
		return defaultLong(l) == 0 ? changeValue : l;
	}

	/**
	 * @Purpose : Object 형 데이터가 null 이거나 0 일 경우 데이터 변경 후 return
	 *
	 * @Method Name : defaultLong
	 * @Return      : Long
	 * @Description :
	 */
	public static Long defaultLong(Object obj, long changeValue) {
		return defaultLong(obj) == 0 ? changeValue : (Long) obj;
	}

	/**
	 * @Purpose :  float 형 데이터 null 일 경우 0 return
	 *
	 * @Method Name 	: defaultFloat
	 * @Return        	: float
	 * @Description
	 */
	public static float defaultFloat(Float f) {
		return (Object) f == null ? 0 : f;
	}

	/**
	 * @Purpose : Object 형 데이터 null 일 경우 0 return
	 *
	 * @Method Name : defaultFloat
	 * @Return      : float
	 * @Description :
	 */
	public static float defaultFloat(Object obj) {
		return (Object) obj == null ? 0 : Float.parseFloat(obj.toString());
	}

	/**
	 * @Purpose : float 형 데이터가 null 이거나 0 일 경우 데이터 변경 후 return
	 *
	 * @Method Name 	: defaultFloat
	 * @Return        	: float
	 * @Description
	 */
	public static float defaultFloat(Float f, float changeValue) {
		return defaultFloat(f) == 0 ? changeValue : f;
	}

	/**
	 * @Purpose : Object 형 데이터가 null 이거나 0 일 경우 데이터 변경 후 return
	 *
	 * @Method Name : defaultFloat
	 * @Return      : float
	 * @Description :
	 */
	public static float defaultFloat(Object obj, float changeValue) {
		return defaultFloat(obj) == 0 ? changeValue : Float.parseFloat(obj.toString());
	}

	/**
	 * @Purpose : double 형 데이터 null 일 경우 0 return
	 *
	 * @Method Name 	: defaultDouble
	 * @Return        	: double
	 * @Description
	 */
	public static double defaultDouble(Double d) {
		return (Object) d == null ? 0 : d;
	}

	/**
	 * @Purpose : double 형 데이터 null 일 경우 0 return
	 *
	 * @Method Name : defaultDouble
	 * @Return      : double
	 * @Description :
	 */
	public static double defaultDouble(Object obj) {
		return (Object) obj == null ? 0 : Double.parseDouble(obj.toString());
	}

	/**
	 * @Purpose : double 형 데이터가 null 이거나 0 일 경우 데이터 변경 후 return
	 *
	 * @Method Name 	: defaultDouble
	 * @Return        	: double
	 * @Description
	 */
	public static double defaultDouble(Double d, double changeValue) {
		return defaultDouble(d) == 0 ? changeValue : d;
	}

	/**
	 * @Purpose : Object 형 데이터가 null 이거나 0 일 경우 데이터 변경 후 return
	 *
	 * @Method Name : defaultDouble
	 * @Return      : double
	 * @Description :
	 */
	public static double defaultDouble(Object obj, double changeValue) {
		return defaultDouble(obj) == 0 ? changeValue : Double.parseDouble(obj.toString());
	}

	/**
	 * @Purpose : Object 형 데이터가 null 일 경우 false
	 *
	 * @Method Name    : defaultBoolean
	 * @Return         : boolean
	 * @essential data :
	 * @Description    :
	 */
	public static boolean defaultBoolean(Object obj) {
		return obj == null ? false : Boolean.parseBoolean(obj.toString());
	}

	/**
	 * @Purpose : request에 담긴 parameter return
	 *
	 * @Method Name 	: getParam
	 * @Return        	: String
	 * @Description
	 */
	public static String getParam(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}

	/**
	 * @Purpose : request 에 담긴 문자형 파라미터 return(null 이거나 공백이면 "" return)
	 *
	 * @Method Name 	: getParamStr
	 * @Return        	: String
	 * @Description
	 */
	public static String getParamStr(HttpServletRequest request, String name) {
		return defaultStr(request.getParameter(name));
	}

	/**
	 * @Purpose : request 에 담긴 문자형 파라미터 null 이거나 ""값일 경우 문자변경하여 return;
	 *
	 * @Method Name 	: getParamStr
	 * @Return        	: String
	 * @Description
	 */
	public static String getParamStr(HttpServletRequest request, String name, String changeValue) {
		return defaultStr(request.getParameter(name), changeValue);
	}

	/**
	 * @Purpose : request에 담긴 parameter int 형변환 return
	 *
	 * @Method Name 	: getParamInt
	 * @Return        	: int
	 * @Description
	 */
	public static int getParamInt(HttpServletRequest request, String name) {
		if (getParam(request, name) == null) {
			return 0;
		} else {
			try {
				return Integer.parseInt(getParam(request, name));
			} catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	/**
	 * @Purpose : request에 담긴 parameter int 형변환 후 null 이거나 0 일 경우 데이터 변환 return
	 *
	 * @Method Name 	: getParamInt
	 * @Return        	: int
	 * @Description
	 */
	public static int getParamInt(HttpServletRequest request, String name, int changeValue) {
		return defaultInt(getParamInt(request, name), changeValue);
	}

	/**
	 * @Purpose : request에 담긴 parameter long 형변환 return
	 *
	 * @Method Name 	: getParamLong
	 * @Return        	: long
	 * @Description
	 */
	public static long getParamLong(HttpServletRequest request, String name) {
		if (getParam(request, name) == null) {
			return 0;
		} else {
			try {
				return Long.parseLong(getParam(request, name));
			} catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	/**
	 * @Purpose :  request에 담긴 parameter long 형변환 후 null 이거나 0 일 경우 데이터 변환 return
	 *
	 * @Method Name 	: getParamLong
	 * @Return        	: long
	 * @Description
	 */
	public static long getParamLong(HttpServletRequest request, String name, long changeValue) {
		return defaultLong(getParamLong(request, name), changeValue);
	}

	/**
	 * @Purpose : request에 담긴 parameter float 형변환 return
	 *
	 * @Method Name 	: getParamFloat
	 * @Return        	: float
	 * @Description
	 */
	public static float getParamFloat(HttpServletRequest request, String name) {
		if (getParam(request, name) == null) {
			return 0;
		} else {
			try {
				return Float.parseFloat(getParam(request, name));
			} catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	/**
	 * @Purpose : request에 담긴 parameter float 형변환 후 null 이거나 0 일 경우 데이터 변환 return
	 *
	 * @Method Name 	: getParamFloat
	 * @Return        	: float
	 * @Description
	 */
	public static float getParamFloat(HttpServletRequest request, String name, float changeValue) {
		return defaultFloat(getParamFloat(request, name), changeValue);
	}

	/**
	 * @Purpose : request에 담긴 parameter double 형변환 return
	 *
	 * @Method Name 	: getParamDouble
	 * @Return        	: double
	 * @Description
	 */
	public static double getParamDouble(HttpServletRequest request, String name) {
		if (getParam(request, name) == null) {
			return 0;
		} else {
			try {
				return Double.parseDouble(getParam(request, name));
			} catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	/**
	 * @Purpose : request에 담긴 parameter double 형변환 후 null 이거나 0 일 경우 데이터 변환 return
	 *
	 * @Method Name 	: getParamDouble
	 * @Return        	: double
	 * @Description
	 */
	public static double getParamDouble(HttpServletRequest request, String name, double changeValue) {
		return defaultDouble(getParamDouble(request, name), changeValue);
	}

	/**
	 * @Purpose : textarea 데이터 보여줄 시 \r\n -> <br> 변경
	 *
	 * @Method Name 	: convertNewlineToBR
	 * @Return        	: String
	 * @Description
	 */
	public static String convertNewlineToBR(String source) {
		if (source != null) {
			// null 문자 제거
			source = source.replaceAll("\0", "");

			Pattern[] patterns = new Pattern[] { Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
					Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
					Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
					Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
					Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
					Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
					Pattern.compile("expression\\((.*?)\\)",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
					Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
					Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
					Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL) };

			for (Pattern scriptPattern : patterns) {
				if (scriptPattern.matcher(source).find()) {
					source = source.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
				}
			}

			source = source.replaceAll("\r\n", "<br>");
			source = source.replaceAll("\r", "<br>");
			source = source.replaceAll("\n", "<br>");
		}

		return source;
	}

	/**
	 * @Purpose : 글자수 줄임 표현
	 *
	 * @Method Name 	: contractStr
	 * @Return        	: String
	 * @Description
	 */
	public static String contractStr(String source, int max) {
		return (source.length() > max) ? source.substring(0, max) + "..." : source;
	}

	/**
	 * @Purpose : int -> boolean return
	 *
	 * @Method Name 	: intToBoolean
	 * @Return        	: boolean
	 * @Description
	 * 
	 * 1:true, 이외 false
	 * 
	 */
	public static boolean intToBoolean(Integer i) {
		return defaultInt(i) == 1 ? true : false;
	}

	/**
	 * @Purpose : String -> int
	 *
	 * @Method Name 	: StringToInt
	 * @Return        	: int
	 * @Description
	 */
	public static int StringToInt(String str) {
		int result = 0;
		if (!"".equals(defaultStr(str))) {
			result = Integer.parseInt(str);
		}

		return result;
	}

	/**
	 * @Purpose : 백분율 계산
	 *
	 * @Method Name 	: ratioCal
	 * @Return        	: String
	 * @Description
	 */
	@SuppressWarnings("static-access")
	public static String ratioCal(int count, int total, int point) {
		String ratio = "0";

		if (total != 0) {
			ratio = ratio.format("%." + String.valueOf(point) + "f", (double) count / (double) total * 100);
		}

		return ratio;
	}

	/**
	 * @Purpose : 이메일 유효성 체크
	 *
	 * @Method Name    : isEmail
	 * @Return         : boolean
	 * @essential data :
	 * @Description    :
	 */
	public static boolean isEmail(String eamil) {
		String emailPatten = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(emailPatten);
		return pattern.matcher(eamil).matches();
	}

	public static boolean isNumber(String key) {
		try {
			Integer.parseInt(key);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static String getFileDanweSizeSsDw(String size, String decimal, String measure) {
		String returnSize = "";
		double fileSize = 0;
		double changeSize = 0;
		int gbKey = 0;
		String[] sizeGb = { "Byte", "KByte", "MByte", "GByte" };
		String pattern = "#";
		String determinePattern = "";
		String point = ".";
		int dec = 0;

		// 자릿수 지정
		DecimalFormat format = new DecimalFormat();
		// 자릿수 지정 했을때
		if (decimal != null && !"".equals(decimal)) {
			dec = Integer.parseInt(decimal);
		}
		// 자릿수 미지정시 기본 소수점 자리수 2 자릿수로 지정.
		else {
			dec = 2;
		}
		// 자릿수 0으로 지정시 소수점 표현안함
		if (dec == 0) {
			point = "";
		} else {
			for (int i = 0; i < dec; i++) {
				determinePattern += pattern;
			}
		}

		format.applyLocalizedPattern("###,###,###" + point + determinePattern);

		if (size != null && !"".equals(size)) {
			fileSize = Long.parseLong(size);
			// 단위 미지정시 사이즈별 단위 지정 표시
			if (measure == null || "".equals(measure)) {
				for (int x = 0; (fileSize / (double) 1024) > 0; x++, fileSize /= (double) 1024) {
					if (fileSize > 0 && fileSize < 1) {
						break;
					}
					gbKey = x;
					changeSize = fileSize;
				}
			}
			// 단위 지정시 해당 단위의 사이즈로 표현.
			else {
				int gbCnt = 0;
				for (String gb : sizeGb) {
					if (gb.equals(measure)) {
						changeSize = fileSize;
						break;
					}
					fileSize = fileSize / (double) 1024;
					gbCnt++;
				}
				gbKey = gbCnt;
			}

			returnSize = format.format(changeSize) + sizeGb[gbKey];
		} else {
			returnSize = "0 Byte";
		}

		return returnSize;
	}
}
