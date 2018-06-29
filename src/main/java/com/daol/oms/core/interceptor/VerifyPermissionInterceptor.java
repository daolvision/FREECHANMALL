package com.daol.oms.core.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.daol.oms.core.exception.PermissionException;

/**
 * <pre>
 * VerifyPermissionInterceptor
 * </pre>
 * 
 * @since 2018. 1. 16.
 * @author 김경보
 */
public class VerifyPermissionInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(VerifyPermissionInterceptor.class);

	private List<String> ignoreUrl;

	/** 암호화 KEY */
	@Value("${encrypt.key.string}")
	private String encKeyStr;
	
	@Autowired
	private MessageSourceAccessor msgSrcAccessor;

//	@Autowired
//	private COService coService;
	
	public List<String> getIgnoreUrl() {
		return ignoreUrl;
	}

	public void setIgnoreUrl(List<String> urls) {
		ignoreUrl = urls;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String req_type = request.getParameter("req_type");
		String errMsg = null;
		
		logger.debug("request url : " + url);
		logger.debug("request type : " + req_type);
		logger.debug("request addr : " + request.getRemoteAddr());

		if(ignoreUrl.contains(StringUtils.startsWith(url, contextPath) ? url.substring(contextPath.length()) : url)) {
			return true;
		}

		HttpSession session = request.getSession(false);

		// 세션 체크
		if (session == null || StringUtils.isEmpty((String)session.getAttribute("_custId"))) {
			
			errMsg = msgSrcAccessor.getMessage("msg.exception.invalid.access", new Object[] { request.getRequestURI()});
		
		// 로그인 정보 확인
		}else{
			
			// 사용자 정보 취득
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("custId", session.getAttribute("_custId"));
			param.put("encKeyStr", encKeyStr);
//			Map<String, ?> userInfo = coService.selectLoginUserInfo(param);
//
//			// 접속 여부 확인
//			if("N".equals(userInfo.get("CUR_ACC_YN"))){
//				errMsg = msgSrcAccessor.getMessage("msg.exception.no.login.info");
//			// 접속 시간 확인(중복 로그인 확인)
//			}else if(!session.getAttribute("_accTime").equals(userInfo.get("LAST_ACC_DAT"))){
//				errMsg = msgSrcAccessor.getMessage("msg.exception.duplication.access");
//			}
//			
//			if(StringUtils.isNotEmpty(errMsg)){
//				// 중복 여부 설정
//				session.setAttribute("isDup", "Y");
//				session.invalidate();
//			}
		}
		
		if(StringUtils.isNotEmpty(errMsg)){
			//요청 타입 확인 (req_type : ajax)
			if("ajax".equals(req_type)){
				throw new PermissionException(errMsg);
			}else{
				request.getRequestDispatcher(request.getContextPath() + "/forword:err/ErrSession?errMsg=" + errMsg).forward(request, response);
			}
			return false;
		}
		
		return true;
	}
}
