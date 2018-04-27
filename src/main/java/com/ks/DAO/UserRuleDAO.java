package com.ks.DAO;

import com.ks.bean.UserRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class UserRuleDAO {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;


	public List<UserRule> getList() {
		String sql = "SELECT id, hazardLevel, description, tag, signature FROM UserRules ORDER BY hazardLevel DESC";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<UserRule>(UserRule.class));
	}

	public UserRule getUserRule(int id){
		String sql = "SELECT id, hazardLevel, description, tag, signature FROM UserRules WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		return jdbcTemplate.queryForObject(sql,paramMap, new BeanPropertyRowMapper<UserRule>(UserRule.class));
	}


	public int addOrUpdateUserRule(UserRule userRule){
		if(userRule.getId() != null){
			return updateUserRule(userRule);
		} else {
			return addUserRule(userRule);
		}
	}

	private int updateUserRule(UserRule userRule){
		String sql = "UPDATE UserRules SET hazardLevel=:hazardLevel,description=:description, tag=:tag,signature=:signature WHERE id=:id";
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(userRule));

	}

	private int addUserRule(UserRule userRule){
		String sql = "INSERT INTO UserRules (id,hazardLevel, description, tag,signature) values (:id,"
				+ ":hazardLevel, :description, :tag,:signature)";

		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(userRule));
	}

	public Boolean deleteUserRule(int id){
		String sql = "DELETE FROM UserRules WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		int res = jdbcTemplate.update(sql,paramMap);
		return res>=1 ? true : false;
	}


}
