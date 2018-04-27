package com.ks.DAO;

import com.ks.bean.RegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RegisteredEventsDAO {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public List<RegisteredEvent> getList() {
		String sql = "SELECT * FROM RegisteredEvents ORDER BY date DESC";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<RegisteredEvent>(RegisteredEvent.class));
	}

	public int addEvent(RegisteredEvent registeredEvent){
		String sql = "INSERT INTO RegisteredEvents (id,parameter, [value], ipAddress,date) values (:id,"
				+ ":parameter, :[value], :ipAddress,:date)";

		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(registeredEvent));
	}

	public Boolean deleteEvent(int id){
		String sql = "DELETE FROM RegisteredEvents WHERE id=:id";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		int res = jdbcTemplate.update(sql,paramMap);
		return res>=1 ? true : false;
	}
}
