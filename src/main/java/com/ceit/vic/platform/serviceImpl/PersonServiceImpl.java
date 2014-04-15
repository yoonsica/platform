package com.ceit.vic.platform.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.DepPersonDao;
import com.ceit.vic.platform.dao.DepartmentDao;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.PersonDTO;
import com.ceit.vic.platform.service.PersonService;
@Service
public class PersonServiceImpl implements PersonService {
	@Autowired
	DepPersonDao depPersonDao;
	@Autowired
	DepartmentDao departmentDao;
	@Override
	public List<PersonDTO> getPersonsByDepId(int depId) {
		List<Person> personList = depPersonDao.getPersonsByDepId(depId);
		List<PersonDTO> dtoList = new ArrayList<PersonDTO>();
		String departmentName = departmentDao.getDepartmentById(depId).getName();
		for (Person person : personList) {
			PersonDTO dto = new PersonDTO();
			dto.setId(person.getId());
			dto.setName(person.getName());
			dto.setCode(person.getCode());
			dto.setDepartmentName(departmentName);
			dto.setMemo(person.getMemo());
			dto.setState(person.getState().equals("0")?"启用":"停用");
			dtoList.add(dto);
		}
		return dtoList;
	}

}
