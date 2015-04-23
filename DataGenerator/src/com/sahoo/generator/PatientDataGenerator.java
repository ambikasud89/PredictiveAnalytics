package com.sahoo.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PatientDataGenerator {

	public static void main(String[] args) {

		File file = new File("C:\\Users\\amby\\226_final\\new\\src\\com\\sahoo\\generator\\inputData.txt");
		
		BufferedWriter br = null;

		try {
			br = new BufferedWriter(new FileWriter(file));
			PatientDataGeneratorUtility pdgu = new PatientDataGeneratorUtility();
			for (int i = 0; i < 25; i++) {
				String name = pdgu.getName();
				String record = "{\"id\":" + pdgu.getId() + ",\"date\":\""
						+ pdgu.getVisitDates() + "\",\"patientId\":\""
						+ pdgu.getPatientId(name) + "\",\"name\":\"" + name
						+ "\",\"age\":" + pdgu.getAge() + ",\"symptoms\":\""
						+ pdgu.getSymptoms() + "\"}";
				if (i < 25) {
					record = record.concat(",");
				}
				br.write(record);
				br.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
