package com.locus.edu.staff.api;

import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.locus.edu.base.api.RestUtils;
import com.locus.edu.base.dao.BaseDao;
import com.locus.edu.staff.service.StaffService;
import com.locus.edu.util.EduConstants;

@RestController
@RequestMapping("staff/info")
public class StaffController<T> extends RestUtils {

	private static final Logger LOGGER = Logger.getLogger(StaffController.class);

	@Autowired
	StaffService<T> staffService;

	@Autowired
	BaseDao<T> baseDao;

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object updateStaffData(@RequestBody Map<String, Object> staffData) {
		try {
			return getSuccessResponse(staffService.updateStaffDetails(staffData));
		} catch (Exception e) {
			String errMsg = "Error while Update Staff data !";
			LOGGER.error(errMsg, e);
			return getErrorResponse(errMsg);
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object saveStaffData(@RequestBody Map<String, Object> staffData) {
		try {
			return getSuccessResponse(staffService.saveStaffDetails(staffData));
		} catch (Exception e) {
			String errMsg = "Error while Save Staff data !";
			LOGGER.error(errMsg, e);
			return getErrorResponse(errMsg);
		}
	}

	@RequestMapping(value = "findById" + EduConstants.SLASH
			+ "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object findStaffData(@PathVariable long id) {
		try {
			return getSuccessResponse(staffService.findStaffDetails(id));
		} catch (Exception e) {
			String errMsg = "Error while Get Staff data !";
			LOGGER.error(errMsg, e);
			return getErrorResponse(errMsg);
		}
	}

	@RequestMapping(value = "/admin/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object saveSubadminData(@RequestBody Map<String, Object> staffData) {
		try {
			return getSuccessResponse(staffService.saveSubadminDetails(staffData));
		} catch (Exception e) {
			String errMsg = "Error while Save subAdmin data !";
			LOGGER.error(errMsg, e);
			return getErrorResponse(errMsg);
		}

	}
	
	@RequestMapping(value = "/addNewTeacher", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object advanceEditSave(@RequestBody Map<String, Object> staffData){
		System.out.println("staffData---------"+staffData);
		try {
			return getSuccessResponse(staffService.saveAddNewTeacher(staffData));
		} catch (Exception e) {
			String errMsg = "Error while Saving New Staff data !";
			LOGGER.error(errMsg, e);
			return getErrorResponse(errMsg);
		}
	}
	
	@RequestMapping(value = "/advanceEditUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object advanceEditUpdate(@RequestBody Map<String, Object> staffData){
		System.out.println("staffData---------"+staffData);
		try {
			return getSuccessResponse(staffService.updateAdvanceEdit(staffData));
		} catch (Exception e) {
			String errMsg = "Error while Saving New Staff data !";
			LOGGER.error(errMsg, e);
			return getErrorResponse(errMsg);
		}
	}
}
