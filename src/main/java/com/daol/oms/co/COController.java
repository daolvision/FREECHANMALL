package com.daol.oms.co;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import com.daol.oms.co.service.COService;
import com.daol.oms.core.json.JsonDTO;
/**
* 공통 Controller
* @author 김경보
* @since 2018. 1. 10.
* @version 0.1
* @see
*/
@Controller
public class COController {
	
	private static final Logger logger = LoggerFactory.getLogger(COController.class);

	@Autowired
	LocaleResolver localeResolver;
	
	@Autowired
	COService coService;
	
	/** 암호화 KEY */
	@Value("${encrypt.key.string}")
	private String encKeyStr;
	
	public COController(){
		logger.info("{} class loaded...", this.getClass().getName());
	}

	/**
	 * 화면이동
	 * @param subsys
	 * @param src
	 * @return view name
	 * @throws Exception
	 */
	@RequestMapping(value = "/forword:{subsys}/{src}")	
	public String forword(@PathVariable String subsys, @PathVariable String src) throws Exception {
		return subsys + "/" + src;
	}
	
	/**
	 * 공통코드 조회
	 * @param dto
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/co/selectCodeList")
	public String selectCodeList(JsonDTO dto, Model model) throws Exception {
		
		// 파리미터 설정
		Map<String, Object> param = dto.getReqSessionData();
		
		// 응답정보 설정
		model.addAttribute("codeList", coService.selectCodeList(param));
		
		return "jsonView";
	}

	/**
	 * 파일 다운로드 보기
	 * @param dto
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/co/imageDownView")
	public String imageDownView(JsonDTO dto, Model model, HttpServletRequest request)  throws Exception {
		
		Map<String, Object> param = dto.getReqSessionData();
		
		if(!param.containsKey("fileUrl") || StringUtils.isEmpty((String)param.get("fileUrl"))){
			return "";
		}
		String imageRootPath = coService.getConfigProp("IMG_ROOT_PATH");
		
		File downloadImage = new File(imageRootPath + "/" + (String)param.get("fileUrl"));
		
		model.addAttribute("downloadImage", downloadImage);

		return "imageDownView";
	}
}