package com.daol.oms.core.taglib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 달력 태그 구현 클래스
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
public class SingleCalendarTag extends TagSupport {
	
	private static final Logger logger = LoggerFactory.getLogger(SingleCalendarTag.class);
	
	/** 일자 ID */
	private String id;
	/** 일자(yyyyMMdd or yyyy-MM-dd or today) */
	private String date;
	/** 년도 범위(default:from(current-2):to(current)) */
	private String[] yearRange;
	/** 타이틀 */
	private String title;
	/** CHANGE 이벤트핸들러 */
	private String onchange;
	/** CSS */
	private String css;
	/** 날짜계산 */
	private int addDate;
	/** 날짜계산타입(일:day, 월:month, 년:year) */
	private String addType;
	/** 필수항목여부 */
	private boolean required = true;

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param date the to date set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @param yearRange the yearRange to set
	 */
	public void setYearRange(String yearRange) {
		this.yearRange = yearRange.split(":");
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param setOnchange the setOnchange to set
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	/**
	 * @param css the css to set
	 */
	public void setCss(String css) {
		this.css = css;
	}

	/**
	 * @param required the style to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setAddDate(int addDate) {
		this.addDate = addDate;
	}

	public void setAddType(String addType) {
		this.addType = addType;
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
	
			DateFormat df =  new SimpleDateFormat("yyyyMMdd");
			DateFormat df_view =  new SimpleDateFormat("yyyy-MM-dd");
	
			boolean hasIinitDate = StringUtils.isNotEmpty(date);
			
			String dateView = "";
			
			Calendar cal = Calendar.getInstance();
			
			if(StringUtils.isEmpty(date) || "today".equals(date)){
				date = df.format(cal.getTime());
			}
			if(date.length() != 8){
				date = df.format(df_view.parse(date));
			}
			
			if(required || hasIinitDate){
				if(addDate != 0){
					addType = StringUtils.isEmpty(addType) ? "day" : addType;
					cal.setTime(df.parse(date));
					cal.add(addType.equals("day") ? Calendar.DATE : (addType.equals("month") ? Calendar.MONTH : Calendar.YEAR), addDate);
					dateView = df_view.format(cal.getTime());
				}else{
					dateView = df_view.format(df.parse(date));
				}

			}

			output.append("<script type='text/javascript' language='javascript'>	\n");
			output.append("//<!--	\n");
			output.append("$(document).ready(function(){	\n");
			output.append("		$('#" + id + "').datepicker({	\n");
			output.append("			dateFormat: 'yy-mm-dd',	\n");
			output.append("			changeYear: true,			\n");
			output.append("			changeMonth: true,			\n");
			output.append("			prevText: '이전 달',	\n");
			output.append("			nextText: '다음 달',	\n");
			output.append("			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],	\n");
			output.append("			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],	\n");
			output.append("			dayNames: ['일','월','화','수','목','금','토'],	\n");
			output.append("			dayNamesShort: ['일','월','화','수','목','금','토'],	\n");
			output.append("			dayNamesMin: ['일','월','화','수','목','금','토'],	\n");
			output.append("			showMonthAfterYear: true,	\n");
			if(yearRange != null){
				output.append("			yearRange: '" + yearRange[0] + ":" + yearRange[1] + "',	\n");
			}
			if(StringUtils.isNotEmpty(onchange)){
				output.append("		onSelect:function(){" + onchange + "();},	\n");
			}
			output.append("			yearSuffix: '년'	\n");
			output.append("		});	\n");
			output.append("	});	\n");
			output.append("	function checkCalendar_" + id + "(){	\n");
			output.append("		var chgDate = $('#" + id + "').val().split('-').join('')	\n");
			output.append("		if(!isNaN(chgDate) && chgDate.length == 8){	\n");
			output.append("			chgDate = chgDate.substring(0, 4) + '-' + chgDate.substring(4, 6) + '-' + chgDate.substring(6, 8);	\n");
			output.append("			$('#" + id + "').val(chgDate);	\n");
			output.append("		}	\n");
			output.append("	}	\n");
			output.append("//-->	\n");
			output.append("</script>	\n");
			output.append("<input type='text' id='" + id + "' name='" + id + "' class='" + (StringUtils.isNotEmpty(css) ? css : "text") + "' value='" + dateView + "' " + (StringUtils.isNotEmpty(title) ? "title='" + title + "'" : "") + " style='text-align:center;' placeholder='yyyy-mm-dd' maxlength='10' onblur='checkCalendar_" + id + "();'/>");
			JspWriter out = pageContext.getOut();
			out.print(output.toString());
		}catch(Exception e){
			logger.error("calendar tag error", e);
			return SKIP_BODY;  // Nothing to output
		}

		// Continue processing this page
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		date = null;
		yearRange = null;
		title = null;
		onchange = null;
		css = "";
		required = true;
		addDate = 0;
		addType = null;
		
		return EVAL_PAGE;
	}
}