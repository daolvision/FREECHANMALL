package com.daol.oms.core.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * DaolExceptionResolver
 * </pre>
 * 
 * @since 2018. 1. 16.
 * @author alarm
 */
@Component
public class DaolExceptionResolver implements HandlerExceptionResolver {

	@Autowired
	private MessageSourceAccessor msgSrcAccessor;

	private static final Logger logger = LoggerFactory.getLogger(DaolExceptionResolver.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {

		ModelAndView mav = new ModelAndView("jsonView");

		if (exception instanceof DaolException) {
			DaolException daolEx = (DaolException) exception;
			logger.error("[DaolException] {}", daolEx.getMessage(), daolEx);
			mav.addObject("rtnCode", daolEx.getErrorCode());
			mav.addObject("rtnMsg", daolEx.getMessage());
		} else if (exception instanceof PermissionException) {
			logger.error("[PermissionException] {}", exception.getMessage(), exception);
			mav.addObject("rtnCode", -99);
			mav.addObject("rtnMsg", exception.getMessage());
			mav.addObject("auth_error", exception.getMessage());
		} else if (exception instanceof DataAccessException) {
			logger.error("[DataAccessException] {}", exception.getMessage(), exception);
			mav.addObject("rtnCode", -80);
			mav.addObject("rtnMsg", msgSrcAccessor.getMessage("msg.exception.dataAccess", new Object[]{exception.getMessage()}));
		} else if (exception instanceof MaxUploadSizeExceededException) {
			MaxUploadSizeExceededException uploadEx = (MaxUploadSizeExceededException) exception;
			logger.error("[MaxUploadSizeExceededException] {}", uploadEx.getMessage(), uploadEx);
			mav.addObject("rtnCode", -210);
			mav.addObject("rtnMsg", msgSrcAccessor.getMessage("msg.exception.upload.size.limit", new Object[] { uploadEx.getMaxUploadSize()}));
		} else {
			logger.error("[{" + exception.getClass().getSimpleName() + "}] {}", exception.getMessage(), exception);
			mav.addObject("rtnCode", -1);
			mav.addObject("rtnMsg",msgSrcAccessor.getMessage("msg.exception", new Object[]{exception.getMessage()}));
		}

		return mav;
	}
}
