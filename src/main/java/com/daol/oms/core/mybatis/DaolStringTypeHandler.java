package com.daol.oms.core.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class DaolStringTypeHandler extends BaseTypeHandler<String>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {

		if(StringUtils.isEmpty(parameter)){
			if(jdbcType == JdbcType.NUMERIC){
				parameter = null;
			}
		}
		ps.setString(i, parameter);
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		
		if(rs.getString(columnName) == null){
			return "";
		}
		
		return rs.getString(columnName);
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		
		if(rs.getString(columnIndex) == null){
			return "";
		}

		return rs.getString(columnIndex);
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		
		if(cs.getString(columnIndex) == null){
			return "";
		}

		return cs.getString(columnIndex);
	}

}