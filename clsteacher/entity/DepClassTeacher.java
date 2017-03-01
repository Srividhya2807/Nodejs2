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


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.locus.edu.campus.model.CamInsttMaster;
import com.locus.edu.campus.model.CamInsttWorkMaster;
import com.locus.edu.common.model.TableLogger;
import com.locus.edu.staff.model.StaPersonal;



@SuppressWarnings("serial")
@Entity
@Table(name= "DEP_CLASS_TEACHER",uniqueConstraints = @UniqueConstraint(columnNames = { "ACADEMIC_YR",
"REF_CLASS_ID", "REF_APP_ID" }))
public class DepClassTeacher extends TableLogger implements java.io.Serializable {

	private long classTeacherId;
	private CamInsttMaster camInsttMaster;
    private DepClassMaster depClassMaster;
    private StaPersonal staPersonal;
    private Date academicYr;
    
    
    @Id
	@GeneratedValue(generator = "classTeacherId")
	@GenericGenerator(name = "classTeacherId", strategy = "increment")
	@Column(name = "CLASS_TEACHER_ID", unique = true, nullable = false, length = 15)
    public long getClassTeacherId() {
		return classTeacherId;
	}
	public void setClassTeacherId(long classTeacherId) {
		this.classTeacherId = classTeacherId;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_INSTT_ID", nullable = false)
	public CamInsttMaster getCamInsttMaster() {
		return camInsttMaster;
	}
	public void setCamInsttMaster(CamInsttMaster camInsttMaster) {
		this.camInsttMaster = camInsttMaster;
	}
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name = "ACADEMIC_YR", nullable = false)
	public Date getAcademicYr() {
		return academicYr;
	}
	public void setAcademicYr(Date academicYr) {
		this.academicYr = academicYr;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_CLASS_ID", nullable = false)
	public  DepClassMaster getDepClassMaster() {
		return depClassMaster;
	}
	public  void setDepClassMaster(DepClassMaster depClassMaster) {
		this.depClassMaster = depClassMaster;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REF_APP_ID", nullable = false)
	public StaPersonal getStaPersonal() {
		return staPersonal;
	}
	
	
	public void setStaPersonal(StaPersonal staPersonal) {
		this.staPersonal = staPersonal;
	}
    
}
