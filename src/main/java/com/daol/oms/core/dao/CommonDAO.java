package com.daol.oms.core.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * CommonDAO
 * </pre>
 * 
 * @since 2016. 06. 14.
 * @author 김경보
 */
@Repository
public class CommonDAO extends SqlSessionDaoSupport {

	@Value("${sys.prop.code}")
	private String _propCd;

	@Value("${sys.cmpx.code}")
	private String _cmpxCd;

	protected CommonDAO() {
		super();
	}

	@Resource(name = "sqlSession")
	public void setSqlSessionn(SqlSessionTemplate sqlSessionTemplate) {
		setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	/**
	 * 기본 파라미터 설정(프로퍼티/컴플렉스 추가)
	 * @param parameter
	 */
	@SuppressWarnings("unchecked")
	public void setBasicParam(Object parameter){
		if(parameter instanceof Map){
			Map<String, Object> paramMap = (Map<String, Object>)parameter;
			paramMap.put("_propCd", _propCd);
			paramMap.put("_cmpxCd", _cmpxCd);
		}
	}
	
	/**
	 * 리스트 조회 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @return List 형태의 조회결과
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectList(String sqlId) throws Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();
		setBasicParam(parameter);
//		return getSqlSession().selectList(sqlId);
		return getSqlSession().selectList(sqlId, parameter);
	}

	/**
	 * 리스트 조회 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @param parameter
	 *            SQL mapping 입력 데이터를 세팅한 파라메터
	 * @return List 형태의 조회결과
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectList(String sqlId, Object parameter) throws Exception {
		setBasicParam(parameter);
		return getSqlSession().selectList(sqlId, parameter);
	}

	/**
	 * 단건 조회 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @return resultType에서 지정한 단일 결과 객체
	 * @throws Exception
	 */
	public Map<String, Object> selectOne(String sqlId) throws Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();
		setBasicParam(parameter);
//		return getSqlSession().selectOne(sqlId);
		return getSqlSession().selectOne(sqlId, parameter);
	}

	/**
	 * 단건 조회 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @param parameter
	 *            SQL mapping 입력 데이터를 세팅한 파라메터
	 * @return resultType에서 지정한 단일 결과 객체
	 * @throws Exception
	 */
	public Map<String, Object> selectOne(String sqlId, Object parameter) throws Exception {
		setBasicParam(parameter);
		return getSqlSession().selectOne(sqlId, parameter);
	}

	/**
	 * 부분 범위 리스트 조회 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @param pageIndex
	 *            현재 페이지 번호
	 * @param pageSize
	 *            한 페이지 조회 수
	 * @return List 형태의 조회결과
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectListWithPaging(String sqlId, int pageIndex, int pageSize) throws Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();
		setBasicParam(parameter);
//		return getSqlSession().selectList(sqlId, new RowBounds((pageIndex - 1) * pageSize, pageSize));
		return getSqlSession().selectList(sqlId, parameter, new RowBounds((pageIndex - 1) * pageSize, pageSize));
	}

	/**
	 * 부분 범위 리스트 조회 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @param parameter
	 *            SQL mapping 입력 데이터를 세팅한 파라메터
	 * @param pageIndex
	 *            현재 페이지 번호
	 * @param pageSize
	 *            한 페이지 조회 수
	 * @return List 형태의 조회결과
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectListWithPaging(String sqlId, Object parameter, int pageIndex, int pageSize) throws Exception {
		setBasicParam(parameter);
		return getSqlSession().selectList(sqlId, parameter, new RowBounds((pageIndex - 1) * pageSize, pageSize));
	}

	/**
	 * 건수 조회 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @return long 데이터건수
	 * @throws Exception
	 */
	public long selectCnt(String sqlId) throws Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();
		return selectCnt(sqlId, parameter);
	}
	
	/**
	 * 건수 조회 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @param parameter
	 *            SQL mapping 입력 데이터를 세팅한 파라메터
	 * @return long 데이터건수
	 * @throws Exception
	 */
	public long selectCnt(String sqlId, Object parameter) throws Exception {
		setBasicParam(parameter);
		Map<String, Object> result = getSqlSession().selectOne(sqlId, parameter);
		if(result.containsKey("CNT")){
			return ((BigDecimal)result.get("CNT")).longValue();
		}
		return -1;
	}

	/**
	 * 입력 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @return insert 처리결과 count
	 * @throws Exception
	 */
	public int insert(String sqlId) throws Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();
		setBasicParam(parameter);
//		return getSqlSession().insert(sqlId);
		return getSqlSession().insert(sqlId, parameter);
	}

	/**
	 * 입력 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @param parameter
	 *            SQL mapping 입력 데이터를 세팅한 파라메터
	 * @return insert 처리결과 count
	 * @throws Exception
	 */
	public int insert(String sqlId, Object parameter) throws Exception {
		setBasicParam(parameter);
		return getSqlSession().insert(sqlId, parameter);
	}

	/**
	 * 수정 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @return update 처리결과 count
	 * @throws Exception
	 */
	public int update(String sqlId) throws Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();
		setBasicParam(parameter);
//		return getSqlSession().update(sqlId);
		return getSqlSession().update(sqlId, parameter);
	}

	/**
	 * 수정 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @param parameter
	 *            SQL mapping 입력 데이터를 세팅한 파라메터
	 * @return update 처리결과 count
	 * @throws Exception
	 */
	public int update(String sqlId, Object parameter) throws Exception {
		setBasicParam(parameter);
		return getSqlSession().update(sqlId, parameter);
	}

	/**
	 * 삭제 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @return delete 처리결과 count
	 * @throws Exception
	 */
	public int delete(String sqlId) throws Exception {
		Map<String, Object> parameter = new HashMap<String, Object>();
		setBasicParam(parameter);
//		return getSqlSession().delete(sqlId);
		return getSqlSession().delete(sqlId, parameter);
	}

	/**
	 * 삭제 처리 SQL mapping 을 실행한다.
	 * 
	 * @param sqlId
	 *            SQL mapping 쿼리 ID
	 * @param parameter
	 *            SQL mapping 입력 데이터를 세팅한 파라메터
	 * @return delete 처리결과 count
	 * @throws Exception
	 */
	public int delete(String sqlId, Object parameter) throws Exception {
		setBasicParam(parameter);
		return getSqlSession().delete(sqlId, parameter);
	}
}
