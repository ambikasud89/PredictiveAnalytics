package com.mkyong.common.bean;
import java.sql.Date;
import java.util.List;

public class PatientReport {

	String id;
	String date;
	String patientId;
	String name;
	long age;
	String gender;
	String symptom;

	public PatientReport(long age, String id, String date, String patientId,
			String name, String gender, String symptom) {

		this.id = id;
		this.date = date;
		this.patientId = patientId;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.symptom = symptom;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSymptoms() {
		return symptom;
	}

	public void setSymptoms(String symptoms) {
		this.symptom = symptom;
	}

}
