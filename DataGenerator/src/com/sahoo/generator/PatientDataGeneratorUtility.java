package com.sahoo.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientDataGeneratorUtility {

	private static long num = 1000;
	private static int patientIdNum = 1;
	private static long beginTime = Timestamp.valueOf("2013-01-01 00:00:00")
			.getTime();
	private static long endTime = Timestamp.valueOf("2013-12-31 00:58:00")
			.getTime();

	private static int beginAge = 0;
	private static int endAge = 150;
	private static Map<String, String> patientIdMap = new HashMap<>();
	private static final String ID_PREFIX = "P_00";
	private static List<String> symptoms = new ArrayList<>();

	public long getId() {

		return num++;
	}

	public String getVisitDates() {
		long diff = endTime - beginTime + 1;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    String date = sdf.format(new Date(beginTime + (long) (Math.random() * diff))); 
		return  date ;
	}

	public String getPatientId(String name) {
		if (patientIdMap != null) {
			return patientIdMap.get(name);

		} else {
			try {
				loadPatientIds();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return patientIdMap.get(name);
		}

	}

	private void loadPatientIds() throws IOException {
		File file = new File("C:\\Users\\amby\\226_final\\new\\src\\patientsnames.data");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String name = br.readLine();
		int i = 1;
		while (name != null) {
			patientIdMap.put(name, ID_PREFIX + i++);
			name = br.readLine();

		}

	}

	public String getName() {
		if (patientIdMap.size() == 0)
		{
			try {
				loadPatientIds();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		List<String> patientNames = new ArrayList<>();
		patientNames.addAll(patientIdMap.keySet());

		return patientNames.get((int) (Math.random() * (patientNames.size()-1)));
	}

	public int getAge() {
		return (int) num;
		//return ((int) (Math.random() * (endAge - beginAge)));

	}

	/*
	 * private String getGender() { return "M"; }
	 */
	public String getSymptoms() {
		if (symptoms.size()>0) {
			return symptoms.get((int) (Math.random() * symptoms.size()));
		} else {
			try {
				loadSymptoms();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return symptoms.get((int) (Math.random() * symptoms.size()));
		}
	}

	private void loadSymptoms() throws IOException {
		File file = new File("C:\\Users\\amby\\226_final\\new\\src\\symptoms.data");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String symptom = br.readLine();
		while (symptom != null) {
			symptoms.add(symptom);
			symptom = br.readLine();
		}

	}

}
