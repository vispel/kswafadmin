package com.ks.service;

import com.ks.DAO.UserRuleDAO;
import com.ks.bean.UserRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRuleService {

	@Autowired
	UserRuleDAO userRuleDAO;


	public List<UserRule> getUserRuleList(){
		return userRuleDAO.getList();
	}


	public UserRule getUserRule(int id){
		return userRuleDAO.getUserRule(id);
	}

	public int addOrUpdateUserRule(UserRule userRule){
		return userRuleDAO.addOrUpdateUserRule(userRule);
	}

	public Boolean deleteUserRule(int id){
		return userRuleDAO.deleteUserRule(id);
	}
}
