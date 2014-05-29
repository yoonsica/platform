package com.ceit.vic.platform.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.Person_RoleDao;
import com.ceit.vic.platform.models.Person_Role;
import com.ceit.vic.platform.service.Person_RoleService;
@Service
public class Person_RoleServiceImpl implements Person_RoleService {
	@Autowired
	Person_RoleDao person_RoleDao;

	@Override
	public List<Person_Role> getPersonRoleByPersonId(int personId) {
		return person_RoleDao.getPersonRoleByPersonId(personId);
	}


}
