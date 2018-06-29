package com.daol.oms.core.view;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.spring.web.servlet.view.JsonView;

public class DaolJsonView extends JsonView{

	@Override
	@SuppressWarnings("unchecked")
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 요청 파라미터 반환
		Enumeration<String> e = request.getParameterNames();
		String pName = null;
		while(e.hasMoreElements()){
			pName = e.nextElement();
			model.put(pName, request.getParameter(pName));
		}
		
		// 결과코드 저장
		if(!model.containsKey("rtnCode")){
			model.put("rtnCode", "0");
		}
		
		super.renderMergedOutputModel(model, request, response);
	}
}
