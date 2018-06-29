package com.daol.oms.core.taglib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.daol.oms.co.service.COService;

/**
* 체크박스(라디오) 태그 구현 클래스
* @author 김경보
* @since 2011. 6. 23.
* @version 1.0
* @see
*
* <pre>
* == 개정이력(Modification Information) ==
*
*    수정일     수정자             수정내용
* ----------  -------  ---------------------------
* 2011.06.23   김경보    최초 작성
* </pre>
*/
@SuppressWarnings("serial")
public class CheckCodeTag extends RequestContextAwareTag {
	
	private static final Logger logger = LoggerFactory.getLogger(CheckCodeTag.class);
	
	/** 버튼 ID */
	private String id;
	/** 데이터 */
	private List<Map<String, Object>> items;
	/** 대분류코드 */
	private String lrgCd;
	/** 중분류코드 */
	private String midCd;
	/** 문자열데이터 */
	private String strItems;
	/** 사용여부 */
	private String useYn = "Y";
	/** 체크구분 */
	private String chkTp = "checkbox";
	/** 체크값 */
	private String chkVal;
	/** 전체출력 */
	private boolean disAll;
	/** 클릭이벤트 */
	private String onclick;

	public void setId(String id) {
		this.id = id;
	}
	public void setList(List<Map<String, Object>> items) {
		this.items = items;
	}
	public void setLrgCd(String lrgCd) {
		this.lrgCd = lrgCd;
	}
	public void setMidCd(String midCd) {
		this.midCd = midCd;
	}
	public void setStrItems(String strItems) {
		this.strItems = strItems;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public void setChkTp(String chkTp) {
		this.chkTp = chkTp;
	}
	public void setChkVal(String chkVal) {
		this.chkVal = chkVal;
	}
	public void setDisAll(boolean disAll) {
		this.disAll = disAll;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * Process the start tag.
	 *
	 * @return int SKIP_BODY
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doStartTagInternal() throws JspException {

		try{
			StringBuffer output = new StringBuffer();
	
			Map<String, Object> bean = null;
			
			if(items == null){
				items = new ArrayList<Map<String, Object>>();
				if(StringUtils.isNotEmpty(lrgCd)){
					//공통코드 취득
					COService coService = getRequestContext().getWebApplicationContext().getBean(COService.class);
					Map<String, Object> parm = new HashMap<String, Object>();
					parm.put("lrgCd", lrgCd);
					parm.put("midCd", midCd);
					parm.put("useYn", useYn);
					items = (List<Map<String, Object>>)coService.selectCodeList(parm);
					bean = new HashMap<String, Object>();
				}else if(StringUtils.isNotEmpty(strItems)){
					String[] options = strItems.split(";");
					String[] option = null;
					for(int i = 0; i < options.length; i++){
						option = options[i].split(":");
						if(option != null && option.length == 2){
							bean = new HashMap<String, Object>();
							bean.put("SML_CD", option[0]);
							bean.put("CD_NM", option[1]);
							items.add(bean);
						}
					}
				}
			}
			
			if(disAll){
				output.append("<input type='" + chkTp + "' name='" + id + "' id='" + id + "' value='' " + (StringUtils.isEmpty(chkVal) ? "checked='checked'" : "") + "onclick=\"" + (StringUtils.isNotEmpty(onclick) ? onclick : "") + "\"/><label for='" + id + "'>전체</label>");
			}
			boolean checked = false;
			for(int i = 0; i < items.size(); i++){
				bean = (Map<String, Object>) items.get(i);
				
				checked = false;
				if(StringUtils.isEmpty(chkVal)){
					if(chkTp.equals("radio") && i == 0 && !disAll){
						checked = true;
					}
				}else{
					if(chkVal.equals(bean.get("SML_CD"))){
						checked = true;
					}
				}
				output.append("<input type='" + chkTp + "' name='" + id + "' id='" + id + bean.get("SML_CD") + "' value='" + bean.get("SML_CD") + "' " + (checked ? "checked='checked'" : "") + " onclick=\"" + (StringUtils.isNotEmpty(onclick) ? onclick : "") + "\"/><label for='" + id + bean.get("SML_CD") + "'>" + bean.get("CD_NM") + "</label>");
			}

			JspWriter out = pageContext.getOut();
			out.print(output.toString());
		}catch(Exception e){
			logger.error("button tag error", e);
			return SKIP_BODY;  // Nothing to output
		}

		// Continue processing this page
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		id = null;
		items = null;
		strItems = null;
		lrgCd = null;
		midCd = null;
		useYn = "Y";
		chkTp = "checkbox";
		chkVal = null;

		return EVAL_PAGE;
	}
}