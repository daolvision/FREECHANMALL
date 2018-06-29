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
public class MultiCalendarTag extends TagSupport {
	
	private static final Logger logger = LoggerFactory.getLogger(MultiCalendarTag.class);
	
	/** 시작일자 ID */
	private String from;
	/** 종료일자 ID */
	private String to;
	/** 시작일자(yyyyMMdd or yyyy-MM-dd) */
	private String fromDate;
	/** 종료일자(yyyyMMdd or yyyy-MM-dd) */
	private String toDate;
	/** 기본 기간 */
	private int period;
	/** 기본 기간 단위(일:day, 월:month, 년:year) */
	private String periodType;
	/** 년도 범위(default:from(current-2):to(current)) */
	private String[] yearRange;
	/** WIDTH */
	private String width;
	/** CSS */
	private String css;
	/** 필수항목여부 */
	private boolean required = true;

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * @param periodType the periodType to set
	 */
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	/**
	 * @param yearRange the yearRange to set
	 */
	public void setYearRange(String yearRange) {
		this.yearRange = yearRange.split(":");
	}

	/**
	 * @param css the css to set
	 */
	public void setCss(String css) {
		this.css = css;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @param required the style to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
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
	
			String fromDateView = null;
			String toDateView = null;
			
			if(StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate)){
				
				if(required){
					Calendar cal = Calendar.getInstance();
					toDate = df.format(cal.getTime());
					toDateView = df_view.format(cal.getTime());
					if(StringUtils.isNotEmpty(periodType)){
						if(period != 0){
							cal.add(periodType.equals("day") ? Calendar.DATE : periodType.equals("month") ? Calendar.MONTH : Calendar.YEAR, period * -1);
							cal.add(Calendar.DATE, 1);
						}else{
							if(periodType.equals("month")){
								//월초
								cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
							}else if(periodType.equals("year")){
								//년초
								cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
								cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
							}
						}
					}
					fromDate = df.format(cal.getTime());
					fromDateView = df_view.format(cal.getTime());
				}
			}else {
				if(fromDate.length() != 8){
					fromDate = df.format(df_view.parse(fromDate));
				}
				fromDateView = df_view.format(df.parse(fromDate));
				
				if(toDate.length() != 8){
					toDate = df.format(df_view.parse(toDate));
				}
				toDateView = df_view.format(df.parse(toDate));
			}
			

			output.append("<script type='text/javascript' language='javascript'>	\n");
			output.append("//<!--	\n");
			output.append("$(document).ready(function(){	\n");
			output.append("		var dates = $('#" + from + ", #" + to + "').datepicker({	\n");
			output.append("		onSelect: function(selectedDate){	\n");
			output.append("			var option = this.id == '" + from + "' ? 'minDate' : 'maxDate';	\n");
			output.append("			var	instance = $(this).data('datepicker');	\n");
			output.append("			var	date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings );	\n");
			output.append("			dates.not(this).datepicker('option', option, date);	\n");
			output.append("		},	\n");
			output.append("		dateFormat: 'yy-mm-dd',	\n");
			output.append("		changeYear: true,			\n");
			output.append("		changeMonth: true,			\n");
			output.append("		prevText: '이전 달',	\n");
			output.append("		nextText: '다음 달',	\n");
			output.append("		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],	\n");
			output.append("		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],	\n");
			output.append("		dayNames: ['일','월','화','수','목','금','토'],	\n");
			output.append("		dayNamesShort: ['일','월','화','수','목','금','토'],	\n");
			output.append("		dayNamesMin: ['일','월','화','수','목','금','토'],	\n");
			output.append("		showMonthAfterYear: true,	\n");
			output.append("	});	\n");
			if(yearRange != null){
				output.append("	$('#" + from + "').datepicker('option', 'yearRange', '" + yearRange[0] + ":" + yearRange[1] + "');	\n");
				output.append("	$('#" + to + "').datepicker('option', 'yearRange', '" + yearRange[2] + ":" + yearRange[3] + "');	\n");
			}else{
				output.append("	$('#" + from + "').datepicker('option', 'yearRange', 'c-2:c');	\n");
				output.append("	$('#" + to + "').datepicker('option', 'yearRange', 'c-2:c');	\n");
			}
			output.append("});	\n");
			output.append("	function checkCalendar_" + from + "(){	\n");
			output.append("		var chgDate = $('#" + from + "').val().split('-').join('')	\n");
			output.append("		if(!isNaN(chgDate) && chgDate.length == 8){	\n");
			output.append("			chgDate = chgDate.substring(0, 4) + '-' + chgDate.substring(4, 6) + '-' + chgDate.substring(6, 8);	\n");
			output.append("			$('#" + from + "').val(chgDate);	\n");
			output.append("		}	\n");
			output.append("	}	\n");
			output.append("	function checkCalendar_" + to + "(){	\n");
			output.append("		var chgDate = $('#" + to + "').val().split('-').join('')	\n");
			output.append("		if(!isNaN(chgDate) && chgDate.length == 8){	\n");
			output.append("			chgDate = chgDate.substring(0, 4) + '-' + chgDate.substring(4, 6) + '-' + chgDate.substring(6, 8);	\n");
			output.append("			$('#" + to + "').val(chgDate);	\n");
			output.append("		}	\n");
			output.append("	}	\n");
			output.append("//-->	\n");
			output.append("</script>	\n");
			output.append("<div id='" + from + "_" + to + "_div_' style='float:left;width:" + StringUtils.defaultIfEmpty(width, "100%") + "'>	\n");
			output.append("	<input type='text' id='" + from + "' name='" + from + "' class='" + (StringUtils.isNotEmpty(css) ? css : "text") + "' value='" + fromDateView + "' title='시작일' style='width:calc(50% - 21px);text-align:center;' placeholder='yyyy-mm-dd' maxlength='10' onblur='checkCalendar_" + from + "'/> ~ ");
			output.append("	<input type='text' id='" + to + "' name='" + to + "' class='" + (StringUtils.isNotEmpty(css) ? css : "text") + "' value='" + toDateView + "' title='종료일' style='width:calc(50% - 21px);text-align:center;' placeholder='yyyy-mm-dd' maxlength='10' onblur='checkCalendar_" + to + "'/>");
			output.append("</div>	\n");

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
    	from = null;
    	to = null;
    	fromDate = null;
    	toDate = null;
    	period = 0;
    	periodType = null;
    	yearRange = null;
    	width = null;
    	css = null;
    	required = true;

    	return EVAL_PAGE;
    }
}