/*
 * Copyright (c) 2018 JSM Co., Ltd
 * All right reserved.
 */
package kr.co.smij.crawler.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.smij.framework.constants.BaseConstants;
import kr.co.smij.framework.controller.BaseController;
import kr.co.smij.framework.exception.BaseException;
import kr.co.smij.framework.model.ParamMap;
import kr.co.smij.framework.util.DateUtil;
import kr.co.smij.framework.util.RestUtil;
import kr.co.smij.framework.util.StringUtil;

/**
 * @Package : kr.co.smij.crawler.controller
 * @File : MainController.java
 * 
 * @Title :
 * @date : 2018. 6. 20.
 */
@Controller
public class MainController extends BaseController {
	private final static Logger log = LoggerFactory.getLogger(MainController.class);

	@Autowired
	ServletContext context;

	/**
	 * @Purpose : 메인 화면
	 *
	 * @Method Name : index
	 * @Author : 정순민
	 * @Date : 2018. 6. 20.
	 * @Return : Object
	 * @essentialData :
	 * @Description :
	 */
	@RequestMapping("/")
	public Object index(ParamMap paramMap, ModelMap modelMap) throws BaseException {
		paramMap.put(BaseConstants.URL_PARAM_KEY, "/index.page");

		return view(paramMap, modelMap);
	}

	/**
	 * @Purpose : 사이트 본문 내용 체크
	 *
	 * @Method Name : urlCheck
	 * @Author : 정순민
	 * @Date : 2018. 6. 20.
	 * @Return : Object
	 * @essentialData :
	 * @Description :
	 */
	@RequestMapping("/crawler/urlCheck")
	public Object urlCheck(ParamMap paramMap, ModelMap modelMap) throws BaseException {
		boolean result = true;
		String resultMsg = "";
		List<Map<String, Object>> list = null;
		String protocol = null;
		String domain = null;
		String url = paramMap.getString("url");
		String stsCd = null;
		long responseTime = 0;

		if (!paramMap.isValue("url")) {
			resultMsg = "URL이 지정되지 않았습니다.";
			result = false;
		}

		if (result) {
			modelMap.put("startTime", DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));

			if (url.startsWith("http://")) {
				protocol = "http://";
			} else if (url.startsWith("https://")) {
				protocol = "https://";
			}

			domain = url.replaceAll(protocol, "");
			if (domain.indexOf("/") > -1) {
				domain = domain.substring(0, domain.indexOf("/"));
			}

			// 기존파일 일괄 삭제
			File selectedDir = new File("image/" + domain);
			if (!selectedDir.exists()) {
				selectedDir.mkdir();
			}
			// 하위 디렉토리들을 배열에 담는다.
			File[] innerFiles = selectedDir.listFiles();
			// 하위 디렉토리 삭제
			if (innerFiles != null) {
				for (int i = 0; i < innerFiles.length; i++) {
					innerFiles[i].delete();
				}
			}

			Map<String, Object> rtData = RestUtil.callRest(paramMap.getString("url"));

			if ((boolean) rtData.get("result")) {
				String html = (String) rtData.get("data");
				stsCd = (String) rtData.get("stsCd");
				responseTime = (long) rtData.get("chkTime");

				// 이미지 정보 확인
				String regexPng = "\"[^<>\"]+[.]png\"";
				String regexJpg = "\"[^<>\"]+[.]jpg\"";
				Matcher mPng = Pattern.compile(regexPng).matcher(html);
				Matcher mJpg = Pattern.compile(regexJpg).matcher(html);
				list = new ArrayList<>();

				while (mPng.find()) {
					Map<String, Object> img = new HashMap<String, Object>();
					String rtUrl = mPng.group().replace("\"", "");

					if (!rtUrl.startsWith("http://") && !rtUrl.startsWith("https://")) {
						if (!rtUrl.startsWith("/")) {
							rtUrl = "/" + rtUrl;
						}

						rtUrl = protocol + domain + rtUrl;
					}

					img.put("domain", domain);
					img.put("url", rtUrl);
					img.put("fileName", rtUrl.substring(rtUrl.lastIndexOf("/") + 1, rtUrl.length()));

					list.add(img);
				}
				while (mJpg.find()) {
					Map<String, Object> img = new HashMap<String, Object>();
					String rtUrl = mJpg.group().replace("\"", "");

					// 지정된 서버 도메인 없을 경우 도메인 붙여서 처리
					if (!rtUrl.startsWith("http://") && !rtUrl.startsWith("https://")) {
						if (!rtUrl.startsWith("/")) {
							rtUrl = "/" + rtUrl;
						}

						rtUrl = protocol + domain + rtUrl;
					}

					img.put("domain", domain);
					img.put("url", rtUrl);
					img.put("fileName", rtUrl.substring(rtUrl.lastIndexOf("/") + 1, rtUrl.length()));

					list.add(img);
				}

				if (list.size() == 0) {
					result = false;
					resultMsg = "사용되는 이미지를 찾을 수 없습니다.";
				}

			} else {
				result = false;
				resultMsg = "정보를 가져올 수 없습니다. URL을 확인하여 주시기 바랍니다.";
			}
		}

		modelMap.put("result", result);
		modelMap.put("resultMsg", resultMsg);
		modelMap.put("stsCd", stsCd);
		modelMap.put("responseTime", responseTime);
		modelMap.put("list", list);

		return view(paramMap, modelMap);
	}

	/**
	 * @Purpose : 이미지 체크
	 *
	 * @Method Name : urlImgCheck
	 * @Author : 정순민
	 * @Date : 2018. 6. 20.
	 * @Return : Object
	 * @essentialData :
	 * @Description :
	 */
	@RequestMapping("/crawler/urlImgCheck")
	public Object urlImgCheck(ParamMap paramMap, ModelMap modelMap) {
		boolean result = true;
		String resultMsg = "";
		String stsCd = null;
		long responseTime = 0;

		String upDomain = paramMap.getString("domain");

		if (!upDomain.endsWith("/")) {
			upDomain += "/";
		}

		String upPath = "image/" + upDomain;
		String thumbPrefix = "thumb_";
		int chgWidth = 200;

		String url = paramMap.getString("url");
		String fileName = paramMap.getString("fileName");
		String fileExt = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
		int seq = Integer.parseInt(String.valueOf(paramMap.get("seq")));

		Map<String, Object> rtData = RestUtil.callRest(url);

		if ((boolean) rtData.get("result")) {
			byte[] rtBytes = (byte[]) rtData.get("data");
			stsCd = (String) rtData.get("stsCd");
			responseTime = (long) rtData.get("chkTime");

			if (rtBytes != null && rtBytes.length > 0) {
				File imgFile = new File(upPath + seq + "_" + fileName);
				try {
					imgFile.createNewFile();
					FileOutputStream fout = new FileOutputStream(imgFile);
					fout.write(rtBytes);
					fout.close();

					modelMap.put("imgPath", imgFile.getPath());
				} catch (FileNotFoundException fnfe) {
					result = false;
					resultMsg = "이미지 파일생성에 실패하였습니다.";
					log.error(fnfe.getMessage());
				} catch (IOException e) {
					result = false;
					resultMsg = "이미지 다운로드 실패하였습니다.";
					log.error(e.getMessage());
				}

				if (result) {
					// 썸네일 추출
					BufferedImage srcImg;
					try {
						srcImg = ImageIO.read(new File(upPath + seq + "_" + fileName));
						// 원본 이미지 넓이
						if (srcImg != null) {
							int oriWidth = srcImg.getWidth();

							// 원본 이미지 넓이가 200px 보다 클경우만 200px로 조정하고 작을 경우에는 원본사이즈
							if (chgWidth > oriWidth) {
								chgWidth = oriWidth;
							}

							BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC,
									Scalr.Mode.FIT_TO_WIDTH, chgWidth);

							// 썸네일을 저장합니다. 이미지 이름 앞에 "THUMB_" 를 붙여 표시했습니다.
							String thumbName = upPath + thumbPrefix + seq + "_" + fileName;
							File thumbFile = new File(thumbName);

							ImageIO.write(destImg, fileExt.toUpperCase(), thumbFile);

							modelMap.put("imgThumbUrl", "/" + thumbName);
						} else {
							result = false;
							resultMsg = "이미지 파일이 정상적이지 않습니다.";
						}

					} catch (IOException e) {
						result = false;
						resultMsg = "썸네일 변환에 실패하였습니다.";
						log.error(e.getMessage());
					}
				}

				modelMap.put("fileSize", StringUtil.getFileDanweSizeSsDw(String.valueOf(imgFile.length()), null, null));
				modelMap.put("chkTime", DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
			} else {
				result = false;
				resultMsg = "이미지 정보가 없습니다.";
			}
		} else {
			result = false;
			resultMsg = "이미지 다운로드 실패하였습니다.";
			stsCd = (String) rtData.get("stsCd");
			responseTime = (long) rtData.get("chkTime");
		}

		modelMap.put("seq", seq);
		modelMap.put("result", result);
		modelMap.put("resultMsg", resultMsg);
		modelMap.put("stsCd", stsCd);
		modelMap.put("responseTime", responseTime);

		return view(paramMap, modelMap);
	}
}
