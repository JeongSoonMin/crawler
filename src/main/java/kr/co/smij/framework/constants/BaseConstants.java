/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.framework.constants;

/**
 * @Package : kr.co.smij.framework.config.constatns
 * @File    : BaseConstants.java
 * 
 * @Title   : 프레임워크 기본 설정
 * @date    : 2017. 12. 29.
 */
public class BaseConstants {

	/*
	 * numeric 형식으로 자동 치환 될 파라미터 명(endWith)
	 */
	public static final String[] DB_COL_TYPE_NUM_PK = { "_num", "_seq" };
	
	/*
	 * 요청 URL 키
	 */
	public static final String URL_PARAM_KEY = "URL_PARAM_KEY";
	
	/*
	 * 업로드 파일 키
	 */
	public static final String UPLOAD_PARAM_KEY = "UPLOAD_PARAM_KEY";
	
	/*
	 * 에러 메시지 내용 KEY
	 */
	public static final String ERR_MSG_KEY = "MSG";
	
	/*
	 * 접속 디바이스 코드
	 */
	public static final String CLIENT_ACCESS_DEVICE = "CLIENT_ACCESS_DEVICE";
	public static final String CLIENT_ACCESS_DEVICE_NULL = "NL";
	public static final String CLIENT_ACCESS_DEVICE_PC = "PC";
	public static final String CLIENT_ACCESS_DEVICE_MOBILE = "MB";
	public static final String CLIENT_ACCESS_DEVICE_TABLET = "TB";

	/*
	 * 암호화 KEY
	 */
	public static final String ENCRYPTION_STRING_KEY = "SMIJ_SHA512_KEY";
	public static final String AES_ENCRYPTION_STRING_KEY = "SCH.SMIJ.CO.KR.COPYRIGHT_J.S.M..";

	/*
	 * 회원 세션 KEY
	 */
	public static final String MBER_SESSION_KEY = "mberSession";

	/*
	 * 사용자 메인 페이지
	 */
	public static final String MBER_MAIN_URL = "/mber/main.page";

}
