package com.daol.oms.core.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DaolImageDownView extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		File downloadFile = (File)model.get("downloadImage");
		
		//응답 헤더의 설정
		response.setContentType("application/image; utf-8");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		FileInputStream fi = null;
		try{
			fi = new FileInputStream(downloadFile);
		}catch(FileNotFoundException fe){
			downloadFile = new File(request.getSession().getServletContext().getRealPath("/images/no_image.jpg"));
			fi = new FileInputStream(downloadFile);
		}finally{
			if(fi != null){
				FileCopyUtils.copy(fi, response.getOutputStream());
			}
			if(fi != null){
				fi.close();
			}
		}
	}
}
