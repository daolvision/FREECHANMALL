package com.daol.oms.core.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 검색 태그 구현 클래스
* @author 김경보
* @since 2017. 2. 10.
* @version 1.0
* @see
*
* <pre>
* == 개정이력(Modification Information) ==
*
*    수정일     수정자             수정내용
* ----------  -------  ---------------------------
* 2017.02.10   김경보    최초 작성
* </pre>
*/
@SuppressWarnings("serial")
public class SearchBoxTag extends TagSupport {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchBoxTag.class);
	
	/** 버튼 ID */
	private String id;
	/** TYPE(simple or pair) */
	private String type = "simple";
	/** VIEW (type이 view일 경우 view이 태그id)*/
	private String view;
	/** WIDTH */
	private String width;
	/** CSS */
	private String css;
	/** readonly */
	private boolean readonly = true;
	/** disable */
	private boolean disable = false;
	/** 클릭이벤트 */
	private String onclick;

	public void setId(String id) {
		this.id = id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setView(String view) {
		this.view = view;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}
	public void setDisable(boolean disable) {
		this.disable = disable;
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
	public int doStartTag() throws JspException {

		try{
			StringBuffer output = new StringBuffer();
			output.append("<script type='text/javascript' language='javascript'>	\n");
			output.append("//<!--	\n");
			output.append("if(!$.fn.disable){	\n");	
			output.append("	$.fn.disable = function(disabled){	\n");
			output.append("		if($('#' + this.prop('id') + '_div_')){	\n");
			output.append("			if(disabled){	\n");
			output.append("				$('#' + this.prop('id') + '_searchbox_').hide();	\n");
			output.append("				$('#' + this.prop('id') + '_div_ input:first-child').css({	\n");
			output.append("					'width' : 'calc(100% - .8em - 2px)',	\n");
			output.append("					'border-top-right-radius' : '6px',	\n");
			output.append("					'border-bottom-right-radius' : '6px',	\n");
			output.append("				});	\n");
			output.append("				$('#' + this.prop('id') + '_div_ input:first-child').prop('disabled', true);	\n");
			output.append("			}else{	\n");
			output.append("				$('#' + this.prop('id') + '_searchbox_').show();	\n");
			output.append("				$('#' + this.prop('id') + '_div_ input:first-child').css({	\n");
			output.append("					'width' : 'calc(100% - 37px)',	\n");
			output.append("					'border-top-right-radius' : '0px',	\n");
			output.append("					'border-bottom-right-radius' : '0px',	\n");
			output.append("				});	\n");
			output.append("				$('#' + this.prop('id') + '_div_ input:first-child').prop('disabled', false);	\n");
			output.append("			}	\n");
			output.append("		}	\n");
			output.append("	};	\n");
			output.append("}	\n");
			output.append("$(document).ready(function(){	\n");
			if(disable){
				output.append("$('#" + id + "').disable(true);	\n");
			}
			output.append("});	\n");
			output.append("//-->	\n");
			output.append("</script>	\n");
			output.append("<div id='" + id + "_div_' style='float:left;width:" + StringUtils.defaultIfEmpty(width, "100%") + "'>	\n");
			if(type.equals("simple")){
				output.append("	<input type='text' id='" + id + "' name='" + id + "' class='" + (StringUtils.isNotEmpty(css) ? css : "text") + "' style='width:calc(100% - 37px);float:left;border-top-right-radius: 0px;left;border-bottom-right-radius: 0px;' " + (readonly ? "readonly='readonly' onclick=\"" + onclick + "\"" : "") + "/>	\n");
				output.append("	<span id='" + id + "_searchbox_' class='searchbox-btn' " + (StringUtils.isNotEmpty(onclick) ? "onclick=\"" + onclick + "\"" : "") + "><i class='fa fa-search'></i></span>");
			}else if(type.equals("pair")){
				if(StringUtils.isEmpty(view)){
					view = id + "Name";
				}
				output.append("	<input type='text' id='" + view + "' name='" + view + "' class='" + (StringUtils.isNotEmpty(css) ? css : "text") + "' style='width:calc(100% - 37px);float:left;border-top-right-radius: 0px;left;border-bottom-right-radius: 0px;' " + (readonly ? "readonly='readonly' onclick=\"" + onclick + "\"" : "") + "/>	\n");
				output.append("	<span id='" + id + "_searchbox_' class='searchbox-btn' " + (StringUtils.isNotEmpty(onclick) ? "onclick=\"" + onclick + "\"" : "") + "><i class='fa fa-search'></i></span>");
				output.append("	<input type='hidden' id='" + id + "' name='" + id + "'/>	\n");
			}
			output.append("</div>	\n");
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
		type  = "simple";
		view = null;
		width = null;
		css = null;
		readonly = true;
		disable = false;
		onclick = null;

		return EVAL_PAGE;
	}
}