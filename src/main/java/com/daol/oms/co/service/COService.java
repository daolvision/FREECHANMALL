package com.daol.oms.co.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import com.daol.oms.core.dao.CommonDAO;

/**
* 공통 Service
* @author 김경보
* @since 2016. 6. 14.
* @version 0.1
* @see
*/
@Service
public class COService {

	private static final Logger logger = LoggerFactory.getLogger(COService.class);
	
	@Autowired
	LocaleResolver localeResolver;
	
	@Autowired
	CommonDAO dao;
	
	public COService(){
		logger.info("{} class loaded...", this.getClass().getName());
	}
	
	/**
	 * 공통코드 목록 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectCodeList(Map<String, Object> param) throws Exception{
		return dao.selectList("co.selectCodeList", param);
	}

	/**
	 * 환경변수 취득
	 * @param propNm
	 * @return
	 * @throws Exception
	 */
	public String getConfigProp(String propNm) throws Exception{
		return getConfigProp().getProperty(propNm);
	}

	/**
	 * 환경변수 취득
	 * @return
	 * @throws Exception
	 */
	public Properties getConfigProp() throws Exception{
		Properties confProp = new Properties();
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> confList = dao.selectList("sm.selectConfigList", param);
		for(Map<String, Object> confInfo : confList){
			confProp.setProperty((String)confInfo.get("CONF_PROP_NM"), (String)confInfo.get("CONF_PROP"));
		}
		return confProp;
	}
}