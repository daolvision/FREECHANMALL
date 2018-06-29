package com.daol.oms.core.json;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * DaolArgumentResolver
 * @since 2018. 1. 11
 * @author alarm
 */
public class JsonArgumentResolver implements HandlerMethodArgumentResolver {

	private static final Logger logger = LoggerFactory.getLogger(JsonArgumentResolver.class);
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return JsonDTO.class.isAssignableFrom(parameter.getParameterType());
	}
	
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		
		HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

		JsonDTO dto = new JsonDTO(request);

		logger.debug("========" + request.getRequestURI() + "========");
		logger.debug("request data : " + dto.getRequestData());
		logger.debug("session data : " + dto.getSessionData());
		
		return dto;
	}
}
