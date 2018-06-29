package com.daol.oms.core.taglib;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.daol.oms.co.service.COService;

import net.sf.json.JSONArray;

/**
* 값(서버로부터) 태그 구현 클래스
* @author 김경보
* @since 2017. 11. 23.
* @version 1.0
* @see
*/
@SuppressWarnings("serial")
public class ValueTag extends RequestContextAwareTag {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	/** Value종류(config:환경변수, code:공통코드) */
	private String type;

	/** 검색용 인자 */
	private String param;

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * 인자 객체화
	 * @return
	 */
	private Map<String, Object> parsingParam(){
		
		Map<String, Object> pMap = new HashMap<String, Object>();

		// 파라미터 규칙(key1:value1;key2:value2;key3:value3)
		String[] arrParam = param.split(";");
		for(int i = 0; i < arrParam.length; i++){
			pMap.put(arrParam[i].split(":")[0], arrParam[i].split(":")[1]);
		}
		
		return pMap;
	}
	
	/**
	 * Process the start tag.
	 *
	 * @return int SKIP_BODY
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doStartTagInternal() throws JspException {

		try{
			
			COService coService = getRequestContext().getWebApplicationContext().getBean(COService.class);
			Object value = null;
			
			// 환경변수
			if("config".equals(type)){
				if(StringUtils.isEmpty(param)){
					value = coService.getConfigProp();
				}else{
					value = coService.getConfigProp(param);
				}
			// 공통코드
			}else if("code".equals(type)){
				if(StringUtils.isNotEmpty(param)){
					Map<String, Object> param = parsingParam();
					value = coService.selectCodeList(param);
				}
			}
			StringBuffer output = new StringBuffer();
			if(value instanceof Collection){
				output = output.append(JSONArray.fromObject(value));
			}else if(value instanceof Map){
				output = output.append(JSONArray.fromObject(value) + "[0]");
			}else{
				output.append(value);
			}
			
			JspWriter out = pageContext.getOut();
			out.print(output.toString());
		}catch(Exception e){
			logger.error("value tag error", e);
			return SKIP_BODY;  // Nothing to output
		}

		// Continue processing this page
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		type = null;
		param = null;
		
		return EVAL_PAGE;
	}
}