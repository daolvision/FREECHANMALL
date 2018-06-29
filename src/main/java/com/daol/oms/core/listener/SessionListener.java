package com.daol.oms.core.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daol.oms.co.COController;

/**
 * 세션 이벤트 핸들러
 * @author 김경보
 *
 */
public class SessionListener implements HttpSessionListener {

	private static int totalActiveSessions;
	
	private static final Logger logger = LoggerFactory.getLogger(COController.class);

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		totalActiveSessions++;
		logger.debug("[sessionCreated]... current total actived sessions is " + totalActiveSessions);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event){
		
		HttpSession session = event.getSession();
		
		if(session != null){
			totalActiveSessions--;
			logger.debug("[sessionDestroyed]... current total actived sessions is " + totalActiveSessions + ". and " + session.getAttribute("_custId") + " is destoried.");
			
//			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext(), FrameworkServlet.SERVLET_CONTEXT_PREFIX + "appServlet");
//			COService coService = (COService)wc.getBean(COService.class);
		
			if(StringUtils.isNotEmpty((String)session.getAttribute("_userId"))){
				// 중복로그인이 아닐 경우
//				if(StringUtils.isEmpty((String)session.getAttribute("isDup"))){
//					// 접속 종료 처리
//					Map<String, Object> param = new HashMap<String, Object>();
//					param.put("type", "logout");
//					param.put("lastAccTime", DaolUtils.getCurrentDateStr("yyyy-MM-dd HH:mm:ss"));
//					param.put("accTime", session.getAttribute("_accTime"));
//					param.put("userId", session.getAttribute("_userId"));
//					try{
//						coService.updateLoginUserInfo(param);
//					}catch(Exception e){
//						e.printStackTrace();
//					}
//				}
			}
		}
	}	
}
