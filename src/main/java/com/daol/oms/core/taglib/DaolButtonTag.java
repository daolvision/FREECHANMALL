package com.daol.oms.core.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 사용자 버튼 태그 구현 클래스
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
public class DaolButtonTag extends TagSupport {
	
	private static final Logger logger = LoggerFactory.getLogger(DaolButtonTag.class);
	
	/** 버튼 ID */
	private String id;
	/** 버튼명 */
	private String title;
	/** 아이콘명 */
	private String icon;
	/** CSS */
	private String css;
	/** Style */
	private String style;
	/** 클릭이벤트 */
	private String onclick;
	/** 비활성화여부(defalt:false) */
	private boolean disable = false;

	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	/**
	 * Process the start tag.
	 *
	 * @return int SKIP_BODY
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doStartTag() throws JspException {

		try{
			StringBuffer output = new StringBuffer();
	
			output.append("<script type='text/javascript'>	\n");
			output.append("$(document).ready(function(){	\n");
			output.append("		$('#" + id + "').button({	\n");
			if(StringUtils.isNotEmpty(icon)){
				output.append("			icons: {		\n");
				output.append("				primary: '" + icon + "'	\n");
				output.append("			},	\n");
			}
			output.append("			disabled : " + disable + ",	\n");
			output.append("		}).click(function(event){	\n");
			output.append("			event.preventDefault();	\n");
			output.append("		});	\n");
			output.append("	});	\n");
			output.append("</script>	\n");
			output.append("<button id='" + id + "' title='" + title + "' class='" + (StringUtils.isNotEmpty(css) ? css : "") + "' style='" + (StringUtils.isNotEmpty(style) ? style : "") + "' onclick=\"" + (StringUtils.isNotEmpty(onclick) ? onclick : "") + "\">" + title + "</button>	\n");

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
		title = null;
		icon = null;
		css = null;
		style = null;
		onclick = null;
		disable = false;
   
		return EVAL_PAGE;
    }
}