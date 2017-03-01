package com.locus.edu.staff.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.locus.edu.base.dao.BaseDao;
import com.locus.edu.base.dao.DaoSupport;
import com.locus.edu.department.model.DepSubjectStaffMap;
import com.locus.edu.util.TypeUtils;

@Repository
public class StaffSubjectMappingDao<T> extends DaoSupport{
	
	private static final Logger LOGGER = Logger.getLogger(StaffSubjectMappingDao.class);
	
	@Autowired
	BaseDao<T> baseDao;

	@SuppressWarnings("unchecked")
	public DepSubjectStaffMap saveSubjectMappingDetails(DepSubjectStaffMap depSubjectStaffMap) {
		
		baseDao.save((T) depSubjectStaffMap);
		return depSubjectStaffMap;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getStaffSubMappingDetails(long id) {
		
		System.out.println("daoid-----"+id);
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DepSubjectStaffMap.class,"depSubjectStaffMap");
		detachedCriteria.add(Restrictions.eq("depSubjectStaffMap.dssmId", id));
		
		detachedCriteria.setFetchMode("depSubjectStaffMap.camInsttMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("depSubjectStaffMap.camInsttMaster", "camInsttMaster");
		
		detachedCriteria.setFetchMode("depSubjectStaffMap.depSubjectMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("depSubjectStaffMap.depSubjectMaster", "depSubjectMaster");
		
		detachedCriteria.setFetchMode("depSubjectStaffMap.depStandardMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("depSubjectStaffMap.depStandardMaster", "depStandardMaster");
		
		detachedCriteria.setFetchMode("depSubjectStaffMap.staPersonal",FetchMode.JOIN);
		detachedCriteria.createAlias("depSubjectStaffMap.staPersonal", "staPersonal");
		
		ProjectionList list = Projections.projectionList();
		
		list.add(Projections.property("depSubjectStaffMap.dssmId"));
		list.add(Projections.property("camInsttMaster.insttId"));
		list.add(Projections.property("depSubjectMaster.subjectId"));
		list.add(Projections.property("depStandardMaster.standardId"));
		list.add(Projections.property("staPersonal.appId"));
		list.add(Projections.property("depSubjectStaffMap.academicYr"));
		
		detachedCriteria.setProjection(list);
		
		List<Object[]> object = ((List<Object[]>)detachedCriteria.getExecutableCriteria(getSession()).list());
		
		if(object != null && !object.isEmpty()){
			for(Object[] objs : object){
				result.put("dssmId", TypeUtils.toStringValue(objs[0]));
				result.put("insttId", TypeUtils.toStringValue(objs[1]));
				result.put("subjectId", TypeUtils.toStringValue(objs[2]));
				result.put("standardId", TypeUtils.toStringValue(objs[3]));
				result.put("appId", TypeUtils.toStringValue(objs[4]));
				result.put("academicYr", TypeUtils.toStringValue(objs[5]));
				result.put("Message", "Success");
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> deleteStaffSubMappingDetails(long id) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DepSubjectStaffMap.class,"depSubjectStaffMap");
		detachedCriteria.add(Restrictions.eq("depSubjectStaffMap.dssmId", id));
		
		detachedCriteria.setFetchMode("depSubjectStaffMap.camInsttMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("depSubjectStaffMap.camInsttMaster", "camInsttMaster");
		
		detachedCriteria.setFetchMode("depSubjectStaffMap.depSubjectMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("depSubjectStaffMap.depSubjectMaster", "depSubjectMaster");
		
		detachedCriteria.setFetchMode("depSubjectStaffMap.depStandardMaster",FetchMode.JOIN);
		detachedCriteria.createAlias("depSubjectStaffMap.depStandardMaster", "depStandardMaster");
		
		detachedCriteria.setFetchMode("depSubjectStaffMap.staPersonal",FetchMode.JOIN);
		detachedCriteria.createAlias("depSubjectStaffMap.staPersonal", "staPersonal");
		
		ProjectionList list = Projections.projectionList();
		
		list.add(Projections.property("depSubjectStaffMap.dssmId"));
		list.add(Projections.property("camInsttMaster.insttId"));
		list.add(Projections.property("depSubjectMaster.subjectId"));
		list.add(Projections.property("depStandardMaster.standardId"));
		list.add(Projections.property("staPersonal.appId"));
		list.add(Projections.property("depSubjectStaffMap.academicYr"));
		
		detachedCriteria.setProjection(list);
		
		List<Object[]> object = ((List<Object[]>)detachedCriteria.getExecutableCriteria(getSession()).list());
		
		if(object != null && !object.isEmpty()){
			for(Object[] objs : object){
				
				result.put("dssmId", TypeUtils.toStringValue(objs[0]));
				result.put("insttId", TypeUtils.toStringValue(objs[1]));
				result.put("subjectId", TypeUtils.toStringValue(objs[2]));
				result.put("standardId", TypeUtils.toStringValue(objs[3]));
				result.put("appId", TypeUtils.toStringValue(objs[4]));
				result.put("academicYr", TypeUtils.toStringValue(objs[5]));
				result.remove(objs);
				result.put("Message", "Success");
			}
		}
		return result;
	}

	@SuppressWarnings("unused")
	private boolean deleteById(Class<?> type, Serializable id) {
	    Object persistentInstance = getSession().load(type, id);
	    if (persistentInstance != null) {
	        getSession().delete(persistentInstance);
	        return true;
	    }
	    return false;
	}
	
	@SuppressWarnings("unchecked")
   public List<DepSubjectStaffMap> fetchStaffSubMappingDetails() {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DepSubjectStaffMap.class,"depSubjectStaffMap");
		List<DepSubjectStaffMap> depSubjectStaffMap = ((List<DepSubjectStaffMap>) detachedCriteria
				.getExecutableCriteria(getSession()).list());
		if (depSubjectStaffMap != null && !depSubjectStaffMap.isEmpty()) {
			return (List<DepSubjectStaffMap>) depSubjectStaffMap;
		} else {
			LOGGER.info("User not found !");
			return null;

		}
	}

	@SuppressWarnings("unchecked")
	public DepSubjectStaffMap updateSubjectMappingDetails(DepSubjectStaffMap depSubjectStaffMap) {
		
		baseDao.update((T) depSubjectStaffMap);
		return depSubjectStaffMap;
	}

}
