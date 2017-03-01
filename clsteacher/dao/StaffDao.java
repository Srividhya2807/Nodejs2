package com.locus.edu.staff.dao;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.locus.edu.base.dao.BaseDao;
import com.locus.edu.base.dao.DaoSupport;
import com.locus.edu.common.model.ComAddress;
import com.locus.edu.common.model.ComAdminStatus;
import com.locus.edu.common.model.ComAuthenticator;
import com.locus.edu.department.model.DepClassTeacher;
import com.locus.edu.staff.model.StaPersonal;


@Repository
public class StaffDao<T> extends DaoSupport {
	
	@Autowired
	BaseDao<T> baseDao;
	
	private static final Logger LOGGER = Logger.getLogger(StaffDao.class);

	@SuppressWarnings("unchecked")
	public StaPersonal updateAdminDetails(StaPersonal staPersonal) {
		
	  baseDao.update((T) staPersonal);
	  return staPersonal;
	}
		
	@SuppressWarnings("unchecked")
	public ComAddress saveStaffAddress(ComAddress comAddress) {
		baseDao.save((T) comAddress);
		return comAddress;
	}
	
	@SuppressWarnings("unchecked")
	public StaPersonal saveStaffDetails(StaPersonal staPersonal) {
		
	baseDao.save((T) staPersonal);
	return staPersonal;
	
	}

	@SuppressWarnings("unchecked")
	public ComAuthenticator saveComAuthenticator(ComAuthenticator comAuthenticator) {

       baseDao.save((T) comAuthenticator);
		return comAuthenticator;
	}

	@SuppressWarnings("unchecked")
	public ComAdminStatus saveComadminstatus(ComAdminStatus comAdminStatus) {
		baseDao.save((T) comAdminStatus);
		return comAdminStatus;
	}

	@SuppressWarnings("unchecked")
	public List<StaPersonal> fetchStaffDetail() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StaPersonal.class);
		List<StaPersonal> staPersonal = ((List<StaPersonal>) detachedCriteria.getExecutableCriteria(getSession()).list());

		if (staPersonal != null && !staPersonal.isEmpty()) {
			return (List<StaPersonal>) staPersonal;
		} else {
			LOGGER.info("User not found !");
			return null;

	}
}

	@SuppressWarnings("unchecked")
	public Object checkClassId(long classId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DepClassTeacher.class,"classTeacher");
		detachedCriteria.setFetchMode("classTeacher.depClassMaster",
				FetchMode.JOIN);
		detachedCriteria.createAlias("classTeacher.depClassMaster", "depclass");
		
		detachedCriteria.add(Restrictions.eq("depclass.classId", classId));
		
		List<Object> object = ((List<Object>) detachedCriteria.getExecutableCriteria(getSession()).list());
		
		if (object != null && !object.isEmpty()) {
			return  object.get(0);
		} else {
			LOGGER.info("User not found !");
			return null;
	}
	}

	@SuppressWarnings("unchecked")
	public ComAddress updateStaffAddress(ComAddress comAddress) {
		
		baseDao.update((T) comAddress);
		return comAddress;
	}

	@SuppressWarnings("unchecked")
	public StaPersonal updateStaffDetails(StaPersonal staPersonal) {
		
		baseDao.update((T) staPersonal);
		return staPersonal;
	}
}

