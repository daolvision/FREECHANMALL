package com.daol.oms.core.taglib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
* 공통코드 셀렉트박스 태그 구현 클래스
* @author 김경보
* @since 2018. 01. 23.
* @version 1.0
* @see
*/
@SuppressWarnings("serial")
public class SelectTag extends RequestContextAwareTag {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	/** 태그 ID */
	private String id;

	/** 대분류코드 */
	private String lrgCd;

	/** 중분류코드 */
	private String midCd;

	/** 소분류코드 */
	private String smlCd;
	
	/** 예외코드(공통코드일경우만 적용됨) */
	private String ignoreCd;

	/** 코드구분(공통코드일경우만 적용됨) */
	private String cdTp;

	/** 데이터 */
	private List<Map<String, Object>> items;
	
	/** 문자열 데이터 */
	private String strItems;

	/** 값컬럼 (defalt:smlCd)*/
	private String valCol = "SML_CD";

	/** 라벨컬럼 (defalt:comNm)*/
	private String txtCol = "CD_NM";

	/** 전체 옵션 생성 여부(defalt:false) */
	private boolean all;

	/** 선택 옵션 생성 여부(defalt:false) */
	private boolean select;

	/** 다중선택 여부(defalt:false) */
	private boolean multiple;

	/** 선택값 설정 */
	private String selected;

	/** 제목 설정 */
	private String title;

	/** CSS 스타일 적용 */
	private String style;

	/** 연관항목 */
	private String relationItem;
	
	/** 연관항목컬럼 */
	private String relationItemCol;
	
	/** 추가연관항목 */
	private String subRelationItem;

	/** 추가연관항목컬럼 */
	private String subRelationItemCol;

	/** CHANGE 이벤트핸들러 */
	private String onchange;

	/** 중복OPTION 허용 여부 */
	private boolean isDupOption = true;

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param lrgCd the lrgCd to set
	 */
	public void setLrgCd(String lrgCd) {
		this.lrgCd = lrgCd;
		
	}

	/**
	 * @param midCd the midCd to set
	 */
	public void setMidCd(String midCd) {
		this.midCd = midCd;
		
	}

	/**
	 * @param smlCd the smlCd to set
	 */
	public void setSmlCd(String smlCd) {
		this.smlCd = smlCd;
		
	}

	/**
	 * @param ignoreCd the ignoreCd to set
	 */
	public void setIgnoreCd(String ignoreCd) {
		this.ignoreCd = ignoreCd;
		
	}

	/**
	 * @param cdTp the cdTp to set
	 */
	public void setCdTp(String cdTp) {
		this.cdTp = cdTp;
		
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<Map<String, Object>> items) {
		this.items = items;
	}
	
	/**
	 * @param items the strItems to set
	 */
	public void setStrItems(String strItems) {
		this.strItems = strItems;
	}

	/**
	 * @param valCol the valCol to set
	 */
	public void setValCol(String valCol) {
		this.valCol = valCol;
	}

	/**
	 * @param txtCol the txtCol to set
	 */
	public void setTxtCol(String txtCol) {
		this.txtCol = txtCol;
	}

	/**
	 * @param all the all to set
	 */
	public void setAll(boolean all) {
		this.all = all;
	}

	/**
	 * @param select the select to set
	 */
	public void setSelect(boolean select) {
		this.select = select;
	}

	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(String selected) {
		this.selected = selected;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @param relationItem the relationItem to set
	 */
	public void setRelationItem(String relationItem) {
		this.relationItem = relationItem;
	}

	/**
	 * @param relationItemCol the relationItemCol to set
	 */
	public void setRelationItemCol(String relationItemCol) {
		this.relationItemCol = relationItemCol;
	}

	/**
	 * @param subRelationItem the subRelationItem to set
	 */
	public void setSubRelationItem(String subRelationItem) {
		this.subRelationItem = subRelationItem;
	}
	
	/**
	 * @param subRelationItem the subRelationItem to set
	 */
	public void setSubRelationItemCol(String subRelationItemCol) {
		this.subRelationItemCol = subRelationItemCol;
	}

	/**
	 * @param setOnchange the setOnchange to set
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	/**
	 * @param setOnchange the setOnchange to set
	 */
	public void setIsDupOption(boolean isDupOption) {
		this.isDupOption = isDupOption;
	}

	/**
	 * Process the start tag.
	 *
	 * @return int SKIP_BODY
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doStartTagInternal() throws JspException {

		try{
			
			if(items == null){
				items = new ArrayList<Map<String, Object>>();
				if(StringUtils.isNotEmpty(lrgCd) || StringUtils.isNotEmpty(cdTp)){
					//공통코드 취득
					COService coService = getRequestContext().getWebApplicationContext().getBean(COService.class);
					Map<String, Object> parm = new HashMap<String, Object>();
					parm.put("lrgCd", lrgCd);
					parm.put("lrgCd", lrgCd);
					parm.put("midCd", midCd);
					parm.put("smlCd", smlCd);
					parm.put("cdTp", cdTp);
					parm.put("useYn", "Y");
					parm.put("ignoreCd", ignoreCd);
					items = coService.selectCodeList(parm);
				}else if(StringUtils.isNotEmpty(strItems)){
					String[] options = strItems.split(";");
					String[] option = null;
					Map<String, Object> bean = null;
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
			
			//소스 생성
			StringBuffer output = new StringBuffer();
			output.append("<script type='text/javascript' language='javascript'>	\n");
			output.append("//<!--	\n");
			output.append("var arr_hid_" + id + " = " + JSONArray.fromObject(items) + ";	\n");
			if(StringUtils.isNotEmpty(relationItem)){
				output.append("var selecttagrelation_" + relationItem + " = null;	\n");
			}
			output.append("$(document).ready(function(){	\n");
//			if(StringUtils.isNotEmpty(onchange)){
//				output.append("		$('#" + id + "').on('change', " + onchange + ");	\n");
//			}
			if(StringUtils.isNotEmpty(relationItem)){
				output.append("		selecttagrelation_" + relationItem + " = function(){	\n");
				output.append("			$('#" + id + " option').remove();	\n");
				if(all && !multiple){
					output.append("			$('#" + id + "').append(\"<option value='' selected>전체</option>\");	\n");	
				}
				//선택 옵션 생성 여부
				if(select && !multiple){
					output.append("			$('#" + id + "').append(\"<option value='' selected>선택</option>\");	\n");	
				}
				output.append("			for(var " + id + "_i = 0; " + id + "_i < arr_hid_" + id + ".length; " + id + "_i++){	\n");
				output.append("				if($('#" + relationItem + " option:selected').text() == 'All'){	\n");
				output.append("					$('#" + id + "').append(\"<option value='\" + arr_hid_" + id + "[" + id + "_i]." + valCol + " + \"'>\" + arr_hid_" + id + "[" + id + "_i]." + txtCol + " + \"</option>\");	\n");	
				output.append("					continue;	\n");
				output.append("				}	\n");
				if(StringUtils.isNotEmpty(subRelationItem)){
					if(multiple){
						output.append("				if($('#" + relationItem + "').multipleSelect('getSelects').indexOf(arr_hid_" + id + "[" + id + "_i]." + (StringUtils.isEmpty(relationItemCol) ? relationItem : relationItemCol) + ") >= 0 && $('#" + subRelationItem + "').multipleSelect('getSelects').indexOf(arr_hid_" + id + "[" + id + "_i]." + (StringUtils.isEmpty(subRelationItemCol) ? subRelationItem : subRelationItemCol) + ") >= 0){	\n");
					}else{
						output.append("				if($('#" + relationItem + "').val() == arr_hid_" + id + "[" + id + "_i]." + (StringUtils.isEmpty(relationItemCol) ? relationItem : relationItemCol) + " && $('#" + subRelationItem + "').val() ==  arr_hid_" + id + "[" + id + "_i]." + (StringUtils.isEmpty(subRelationItemCol) ? subRelationItem : subRelationItemCol) + "){	\n");
					}
				}else{
					if(multiple){
						output.append("				if($('#" + relationItem + "').multipleSelect('getSelects').indexOf(arr_hid_" + id + "[" + id + "_i]." + (StringUtils.isEmpty(relationItemCol) ? relationItem : relationItemCol) + ") >= 0){	\n");
					}else{
						output.append("				if($('#" + relationItem + "').val() == arr_hid_" + id + "[" + id + "_i]." + (StringUtils.isEmpty(relationItemCol) ? relationItem : relationItemCol) + "){	\n");
					}
				}
				output.append("					$('#" + id + "').append(\"<option value='\" + arr_hid_" + id + "[" + id + "_i]." + valCol + " + \"'>\" + arr_hid_" + id + "[" + id + "_i]." + txtCol + " + \"</option>\");	\n");	
				output.append("				}		\n");
				output.append("			}	\n");
				output.append("				\n");
				// 중복허용안될경우
				if(!isDupOption){
					output.append("			gfnDelDupOption('" + id + "');	\n");
				}
				if(multiple){
					// 다중 선택
					output.append("	$('#" + id + "').multipleSelect({		\n");
					output.append("		multiple: true,		\n");
					output.append("		multipleWidth: 120,		\n");
					output.append("		width: '100%',	\n");
					output.append("		single: false,	\n");	
					output.append("		minimumCountSelected: 7,	\n");	
					output.append("		selectAllText: 'Select All',	\n");	
					output.append("		countSelected: false,	\n");	
					output.append("		ellipsis: true,	\n");	
					if(StringUtils.isNotEmpty(title)){
						output.append("		placeholder: '" + title + "',	\n");	
					}
					output.append("	});	\n");
				}
				output.append("			$('#" + id + "').trigger('change');	\n");
				output.append("		};	\n");
				output.append("		$('#" + relationItem + "').on('change', selecttagrelation_" + relationItem + ");	\n");
				output.append("		selecttagrelation_" + relationItem + "();	\n");
			}else{
				if(multiple){
					// 다중 선택
					output.append("	$('#" + id + "').multipleSelect({		\n");
					output.append("		multiple: true,		\n");
					output.append("		multipleWidth: 120,		\n");
					output.append("		width: '100%',	\n");
					output.append("		single: false,	\n");	
					output.append("		minimumCountSelected: 7,	\n");	
					output.append("		selectAllText: 'Select All',	\n");	
					output.append("		countSelected: false,	\n");	
					output.append("		ellipsis: true,	\n");	
					if(StringUtils.isNotEmpty(title)){
						output.append("		placeholder: '" + title + "',	\n");
					}
					output.append("	});	\n");
				}
			}
			output.append("	});	\n");
			output.append("//-->	\n");
			output.append("</script>	\n");
			
			output.append("<select id='" + id + "' name='" + id + "' title='" + title + "'" + (multiple ? " multiple='multiple'" : "") + (StringUtils.isNotEmpty(style) ? " style='" + style + "'" : "") + (StringUtils.isNotEmpty(onchange) ? " onchange='" + onchange + "'" : "") + ">\n");
			//전체 옵션 생성 여부
			if(all && !multiple){
				output.append("<option value='' " + (StringUtils.isEmpty(selected) ? "selected='selected'" : "") + ">전체</option>\n");			
			}
			//선택 옵션 생성 여부
			if(select && !multiple){
				output.append("<option value='' " + (StringUtils.isEmpty(selected) ? "selected='selected'" : "") + ">선택</option>\n");
			}
			//옵션 생성
			Map<String, Object> option = null;
			for(int i = 0; i < items.size(); i++){
				option = items.get(i);
				output.append("<option value='" + option.get(valCol) + "' " + ((option.get(valCol).equals(selected)) ? "selected='selected'" : "") + ">" + option.get(txtCol) + "</option>\n");			
			}
			output.append("</select>");
			JspWriter out = pageContext.getOut();
			out.print(output.toString());
		}catch(Exception e){
			logger.error("select tag error", e);
			return SKIP_BODY;  // Nothing to output
		}

		// Continue processing this page
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		id = null;
		lrgCd = null;
		midCd = null;
		smlCd = null;
		ignoreCd = null;
		items = null;
		strItems = null;
		valCol = "SML_CD";
		txtCol = "CD_NM";
		all = false;
		select = false;
		multiple = false;
		selected = null;
		title = null;
		style = null;
		relationItem = null;
		relationItemCol = null;
		subRelationItem = null;
		subRelationItemCol = null;
		onchange = null;
		isDupOption = true;
		
		return EVAL_PAGE;
	}
}