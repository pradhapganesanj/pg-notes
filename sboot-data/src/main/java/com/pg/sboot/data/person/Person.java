package com.pg.sboot.data.person;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Person")
@Table(name="tmp_person")
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name="p_id")
	Long id;


	@Column(name="f_name")
	String fName;
	
	@Column(name="m_name")
	String mName;
	
	@Column(name="l_name")
	String lName;
	
	Date dob;
	
	String sex;
	
	public Person() {
	}

	public String getfName() {
		return fName;
	}

	public String getmName() {
		return mName;
	}

	public String getlName() {
		return lName;
	}

	public Date getDob() {
		return dob;
	}

	public String getSex() {
		return sex;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String toString(){
		return this.getfName()+" , "+this.getmName()+" , "+this.getlName()+" , "+this.getSex()+" , "+this.getDob();
	}
}
