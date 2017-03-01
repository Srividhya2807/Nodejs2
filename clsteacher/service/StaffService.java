package com.locus.edu.staff.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locus.edu.campus.model.CamInsttMaster;
import com.locus.edu.common.model.ComAddress;
import com.locus.edu.common.model.ComAdminStatus;
import com.locus.edu.common.model.ComAuthenticator;
import com.locus.edu.common.model.ComScreenName;
import com.locus.edu.department.dao.DepartmentDao;
import com.locus.edu.department.model.DepClassMaster;
import com.locus.edu.department.model.DepClassTeacher;
import com.locus.edu.department.model.DepStandardMaster;
import com.locus.edu.department.model.DepSubjectMaster;
import com.locus.edu.department.model.DepSubjectStaffMap;
import com.locus.edu.login.dao.LoginDao;
import com.locus.edu.staff.dao.StaffDao;
import com.locus.edu.staff.dao.StaffSubjectMappingDao;
import com.locus.edu.staff.model.StaPersonal;
import com.locus.edu.util.TypeUtils;

@Service
public class StaffService<T> {

	private static final Logger LOGGER = Logger.getLogger(StaffService.class);

	@Autowired
	StaffDao<T> staffDao;

	@Autowired
	LoginDao<T> loginDao;

	@Autowired
	StaffSubjectMappingDao<T> staffSubjectMappingDao;

	@Autowired
	DepartmentDao<T> departmentDao;

	@Transactional
	public Map<String, Object> updateStaffDetails(Map<String, Object> staffData) {

		Map<String, Object> result = new HashMap<String, Object>();

		StaPersonal staPersonal = new StaPersonal();

		if (staffData != null && !staffData.isEmpty()) {

			String userId = TypeUtils.toStringValue(staffData.get("userId"));

			if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("insttId")))) {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			} else {
				CamInsttMaster camInsttMaster = new CamInsttMaster();
				camInsttMaster.setInsttId(TypeUtils.toLongValue(staffData.get("insttId")));
				staPersonal.setCamInsttMaster(camInsttMaster);
			}

			if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("addressId")))) {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			} else {
				ComAddress comAddress = new ComAddress();
				comAddress.setAddressId(TypeUtils.toLongValue(staffData.get("addressId")));
				comAddress.setStreet1(TypeUtils.toStringValue(staffData.get("street")));
				comAddress.setCity(TypeUtils.toStringValue(staffData.get("city")));
				comAddress.setDistrict(TypeUtils.toStringValue(staffData.get("district")));
				comAddress.setEmail(TypeUtils.toStringValue(staffData.get("email1")));
				;
				comAddress.setMobile(TypeUtils.toStringValue(staffData.get("mobile")));
				;
				comAddress.setNation(TypeUtils.toStringValue(staffData.get("nation")));
				comAddress.setPincode(TypeUtils.toStringValue(staffData.get("pincode")));
				;
				comAddress.setState(TypeUtils.toStringValue(staffData.get("state")));
				staPersonal.setComAddress(comAddress);
			}
			staPersonal.setStaffId(TypeUtils.toStringValue(staffData.get("staffId")));
			staPersonal.setEmail(TypeUtils.toStringValue(staffData.get("email")));
			staPersonal.setFirstName(TypeUtils.toStringValue(staffData.get("firstName")));
			staPersonal.setMobileNumber(TypeUtils.toStringValue(staffData.get("mobileNumber")));
			staPersonal.setAppId(TypeUtils.toLongValue(staffData.get("appId")));
			staPersonal.setDob(new Date(TypeUtils.toLongValue(staffData.get("dob"))));
			staPersonal.setSex(TypeUtils.toStringValue(staffData.get("sex")).charAt(0));

			staPersonal = staffDao.updateAdminDetails(staPersonal);

			if (staPersonal != null) {
				long screenId = TypeUtils.toLongValue(staffData.get("screenId"));
				ComAdminStatus comAdminStatus = new ComAdminStatus();
				comAdminStatus = loginDao.findScreenStatus(userId);
				if (comAdminStatus != null) {

					int maxscreenId = comAdminStatus.getMaxScreen();

					if (screenId >= maxscreenId) {
						comAdminStatus.setFlag(true);
						ComScreenName comScreenName = new ComScreenName();
						comScreenName.setScreenId(2);
						comAdminStatus.setComScreenName(comScreenName);
						comAdminStatus.setMaxScreen(2);
					} else {
						comAdminStatus.setFlag(true);
						ComScreenName comScreenName = new ComScreenName();
						comScreenName.setScreenId(maxscreenId);
						comAdminStatus.setComScreenName(comScreenName);
						comAdminStatus.setMaxScreen(maxscreenId);
					}
					comAdminStatus = loginDao.updateScreenStatus(comAdminStatus);
					if (comAdminStatus != null) {
						result.put("Message", "Success");
						return result;
					} else {
						result.put("Message", "Error");
						result.put("Reason", "Updating Screen Status Is Failed");
						return result;
					}
				} else {
					result.put("Message", "Error");
					result.put("Reason", "Fetching Screen Status Is Failed");
					return result;
				}
			} else {
				LOGGER.error("Admin data update fails");
				result.put("Message", "Error");
				result.put("Reason", "Admin data update fails");
				return result;
			}
		} else {
			result.put("Message", "Error");
			result.put("Reason", "Please Fill All the Details!");
			return result;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Map<String, Object> saveStaffDetails(Map<String, Object> staffData) {

		Map<String, Object> result = new HashMap<String, Object>();

		StaPersonal staPersonal = new StaPersonal();
		CamInsttMaster camInsttMaster = new CamInsttMaster();

		if (staffData != null && !staffData.isEmpty()) {

			if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("insttId")))) {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			} else {
				camInsttMaster.setInsttId(TypeUtils.toLongValue(staffData.get("insttId")));
				staPersonal.setCamInsttMaster(camInsttMaster);
			}

			if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("address")))) {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			} else {
				Map<String, Object> address = (Map<String, Object>) staffData.get("address");
				ComAddress comAddress = new ComAddress();
				comAddress.setAddressId(TypeUtils.toLongValue(0));
				comAddress.setStreet1(TypeUtils.toStringValue(address.get("street")));
				comAddress.setCity(TypeUtils.toStringValue(address.get("city")));
				comAddress.setDistrict(TypeUtils.toStringValue(address.get("district")));
				comAddress.setEmail(TypeUtils.toStringValue(address.get("email1")));
				;
				comAddress.setMobile(TypeUtils.toStringValue(address.get("mobile")));
				;
				comAddress.setNation(TypeUtils.toStringValue(address.get("nation")));
				comAddress.setPincode(TypeUtils.toStringValue(address.get("pincode")));
				;
				comAddress.setState(TypeUtils.toStringValue(address.get("state")));
				comAddress = staffDao.saveStaffAddress(comAddress);
				staPersonal.setComAddress(comAddress);
			}

			staPersonal.setStaffId(TypeUtils.toStringValue(staffData.get("staffId")));
			staPersonal.setEmail(TypeUtils.toStringValue(staffData.get("email")));
			staPersonal.setFirstName(TypeUtils.toStringValue(staffData.get("firstName")));
			staPersonal.setLastName(TypeUtils.toStringValue(staffData.get("lastName")));
			staPersonal.setMobileNumber(TypeUtils.toStringValue(staffData.get("mobileNumber")));
			staPersonal.setDob(new Date(TypeUtils.toLongValue(staffData.get("dob"))));
			staPersonal.setSex(TypeUtils.toStringValue(staffData.get("sex")).charAt(0));

			staPersonal = staffDao.saveStaffDetails(staPersonal);
			if (staPersonal != null) {

				ComAuthenticator comAuthenticator = new ComAuthenticator();
				ComAdminStatus comAdminStatus = new ComAdminStatus();
				comAuthenticator.setCamInsttMaster(camInsttMaster);
				comAuthenticator.setUser(staPersonal.getStaffId());
				comAuthenticator.setUserName(staPersonal.getStaffId());
				comAuthenticator.setEncPassword("admin");
				comAuthenticator.setEducationStatus("B.Tech");
				comAuthenticator.setFlag(false);
				comAuthenticator.setAuthlevel(1);
				comAuthenticator = staffDao.saveComAuthenticator(comAuthenticator);
				if (comAuthenticator != null) {
					comAdminStatus.setComAuthenticator(comAuthenticator);
					ComScreenName comScreenName = new ComScreenName();
					comScreenName.setScreenId(1);
					comAdminStatus.setComScreenName(comScreenName);
					comAdminStatus.setFlag(false);
					comAdminStatus.setMaxScreen(1);
					comAdminStatus = staffDao.saveComadminstatus(comAdminStatus);
					if (comAdminStatus != null) {
						result.put("Message", "Success");
						return result;
					} else {
						result.put("Message", "Error");
						result.put("Reason", "Error while saving subadmin data!");
						return result;
					}
				} else {
					result.put("Message", "Error");
					result.put("Reason", "Error while saving subadmin data!");
					return result;
				}

			} else {
				LOGGER.error("Admin data update fails");
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			}
		} else {
			result.put("Message", "Error");
			result.put("Reason", "Please Fill All the Details!");
			return result;
		}
	}

	@Transactional
	public Map<String, Object> findStaffDetails(long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result = loginDao.getStaffDetails(id);
		if (!result.isEmpty() && result != null) {
			result.put("ScreenId", 1);
			return result;
		} else {
			result.put("Message", "No Data Found!");
			return result;
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Map<String, Object> saveSubadminDetails(Map<String, Object> staffData) {
		Map<String, Object> result = new HashMap<String, Object>();

		StaPersonal staPersonal = new StaPersonal();
		CamInsttMaster camInsttMaster = new CamInsttMaster();
		if (staffData != null && !staffData.isEmpty()) {

			if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("insttId")))) {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			} else {

				camInsttMaster.setInsttId(TypeUtils.toLongValue(staffData.get("insttId")));
				staPersonal.setCamInsttMaster(camInsttMaster);
			}

			if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("address")))) {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			} else {
				Map<String, Object> address = (Map<String, Object>) staffData.get("address");
				ComAddress comAddress = new ComAddress();
				comAddress.setAddressId(TypeUtils.toLongValue(0));
				comAddress.setStreet1(TypeUtils.toStringValue(address.get("street")));
				comAddress.setCity(TypeUtils.toStringValue(address.get("city")));
				comAddress.setDistrict(TypeUtils.toStringValue(address.get("district")));
				comAddress.setEmail(TypeUtils.toStringValue(address.get("email1")));
				;
				comAddress.setMobile(TypeUtils.toStringValue(address.get("mobile")));
				;
				comAddress.setNation(TypeUtils.toStringValue(address.get("nation")));
				comAddress.setPincode(TypeUtils.toStringValue(address.get("pincode")));
				;
				comAddress.setState(TypeUtils.toStringValue(address.get("state")));
				comAddress = staffDao.saveStaffAddress(comAddress);
				staPersonal.setComAddress(comAddress);
			}

			staPersonal.setStaffId(TypeUtils.toStringValue(staffData.get("staffId")));
			staPersonal.setEmail(TypeUtils.toStringValue(staffData.get("email")));
			staPersonal.setFirstName(TypeUtils.toStringValue(staffData.get("firstName")));
			staPersonal.setLastName(TypeUtils.toStringValue(staffData.get("lastName")));
			staPersonal.setMobileNumber(TypeUtils.toStringValue(staffData.get("mobileNumber")));
			staPersonal.setDob(new Date(TypeUtils.toLongValue(staffData.get("dob"))));
			staPersonal.setSex(TypeUtils.toStringValue(staffData.get("sex")).charAt(0));

			staPersonal = staffDao.saveStaffDetails(staPersonal);

			if (staPersonal != null) {
				// String userId = staPersonal.getStaffId();
				long screenId = TypeUtils.toLongValue(staffData.get("screenId"));
				String userId = TypeUtils.toStringValue(staffData.get("userId"));
				ComAuthenticator comAuthenticator = new ComAuthenticator();
				ComAdminStatus comAdminStatus = new ComAdminStatus();
				comAuthenticator.setCamInsttMaster(camInsttMaster);
				comAuthenticator.setUser(staPersonal.getStaffId());
				comAuthenticator.setUserName(staPersonal.getStaffId());
				comAuthenticator.setEncPassword("admin");
				comAuthenticator.setEducationStatus("B.Tech");
				comAuthenticator.setFlag(false);
				comAuthenticator.setAuthlevel(1);
				comAuthenticator = staffDao.saveComAuthenticator(comAuthenticator);
				if (comAuthenticator != null) {
					comAdminStatus.setComAuthenticator(comAuthenticator);
					ComScreenName comScreenName = new ComScreenName();
					comScreenName.setScreenId(1);
					comAdminStatus.setComScreenName(comScreenName);
					comAdminStatus.setFlag(false);
					comAdminStatus.setMaxScreen(1);

					comAdminStatus = staffDao.saveComadminstatus(comAdminStatus);

					if (comAdminStatus != null) {
						comAdminStatus = loginDao.findScreenStatus(userId);
						if (comAdminStatus != null) {

							int maxscreenId = comAdminStatus.getMaxScreen();

							if (screenId >= maxscreenId) {
								comAdminStatus.setFlag(true);
								comScreenName.setScreenId(5);
								comAdminStatus.setComScreenName(comScreenName);
								comAdminStatus.setMaxScreen(5);
							} else {
								comAdminStatus.setFlag(true);
								comScreenName.setScreenId(maxscreenId);
								comAdminStatus.setComScreenName(comScreenName);
								comAdminStatus.setMaxScreen(maxscreenId);
							}
							comAdminStatus = loginDao.updateScreenStatus(comAdminStatus);
							if (comAdminStatus != null) {
								result.put("Message", "Success");
								return result;
							}
							result.put("Message", "Success");
							return result;
						}

						else {
							result.put("Message", "Error");
							result.put("Reason", "Updating Screen Status Is Failed");
							return result;
						}
					} else {
						result.put("Message", "Error");
						result.put("Reason", "Fetching Screen Status Is Failed");
						return result;
					}
				} else {
					LOGGER.error("Admin data update fails");
					result.put("Message", "Error");
					result.put("Reason", "Please Fill All the Details!");
					return result;
				}
			} else {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			}
		} else {
			result.put("Message", "Error");
			result.put("Reason", "Please Fill All the Details!");
			return result;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Object saveAddNewTeacher(Map<String, Object> staffData) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Message", "Success");
		StaPersonal staPersonal = new StaPersonal();

		if (staffData != null && !staffData.isEmpty()) {

			staPersonal.setAppId(TypeUtils.toLongValue(0));

			if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("camInsttMaster")))) {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			} else {
				CamInsttMaster camInsttMaster = new CamInsttMaster();
				camInsttMaster.setInsttId(TypeUtils.toLongValue(staffData.get("camInsttMaster")));
				staPersonal.setCamInsttMaster(camInsttMaster);
			}

			if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("comAddress")))) {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			} else {
				Map<String, Object> address = (Map<String, Object>) staffData.get("comAddress");
				ComAddress comAddress = new ComAddress();
				comAddress.setAddressId(TypeUtils.toLongValue(0));
				comAddress.setStreet1(TypeUtils.toStringValue(address.get("street1")));
				comAddress.setCity(TypeUtils.toStringValue(address.get("city")));
				comAddress.setDistrict(TypeUtils.toStringValue(address.get("district")));
				comAddress.setEmail(TypeUtils.toStringValue(address.get("email")));
				;
				comAddress.setMobile(TypeUtils.toStringValue(address.get("mobile")));
				;
				comAddress.setNation(TypeUtils.toStringValue(address.get("nation")));
				comAddress.setPincode(TypeUtils.toStringValue(address.get("pincode")));
				;
				comAddress.setState(TypeUtils.toStringValue(address.get("state")));
				comAddress = staffDao.saveStaffAddress(comAddress);
				staPersonal.setComAddress(comAddress);
			}

			staPersonal.setStaffId(TypeUtils.toStringValue(staffData.get("staffId")));
			staPersonal.setEmail(TypeUtils.toStringValue(staffData.get("email")));
			staPersonal.setFirstName(TypeUtils.toStringValue(staffData.get("firstName")));
			staPersonal.setLastName(TypeUtils.toStringValue(staffData.get("lastName")));
			staPersonal.setMobileNumber(TypeUtils.toStringValue(staffData.get("mobileNumber")));
			staPersonal.setDob(new Date(TypeUtils.toLongValue(staffData.get("dob"))));
			staPersonal.setSex(TypeUtils.toStringValue(staffData.get("sex")).charAt(0));

			staPersonal = staffDao.saveStaffDetails(staPersonal);

			if (staPersonal != null) {

				List<Map<String, Object>> subjectData = (List<Map<String, Object>>) staffData.get("subjectData");
				Map<String, Object> staffdetails = new HashMap<String, Object>();
				System.out.println(staPersonal.getAppId());
				System.out.println(staPersonal.getCamInsttMaster().getInsttId());

				long appId = staPersonal.getAppId();
				long insttId = staPersonal.getCamInsttMaster().getInsttId();

				if (subjectData != null && !subjectData.isEmpty()) {

					for (int i = 0; i < subjectData.size(); i++) {
						staffdetails = subjectData.get(i);

						DepSubjectStaffMap depSubjectStaffMap = new DepSubjectStaffMap();
						depSubjectStaffMap.setDssmId(TypeUtils.toLongValue(0));

						if (StringUtils.isBlank(TypeUtils.toStringValue(staffdetails.get("depStandardMaster")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							DepStandardMaster depStandardMaster = new DepStandardMaster();
							depStandardMaster
									.setStandardId(TypeUtils.toLongValue(staffdetails.get("depStandardMaster")));
							depSubjectStaffMap.setDepStandardMaster(depStandardMaster);
						}

						if (StringUtils.isBlank(TypeUtils.toStringValue(staffdetails.get("depSubjectMaster")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							DepSubjectMaster depSubjectMaster = new DepSubjectMaster();
							depSubjectMaster.setSubjectId(TypeUtils.toLongValue(staffdetails.get("depSubjectMaster")));
							depSubjectStaffMap.setDepSubjectMaster(depSubjectMaster);
						}

						if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("camInsttMaster")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							CamInsttMaster camInsttMaster = new CamInsttMaster();
							camInsttMaster.setInsttId(insttId);
							depSubjectStaffMap.setCamInsttMaster(camInsttMaster);
						}

						if (StringUtils.isBlank(TypeUtils.toStringValue(staffdetails.get("staPersonal")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							StaPersonal staff = new StaPersonal();
							staff.setAppId(appId);
							depSubjectStaffMap.setStaPersonal(staff);
						}

						depSubjectStaffMap
								.setAcademicYr(new Date(TypeUtils.toLongValue(staffdetails.get("academicYr"))));
						depSubjectStaffMap = staffSubjectMappingDao.saveSubjectMappingDetails(depSubjectStaffMap);
					}
				} else {
					result.put("Message", "Teachers created successfully!");
					return result;
				}
				DepClassTeacher depClassTeacher = new DepClassTeacher();
				Map<String, Object> clsTeacher = (Map<String, Object>) staffData.get("clsTeacherData");
				if (clsTeacher != null && !clsTeacher.isEmpty()) {
					long classId = TypeUtils.toLongValue(clsTeacher.get("depClassMaster"));
					System.out.println("depClassMaster-----" + classId);
					Object classTeacher = staffDao.checkClassId(classId);
					System.out.println("classTeacher------" + classTeacher);
					if (classTeacher != null) {
						result.put("Message", "Error");
						result.put("Reason", "For this class and Section already class teacher is allocated!");
						return result;

					} else {

						depClassTeacher.setClassTeacherId(TypeUtils.toLongValue(0));

						if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("camInsttMaster")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							CamInsttMaster camInsttMaster = new CamInsttMaster();
							camInsttMaster.setInsttId(insttId);
							depClassTeacher.setCamInsttMaster(camInsttMaster);
						}
						depClassTeacher.setAcademicYr(new Date(TypeUtils.toLongValue(staffdetails.get("academicYr"))));

						if (StringUtils.isBlank(TypeUtils.toStringValue(clsTeacher.get("depClassMaster")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							DepClassMaster clsMaster = new DepClassMaster();
							clsMaster.setClassId(classId);
							depClassTeacher.setDepClassMaster(clsMaster);

						}

						if (StringUtils.isBlank(TypeUtils.toStringValue(staffdetails.get("staPersonal")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							StaPersonal staff = new StaPersonal();
							staff.setAppId(appId);
							depClassTeacher.setStaPersonal(staff);
						}
						depClassTeacher = departmentDao.saveDepClassTeacherDetails(depClassTeacher);

						if (depClassTeacher != null) {
							result.put("Message", "Teachers created successfully!");
							return result;
						} else {
							result.put("Message", "Error");
							result.put("Reason", "Error occur while creating teacher details!");
							return result;
						}

					}
				} else {
					result.put("Message", "Teachers created successfully!");
					return result;
				}

			} else {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			}

		} else {
			result.put("Message", "Error");
			result.put("Reason", "Please Fill All the Details!");
			return result;
		}

	}

	@SuppressWarnings("unchecked")
	public Object updateAdvanceEdit(Map<String, Object> staffData) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Message", "Success");

		StaPersonal staPersonal = new StaPersonal();

		if (staffData != null && !staffData.isEmpty()) {

			staPersonal.setAppId(TypeUtils.toLongValue(staffData.get("appId")));

			if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("camInsttMaster")))) {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			} else {
				CamInsttMaster camInsttMaster = new CamInsttMaster();
				camInsttMaster.setInsttId(TypeUtils.toLongValue(staffData.get("camInsttMaster")));
				staPersonal.setCamInsttMaster(camInsttMaster);
			}

			if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("comAddress")))) {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			} else {
				Map<String, Object> address = (Map<String, Object>) staffData.get("comAddress");
				ComAddress comAddress = new ComAddress();
				comAddress.setAddressId(TypeUtils.toLongValue(address.get("addressId")));
				comAddress.setStreet1(TypeUtils.toStringValue(address.get("street1")));
				comAddress.setCity(TypeUtils.toStringValue(address.get("city")));
				comAddress.setDistrict(TypeUtils.toStringValue(address.get("district")));
				comAddress.setEmail(TypeUtils.toStringValue(address.get("email")));
				;
				comAddress.setMobile(TypeUtils.toStringValue(address.get("mobile")));
				;
				comAddress.setNation(TypeUtils.toStringValue(address.get("nation")));
				comAddress.setPincode(TypeUtils.toStringValue(address.get("pincode")));
				;
				comAddress.setState(TypeUtils.toStringValue(address.get("state")));
				comAddress = staffDao.updateStaffAddress(comAddress);
				staPersonal.setComAddress(comAddress);
			}

			staPersonal.setStaffId(TypeUtils.toStringValue(staffData.get("staffId")));
			staPersonal.setEmail(TypeUtils.toStringValue(staffData.get("email")));
			staPersonal.setFirstName(TypeUtils.toStringValue(staffData.get("firstName")));
			staPersonal.setLastName(TypeUtils.toStringValue(staffData.get("lastName")));
			staPersonal.setMobileNumber(TypeUtils.toStringValue(staffData.get("mobileNumber")));
			staPersonal.setDob(new Date(TypeUtils.toLongValue(staffData.get("dob"))));
			staPersonal.setSex(TypeUtils.toStringValue(staffData.get("sex")).charAt(0));

			staPersonal = staffDao.updateStaffDetails(staPersonal);

			if (staPersonal != null) {

				List<Map<String, Object>> subjectData = (List<Map<String, Object>>) staffData.get("subjectData");
				Map<String, Object> staffdetails = new HashMap<String, Object>();
				System.out.println(staPersonal.getAppId());
				System.out.println(staPersonal.getCamInsttMaster().getInsttId());

				long appId = staPersonal.getAppId();
				long insttId = staPersonal.getCamInsttMaster().getInsttId();

				if (subjectData != null && !subjectData.isEmpty()) {

					for (int i = 0; i < subjectData.size(); i++) {
						staffdetails = subjectData.get(i);

						DepSubjectStaffMap depSubjectStaffMap = new DepSubjectStaffMap();
						depSubjectStaffMap.setDssmId(TypeUtils.toLongValue(staffdetails.get("dssmId")));

						if (StringUtils.isBlank(TypeUtils.toStringValue(staffdetails.get("depStandardMaster")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							DepStandardMaster depStandardMaster = new DepStandardMaster();
							depStandardMaster
									.setStandardId(TypeUtils.toLongValue(staffdetails.get("depStandardMaster")));
							depSubjectStaffMap.setDepStandardMaster(depStandardMaster);
						}

						if (StringUtils.isBlank(TypeUtils.toStringValue(staffdetails.get("depSubjectMaster")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							DepSubjectMaster depSubjectMaster = new DepSubjectMaster();
							depSubjectMaster.setSubjectId(TypeUtils.toLongValue(staffdetails.get("depSubjectMaster")));
							depSubjectStaffMap.setDepSubjectMaster(depSubjectMaster);
						}

						if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("camInsttMaster")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							CamInsttMaster camInsttMaster = new CamInsttMaster();
							camInsttMaster.setInsttId(insttId);
							depSubjectStaffMap.setCamInsttMaster(camInsttMaster);
						}

						if (StringUtils.isBlank(TypeUtils.toStringValue(staffdetails.get("staPersonal")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							StaPersonal staff = new StaPersonal();
							staff.setAppId(appId);
							depSubjectStaffMap.setStaPersonal(staff);
						}

						depSubjectStaffMap
								.setAcademicYr(new Date(TypeUtils.toLongValue(staffdetails.get("academicYr"))));
						depSubjectStaffMap = staffSubjectMappingDao.updateSubjectMappingDetails(depSubjectStaffMap);
					}
				} else {
					result.put("Message", "Teachers created successfully!");
					return result;
				}
				DepClassTeacher depClassTeacher = new DepClassTeacher();
				Map<String, Object> clsTeacher = (Map<String, Object>) staffData.get("clsTeacherData");
				if (clsTeacher != null && !clsTeacher.isEmpty()) {
					long classId = TypeUtils.toLongValue(clsTeacher.get("depClassMaster"));
					System.out.println("depClassMaster-----" + classId);
					Object classTeacher = staffDao.checkClassId(classId);
					System.out.println("classTeacher------" + classTeacher);
					if (classTeacher != null) {
						result.put("Message", "Error");
						result.put("Reason", "For this class and Section already class teacher is allocated!");
						return result;

					} else {

						depClassTeacher.setClassTeacherId(TypeUtils.toLongValue(clsTeacher.get("classTeacherId")));

						if (StringUtils.isBlank(TypeUtils.toStringValue(staffData.get("camInsttMaster")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							CamInsttMaster camInsttMaster = new CamInsttMaster();
							camInsttMaster.setInsttId(insttId);
							depClassTeacher.setCamInsttMaster(camInsttMaster);
						}
						depClassTeacher.setAcademicYr(new Date(TypeUtils.toLongValue(staffdetails.get("academicYr"))));

						if (StringUtils.isBlank(TypeUtils.toStringValue(clsTeacher.get("depClassMaster")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							DepClassMaster clsMaster = new DepClassMaster();
							clsMaster.setClassId(classId);
							depClassTeacher.setDepClassMaster(clsMaster);

						}

						if (StringUtils.isBlank(TypeUtils.toStringValue(staffdetails.get("staPersonal")))) {
							result.put("Message", "Error");
							result.put("Reason", "Please Fill All the Details!");
							return result;
						} else {
							StaPersonal staff = new StaPersonal();
							staff.setAppId(appId);
							depClassTeacher.setStaPersonal(staff);
						}
						depClassTeacher = departmentDao.updateDepClassTeacherDetails(depClassTeacher);

						if (depClassTeacher != null) {
							result.put("Message", "Teachers created successfully!");
							return result;
						} else {
							result.put("Message", "Error");
							result.put("Reason", "Error occur while creating teacher details!");
							return result;
						}

					}
				} else {
					result.put("Message", "Teachers created successfully!");
					return result;
				}

			} else {
				result.put("Message", "Error");
				result.put("Reason", "Please Fill All the Details!");
				return result;
			}

		} else {
			result.put("Message", "Error");
			result.put("Reason", "Please Fill All the Details!");
			return result;
		}
	}
}
