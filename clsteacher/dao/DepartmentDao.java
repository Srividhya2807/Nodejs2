package com.locus.edu.department.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.locus.edu.base.dao.BaseDao;
import com.locus.edu.base.dao.DaoSupport;
import com.locus.edu.department.model.DepClassMaster;
import com.locus.edu.department.model.DepClassTeacher;
import com.locus.edu.department.model.DepSectionMaster;
import com.locus.edu.department.model.DepStandardMaster;
import com.locus.edu.department.model.DepSubjectMaster;
import com.locus.edu.student.model.StuMaster;

@Repository
public class DepartmentDao<T> extends DaoSupport {

	private static final Logger LOGGER = Logger.getLogger(DepartmentDao.class);

	@Autowired
	BaseDao<T> baseDao;

	@SuppressWarnings("unchecked")
	public StuMaster getStudentDetails(String studentid) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				StuMaster.class, "stuMaster");

		detachedCriteria.setFetchMode("stuMaster.camInsttMaster",
				FetchMode.JOIN);
		detachedCriteria.createAlias("stuMaster.camInsttMaster",
				"camInsttMaster");

		detachedCriteria.setFetchMode("camInsttMaster.comAddressByAddress1",
				FetchMode.JOIN);

		detachedCriteria.add(Restrictions.eq("stuMaster.studentNo", studentid));

		List<StuMaster> obj = ((List<StuMaster>) detachedCriteria
				.getExecutableCriteria(getSession()).list());
		if (obj != null && !obj.isEmpty()) {
			return obj.get(0);
		}
		return null;
	}

	public Map<String, Integer> getActivityRecordCount(List<String> activities) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<DepStandardMaster> getAllStandardData() {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(DepStandardMaster.class);
		List<DepStandardMaster> depStandardMaster = ((List<DepStandardMaster>) detachedCriteria
				.getExecutableCriteria(getSession()).list());

		if (depStandardMaster != null && !depStandardMaster.isEmpty()) {
			return (List<DepStandardMaster>) depStandardMaster;
		} else {
			LOGGER.info("User not found !");
			return null;

		}

	}

	@SuppressWarnings("unchecked")
	public List<DepSectionMaster> getAllSectionData() {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(DepSectionMaster.class);
		List<DepSectionMaster> depSectionMaster = ((List<DepSectionMaster>) detachedCriteria
				.getExecutableCriteria(getSession()).list());

		if (depSectionMaster != null && !depSectionMaster.isEmpty()) {
			return (List<DepSectionMaster>) depSectionMaster;
		} else {
			LOGGER.info("User not found !");
			return null;

		}

	}

	@SuppressWarnings("unchecked")
	public DepStandardMaster getStandardId(String standard) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				DepStandardMaster.class, "depStandard");
		detachedCriteria.add(Restrictions.eq("depStandard.standardName",
				standard));

		List<DepStandardMaster> obj = ((List<DepStandardMaster>) detachedCriteria
				.getExecutableCriteria(getSession()).list());

		if (obj != null && !obj.isEmpty()) {
			return obj.get(0);
		} else {
			LOGGER.info("Exists!");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public DepSectionMaster getSectionId(char sec) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				DepSectionMaster.class, "depSection");
		detachedCriteria.add(Restrictions.eq("depSection.sectionName", sec));

		List<DepSectionMaster> obj = ((List<DepSectionMaster>) detachedCriteria
				.getExecutableCriteria(getSession()).list());

		if (obj != null && !obj.isEmpty()) {
			return obj.get(0);
		} else {
			LOGGER.info("Exists!");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public DepClassMaster findSectionId(long standard, long section) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				DepClassMaster.class, "depClass");
		detachedCriteria.setFetchMode("depClass.depStandardMaster",
				FetchMode.JOIN);
		detachedCriteria.createAlias("depClass.depStandardMaster", "depStand");
		detachedCriteria.add(Restrictions.eq("depStand.standardId", standard));
		detachedCriteria.setFetchMode("depClass.depSectionMaster",
				FetchMode.JOIN);
		detachedCriteria.createAlias("depClass.depSectionMaster", "depSection");
		detachedCriteria.add(Restrictions.eq("depSection.sectionId", section));

		List<DepClassMaster> obj = ((List<DepClassMaster>) detachedCriteria
				.getExecutableCriteria(getSession()).list());

		if (obj != null && !obj.isEmpty()) {
			return obj.get(0);
		} else {
			LOGGER.info("Exists!");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public DepClassMaster saveClassData(DepClassMaster depClassMaster) {

		baseDao.save((T) depClassMaster);
		return depClassMaster;

	}

	@SuppressWarnings("unchecked")
	public DepClassMaster checkRoom(Integer floor, String room) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				DepClassMaster.class, "depClass");
		detachedCriteria.add(Restrictions.eq("depClass.floor", floor));
		detachedCriteria.add(Restrictions.eq("depClass.room", room));

		List<DepClassMaster> obj = ((List<DepClassMaster>) detachedCriteria
				.getExecutableCriteria(getSession()).list());

		if (obj != null && !obj.isEmpty()) {
			return obj.get(0);
		} else {
			LOGGER.info("Exists!");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public DepSubjectMaster findSubjectById(long instId) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				DepSubjectMaster.class, "depSubjectMaster");

		detachedCriteria.setFetchMode("depSubjectMaster.camInsttMaster",
				FetchMode.JOIN);
		detachedCriteria.createAlias("depSubjectMaster.camInsttMaster",
				"camInsttMaster");

		detachedCriteria.setFetchMode("depSubjectMaster.depClassMaster",
				FetchMode.JOIN);
		detachedCriteria.createAlias("depSubjectMaster.depClassMaster",
				"depClassMaster");

		detachedCriteria.add(Restrictions.eq("camInsttMaster.insttId", instId));

		List<DepSubjectMaster> depSubjectMasters = ((List<DepSubjectMaster>) detachedCriteria
				.getExecutableCriteria(getSession()).list());

		if (depSubjectMasters != null && !depSubjectMasters.isEmpty()) {
			return depSubjectMasters.get(0);
		} else {
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	public DepClassMaster updateClassData(DepClassMaster depClassMaster) {

		baseDao.update((T) depClassMaster);

		return depClassMaster;
	}

	@SuppressWarnings("unchecked")
	public DepClassMaster deleteClassData(DepClassMaster depClassMaster) {

		baseDao.delete((T) depClassMaster);

		return null;
	}

	@SuppressWarnings("unchecked")
	public DepClassMaster findById(long l) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				DepClassMaster.class, "depClassMaster");
		detachedCriteria.add(Restrictions.eq("depClassMaster.classId", l));
		List<DepClassMaster> obj = ((List<DepClassMaster>) detachedCriteria
				.getExecutableCriteria(getSession()).list());
		if (obj != null && !obj.isEmpty()) {
			return obj.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<DepClassMaster> fetchClassId() {

		System.out.println("Entered into Dao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				DepClassMaster.class, "depClassMaster");
		// detachedCriteria.add(Restrictions.eq("depClassMaster.classId", id));
		List<DepClassMaster> obj = ((List<DepClassMaster>) detachedCriteria
				.getExecutableCriteria(getSession()).list());
		System.out.println("obj----" + obj);
		if (obj != null && !obj.isEmpty()) {
			return obj;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public DepClassMaster checkBulFlrRoom(String building, Integer floor,
			String room) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				DepClassMaster.class, "depClass");
		detachedCriteria.add(Restrictions.eq("depClass.building", building));
		detachedCriteria.add(Restrictions.eq("depClass.floor", floor));
		detachedCriteria.add(Restrictions.eq("depClass.room", room));

		List<DepClassMaster> obj = ((List<DepClassMaster>) detachedCriteria
				.getExecutableCriteria(getSession()).list());

		if (obj != null && !obj.isEmpty()) {
			return obj.get(0);
		} else {

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public DepClassMaster findByIdClass(long l) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				DepClassMaster.class, "depClass");
		detachedCriteria.add(Restrictions.eq("depClass.classId", l));

		List<DepClassMaster> obj = ((List<DepClassMaster>) detachedCriteria
				.getExecutableCriteria(getSession()).list());

		if (obj != null && !obj.isEmpty()) {
			return obj.get(0);
		} else {

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DepSubjectMaster> getAllSubjectDetail() {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DepSubjectMaster.class);
		List<DepSubjectMaster> depSubjectMaster = ((List<DepSubjectMaster>) detachedCriteria.getExecutableCriteria(getSession()).list());
		if (depSubjectMaster != null && !depSubjectMaster.isEmpty()) {
			return (List<DepSubjectMaster>) depSubjectMaster;
		} else {
			LOGGER.info("User not found !");
			return null;

		}

	
	}

	@SuppressWarnings("unchecked")
	public DepClassTeacher saveDepClassTeacherDetails(DepClassTeacher depClassTeacher) {
		
		baseDao.save((T) depClassTeacher);
		return depClassTeacher;
	}

	@SuppressWarnings("unchecked")
	public DepClassTeacher updateDepClassTeacherDetails(DepClassTeacher depClassTeacher) {

		baseDao.update((T) depClassTeacher);
		return depClassTeacher;
	}

}
