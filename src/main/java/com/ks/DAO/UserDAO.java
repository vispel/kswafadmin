package com.ks.DAO;

import com.ks.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class UserDAO {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public Boolean addOrUpdateUser(User user){
		if(user.getId() != null){
			return updateUser(user);
		} else {
			return addUser(user);
		}
	}

	private Boolean updateUser(User user){
		String sql = "UPDATE users SET login=:login,password=:password,role=:password WHERE id=:id";
		int res = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
		return res>=1 ? true : false;
	}

	private Boolean addUser(User user){
		String sql = "INSERT INTO users (id,login,password,role) values (:id,"
				+ ":login,:password,:role)";

		int res = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
		return res>=1 ? true : false;
	}

	public Boolean deleteUser(User user){
		String sql = "DELETE FROM users WHERE id=:id";
		int res = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
		return res>=1 ? true : false;
	}


}
