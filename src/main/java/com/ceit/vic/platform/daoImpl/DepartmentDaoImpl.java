package com.ceit.vic.platform.daoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceit.vic.platform.dao.DepartmentDao;
import com.ceit.vic.platform.models.DepDTO;
import com.ceit.vic.platform.models.Department;
import com.ceit.vic.platform.models.Role;
@Repository
public class DepartmentDaoImpl implements DepartmentDao{
	@Autowired
	private SessionFactory sf;
	@Override
	public List<Department> getAllDepartments() {
		Query query = null;
		try {
			query = sf.getCurrentSession().createQuery("from Department order by parentId,dispindex");
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Department> list=new ArrayList<Department>();
		try {
			list = query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void insertDepartment(Department department) {
		sf.getCurrentSession().save(department);
	}

	@Override
	public List<Object[]> getDepartmentTreeById(int id) {
		Query query = null;
		try {
			query = sf.getCurrentSession().createSQLQuery("select * from DEPARTMENT t start with t.id=4 connect by t.parentid = prior t.id order by t.parentid,t.dispIndex");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		List<Object[]> list=new ArrayList<Object[]>();
		try {
			list = query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Department getDepartmentById(int id) {
		Query query = null;
		Department department = null;
		try {
			query = sf.getCurrentSession().createQuery("from Department t where t.id="+id);
			department = (Department) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return department;
	}

	@Override
	public void delete(int id) {
		sf.getCurrentSession().delete(getDepartmentById(id));
	}

	@Override
	public void update(Department department) {
		try {
			sf.getCurrentSession().update(department);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Department getDepartmentToDown(int parentId, int dispindex) {
		StringBuffer sb = new StringBuffer("from Department t where t.parentId=");
		sb.append(parentId).append(" and t.dispindex<").append(dispindex)
		.append(" order by t.dispindex desc");
		Query query = sf.getCurrentSession().createQuery(sb.toString());
		return (Department) query.list().get(0);
	}

	@Override
	public Department getDepartmentToUp(int parentId, int dispindex) {
		try {
			StringBuffer sb = new StringBuffer("from Department t where t.parentId=");
			sb.append(parentId).append(" and t.dispindex>").append(dispindex)
			.append(" order by t.dispindex");
			Query query = sf.getCurrentSession().createQuery(sb.toString());
			return (Department) query.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Department> getDepartmentsByIds(List<Integer> idList) {
		if (idList==null||idList.size()==0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("from Department t where t.id in(");
		for (int i = 0; i < idList.size()-1; i++) {
			sb.append(idList.get(i)).append(",");
		}
		sb.append(idList.get(idList.size()-1))
		.append(") order by t.dispindex");
		Query query;
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return query.list();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<Department> getByParamMap(Map<String, Object> paraMap) {
		Query query;
		StringBuffer sb = new StringBuffer("from Department t ");
		if (paraMap!=null) {
			sb.append("where ");
			for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
				if(entry.getValue() instanceof List){
					sb.append("t.").append(entry.getKey()).append("in(");
					List<Object> list = new ArrayList<Object>();
					if (null!=list) {
						for (Object object : list) {
							sb.append("'").append(object.toString()).append("',");
						}
						sb=new StringBuffer(sb.substring(0,sb.length()-1));
						sb.append(") and ");
					}
					
				}else {
					sb.append("t.").append(entry.getKey()).append("=").append(entry.getValue().toString()).append(" and ");
				}
			}
		}
		try {
			query = sf.getCurrentSession().createQuery(sb.substring(0, sb.length()-4).toString());
			return query.list();
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public Department getDepartmentByPersonId(int personId) {
		Query query = null;
		Department department = null;
		try {
			query = sf.getCurrentSession().createQuery("select t from Department t, Dep_Person t1 where t.id = t1.depId and t1.personId="+personId);
			department = (Department) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return department;
	}

	@Override
	public int getDepartmentCountByResourceId(int id) {
		Query query = null;
		StringBuffer sb = new StringBuffer("select count(*) from ResAccess t where t.accessType = 2 and t.resId=" + id);
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			return Integer.parseInt(String.valueOf(query.uniqueResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Department> getDepartmentsByResourceId(int id, int pageIndex, int pageSize) {
		Query query = null;
		StringBuffer sb = new StringBuffer("select t1 from ResAccess t, Department t1 where t1.id = t.accessId and t.resId=" + id);
		try {
			query = sf.getCurrentSession().createQuery(sb.toString());
			query.setFirstResult((pageIndex - 1) * pageSize);
			query.setMaxResults(pageSize);
			/*List<Object[]> roleObjs = query.list();
			List<Role> roles = new ArrayList<Role>();
			for(Object[] roleObj : roleObjs) {
				Role r = new Role();
				r.setId(Integer.parseInt(String.valueOf(roleObj[0])));
				r.setName((String) roleObj[1]);
				r.setMemo((String) roleObj[2]);
				
				roles.add(r);
			}
			return roles;*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return query.list();
	}
		
	public String getDepPathNameById(int depId) {
		try {
			StringBuffer sb = new StringBuffer("select * from DEPARTMENT t where t.parentid!=0 start with t.id=");
			sb.append(depId).append(" connect by t.id = prior t.parentid");
			Query query = sf.getCurrentSession().createSQLQuery(sb.toString());
			List<Object[]> list = query.list();
			if (null!=list&&list.size()>0) {
				StringBuffer pathName=new StringBuffer();
				for (int i = list.size()-1; i >=0; i--) {
					pathName.append(list.get(i)[3]).append("-");
				}
				return pathName.substring(0,pathName.length()-1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Department> getDepartmentByIds(List<Integer> depIds) {
		if (null!=depIds&&depIds.size()>0) {
			StringBuffer sb = new StringBuffer();
			sb.append("from Department t ");
			sb.append("where t.id in(");
			for (Integer id : depIds) {
				sb.append(id).append(",");
			}
			sb = new StringBuffer(sb.substring(0, sb.length()-1));
			sb.append(") order by t.dispindex");
			Query query;
			try {
				query = sf.getCurrentSession().createQuery(sb.toString());
				return query.list();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}
}
