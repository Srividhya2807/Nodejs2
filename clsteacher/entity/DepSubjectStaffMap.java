package com.locus.edu.department.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.locus.edu.campus.model.CamInsttMaster;
import com.locus.edu.common.model.TableLogger;
import com.locus.edu.staff.model.StaPersonal;


@SuppressWarnings("serial")
@Entity
@Table(name = "DEP_SUBJECT_STAFF_MAP", uniqueConstraints = @UniqueConstraint(columnNames = {
		"REF_SUBJECT_ID", "REF_STANDARD_ID", "ACADEMIC_YR","REF_INSTT_ID","REF_APP_ID" }))
public class DepSubjectStaffMap extends TableLogger implements java.io.Serializable {

	private long dssmId;
	private DepStandardMaster depStandardMaster;
	private DepSubjectMaster depSubjectMaster;
	private CamInsttMaster camInsttMaster;
	private StaPersonal staPersonal;
	//private CamInsttWorkMaster camInsttWorkMaster;
	private Date academicYr;
	
	public DepSubjectStaffMap() {
	}

	@Id
	@GeneratedValue(generator = "dssmId")
	@GenericGenerator(name = "dssmId", strategy = "increment")
	@Column(name = "DSSM_ID", unique = true, nullable = false)
	public long getDssmId() {
		return this.dssmId;
	}

	public void setDssmId(long dssmId) {
		this.dssmId = dssmId;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_INSTT_ID", nullable = false)
	public CamInsttMaster getCamInsttMaster() {
		return camInsttMaster;
	}

	public void setCamInsttMaster(CamInsttMaster camInsttMaster) {
		this.camInsttMaster = camInsttMaster;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_SUBJECT_ID", nullable = false)
	public DepSubjectMaster getDepSubjectMaster() {
		return this.depSubjectMaster;
	}

	public void setDepSubjectMaster(DepSubjectMaster depSubjectMaster) {
		this.depSubjectMaster = depSubjectMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_APP_ID", nullable = false)
	public StaPersonal getStaPersonal() {
		return staPersonal;
	}

	public void setStaPersonal(StaPersonal staPersonal) {
		this.staPersonal = staPersonal;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_STANDARD_ID", nullable = false)
	public DepStandardMaster getDepStandardMaster() {
		return depStandardMaster;
	}

	public void setDepStandardMaster(DepStandardMaster depStandardMaster) {
		this.depStandardMaster = depStandardMaster;
	}

	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name = "ACADEMIC_YR", nullable = false)
	public Date getAcademicYr() {
		return academicYr;
	}

	public void setAcademicYr(Date academicYr) {
		this.academicYr = academicYr;
	}
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "ACADEMIC_YR", nullable = false, referencedColumnName = "ACADEMIC_YR")
//	public CamInsttWorkMaster getCamInsttWorkMaster() {
//		return camInsttWorkMaster;
//	}
//
//	public void setCamInsttWorkMaster(CamInsttWorkMaster camInsttWorkMaster) {
//		this.camInsttWorkMaster = camInsttWorkMaster;
//	}
	
	
}
