package com.ceit.vic.platform.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ceit.vic.platform.models.Person_Role;
public interface Person_RoleService {
	@Transactional
	public List<Person_Role> getPersonRoleByPersonId(int personId);
}
