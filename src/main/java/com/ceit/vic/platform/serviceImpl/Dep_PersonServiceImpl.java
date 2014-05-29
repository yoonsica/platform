package com.ceit.vic.platform.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.Dep_PersonDao;
import com.ceit.vic.platform.models.Dep_Person;
import com.ceit.vic.platform.service.Dep_PersonService;
@Service
public class Dep_PersonServiceImpl implements Dep_PersonService {
	@Autowired
	Dep_PersonDao dep_PersonDao;

	@Override
	public List<Dep_Person> getByPersonId(int id, boolean mainDep) {
		return dep_PersonDao.getByPersonId(id, mainDep);
	}

}
