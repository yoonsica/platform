package com.ceit.vic.platform.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceit.vic.platform.dao.DepPersonDao;
import com.ceit.vic.platform.dao.Dep_PersonDao;
import com.ceit.vic.platform.dao.DepartmentDao;
import com.ceit.vic.platform.dao.IDPROVIDERDao;
import com.ceit.vic.platform.dao.PersonDao;
import com.ceit.vic.platform.dao.Person_RoleDao;
import com.ceit.vic.platform.models.Dep_Person;
import com.ceit.vic.platform.models.Department;
import com.ceit.vic.platform.models.Person;
import com.ceit.vic.platform.models.PersonDTO;
import com.ceit.vic.platform.models.Person_Role;
import com.ceit.vic.platform.service.PersonService;
@Service
public class PersonServiceImpl implements PersonService {
	@Autowired
	DepPersonDao depPersonDao;
	@Autowired
	DepartmentDao departmentDao;
	@Autowired
	IDPROVIDERDao idproviderDao;
	@Autowired
	PersonDao personDao;
	@Autowired
	Person_RoleDao person_RoleDao;
	@Autowired
	Dep_PersonDao dep_PersonDao;
	@Override
	public List<PersonDTO> getPersonsByDepId(int depId,int page,int rows) {
		int firstResult,maxResults;
		firstResult = (page-1)*rows;
		maxResults = rows;
		List<Person> personList = depPersonDao.getPersonsByDepId(depId,firstResult,maxResults);
		List<PersonDTO> dtoList = new ArrayList<PersonDTO>();
		//Department department = departmentDao.getDepartmentById(depId);
		String departmentName = departmentDao.getDepPathNameById(depId);
		
		/*if (null!=department.getMemo()) {
			departmentName = department.getMemo();
		}else {
			if (null!=department.getName()) {
				departmentName = department.getName();
			}else {
				departmentName = "未知";
			}
		}*/
		for (Person person : personList) {
			PersonDTO dto = new PersonDTO();
			if (null==person.getSex()) {
				dto.setSex("未知");
			}else {
				dto.setSex(person.getSex().equals("1")?"男":"女");
			}
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
	@Override
	public int getTotalPersonsByDepId(int depId) {
		return depPersonDao.getTotalPersonByDepId(depId);
	}
	@Override
	public int add(Person person, int roleId, int depId) {
		//添加人员
		int personId = idproviderDao.getCurrentId("PERSON");
		person.setId(personId);
		personDao.add(person);
		idproviderDao.add("PERSON");
		//添加人员角色关联
		int personRoleId = idproviderDao.getCurrentId("PERSONROLE");
		Person_Role person_Role = new Person_Role();
		person_Role.setPersonId(personId);
		person_Role.setRoleId(roleId);
		person_Role.setId(personRoleId);
		person_RoleDao.add(person_Role);
		idproviderDao.add("PERSONROLE");
		
		//添加人员部门关联
		int depPersonId = idproviderDao.getCurrentId("DEP_PERSON");
		Dep_Person dep_Person = new Dep_Person();
		dep_Person.setId(depPersonId);
		dep_Person.setPersonId(person.getId());
		dep_Person.setDepId(depId);
		dep_Person.setMainDep(1);
		dep_PersonDao.add(dep_Person);
		idproviderDao.add("DEP_PERSON");
		return personId;
	}
	@Override
	public String up(int depId,int personId) {
		Person person1 = personDao.getPersonsById(personId);
		Person person2 = personDao.getPersonToDown(depId,person1.getDispIndex());
		if (person2==null) {
			return "已经是第一个了，无法上调！";
		}
		int tmp = person2.getDispIndex();
		person2.setDispIndex(person1.getDispIndex());
		person1.setDispIndex(tmp);
		personDao.update(person1);
		personDao.update(person2);
		return "上调成功！";
	}
	@Override
	public String down(int depId, int personId) {
		Person person1 = personDao.getPersonsById(personId);
		Person person2 = personDao.getPersonToUp(depId,person1.getDispIndex());
		if (person2==null) {
			return "已经是最后一个了，无法下调！";
		}
		int tmp = person2.getDispIndex();
		person2.setDispIndex(person1.getDispIndex());
		person1.setDispIndex(tmp);
		personDao.update(person1);
		personDao.update(person2);
		return "下调成功！";
	}
	@Override
	public Person getPersonById(int personId) {
		return personDao.getPersonsById(personId);
	}
	@Override
	public void update(Person person) {
		personDao.update(person);
	}
	@Override
	public void updateDepartment(int id, int depId) {
		Dep_Person dep_Person =  depPersonDao.getByPersonId(id).get(0);
		dep_Person.setDepId(depId);
		dep_PersonDao.update(dep_Person);
	}
	@Override
	public void delete(int[] idArray) {
		personDao.multDelete(idArray);
		
	}
	@Override
	public void deleteDepPerson(int depId, int[] idArray) {
		dep_PersonDao.deletePerson(depId,idArray);
		
	}
	@Override
	public void deletePersonRole(int[] idArray) {
		person_RoleDao.deleteByPersonIds(idArray);
	}
	@Override
	public List<Person> getPersonsByIds(int[] idArray) {
		List<Integer> list = new ArrayList<Integer>();
		for (int id : idArray) {
			list.add(id);
		}
		return personDao.getPersonsByIds(list);
	}
	@Override
	public void resetPassword(int[] idArray) {
		personDao.resetPassword(idArray);
		
	}
	@Override
	public List<Person> getPersonsByCodeName(String code) {
		return personDao.getPersonsByCode(code);
	}

}
