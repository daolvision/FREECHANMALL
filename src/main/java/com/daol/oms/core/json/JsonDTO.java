package com.daol.oms.core.json;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JsonDTO
 * @since 2018. 1. 11
 * @author alarm
 */
public class JsonDTO {
	
	/** 세션 정보 */
	protected Map<String, Object> sessionData = new HashMap<String, Object>();
	
	/** 요청 파라미터 정보 */
	protected Map<String, Object> requestData = new HashMap<String, Object>();
	
	/** 세션 및 요청 파라미터 정보 제공 */
	private Map<String, Object> reqSessionData = null;
	
	/**
	 * 생성자
	 * @param req
	 */
	protected JsonDTO(HttpServletRequest request){
		convertData(request);
	}
	
	/**
	 * 데이터 변환
	 * @param request
	 */
	private void convertData(HttpServletRequest request){
		
		// 세션정보 변환
		HttpSession session = request.getSession(false);
		if(session != null){
			Enumeration<String> e = (Enumeration<String>)session.getAttributeNames();
			String key = null;
			while(e.hasMoreElements()){
				key = e.nextElement();
				sessionData.put(key, session.getAttribute(key));
			}
		}
		
		Enumeration<String> e = request.getParameterNames();
		String pName = null;
		String pValue = null;
		while(e.hasMoreElements()){
			pName = e.nextElement();
			pValue = request.getParameter(pName);
			// json 여부 판단
			if(StringUtils.startsWith(pValue, "{") && StringUtils.endsWith(pValue, "}")){
				requestData.put(pName, JSONObject.toBean(JSONObject.fromObject(pValue), Map.class));
			}else if(StringUtils.startsWith(pValue, "[") && StringUtils.endsWith(pValue, "]")){
				System.out.println("pName : " + pName + ", pValue : " + pValue);
				requestData.put(pName, JsonHelper.toList(JSONArray.fromObject(pValue)));
			}else{
				requestData.put(pName, pValue);
			}
		}
		
		//파일업로드
		if(request instanceof MultipartHttpServletRequest){
			MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest)request;
			Iterator<String> iter = mrequest.getFileNames();
			String fileName = "";
			while(iter.hasNext()){
				fileName = iter.next();
				requestData.put(fileName, mrequest.getFile(fileName));
			}
		}
	}

	/**
	 * 세션 정보 제공
	 * @return
	 */
	public Map<String, Object> getSessionData(){
		return sessionData;
	}

	/**
	 * 세션 정보 설정
	 * @return
	 */
	public void setSessionData(String name, String value){
		sessionData.put(name, value);
	}

	/**
	 * 요청 파라미터 정보 제공
	 * @return
	 */
	public Map<String, Object> getRequestData(){
		return requestData;
	}
	
	/**
	 * 요청 파라미터 정보 설정
	 * @return
	 */
	public void setRequestData(String name, String value){
		requestData.put(name, value);
	}
	
	/**
	 * 세션 및 요청 파라미터 정보 제공
	 * @return
	 */
	public Map<String, Object> getReqSessionData(){
		
		if(reqSessionData == null){
			reqSessionData = new HashMap<String, Object>();
			reqSessionData.putAll(requestData);
			reqSessionData.putAll(sessionData);
		}
		
		return reqSessionData;
	}
}