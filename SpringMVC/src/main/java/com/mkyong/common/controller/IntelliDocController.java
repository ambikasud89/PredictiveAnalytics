package com.mkyong.common.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mkyong.common.bean.PatientReport;

@Controller
@RequestMapping("/demo")
public class IntelliDocController {

	private static SparkConf sparkConf;
	private static JavaSparkContext jsc;
	private static SQLContext sqlContext;
	private static DataFrame patientDataFrame;

	private IntelliDocController() {
		if (sparkConf == null) {
			sparkConf = new SparkConf().setMaster("local[2]").setAppName(
					"IntelliDoctor");
			jsc = new JavaSparkContext(sparkConf);
			sqlContext = new SQLContext(jsc);
			patientDataFrame = sqlContext.jsonFile(path);
		}
	}

	public static Map<String, String> patientSymptom = new HashMap<>();
	private static String path = "C:\\Users\\amby\\226_final\\new\\src\\com\\sahoo\\generator\\inputData.txt";

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public List<PatientReport> getPatientRecord(@PathVariable String name,
			ModelMap model) {// Filtering patients with Alzheimer's disease
		patientDataFrame.show();
		System.out.println("-----------------------------");
		patientDataFrame.filter(patientDataFrame.col("name").equalTo(name))
				.show();

		List<Row> patientDataTemp = patientDataFrame.filter(
				patientDataFrame.col("name").equalTo(name)).collectAsList();
		List<PatientReport> patientData = new ArrayList<PatientReport>();
		for (Row r : patientDataTemp) {
			PatientReport pr = new PatientReport(r.getLong(0), r.getString(1),
					r.getString(2), r.getString(3), r.getString(4),
					r.getString(5), r.getString(6));
			patientData.add(pr);
		}

		System.out.println("size-->" + patientData.size());

		return patientData;

	}

	// DiSEASE DATA FILE
	private static String pathDisease = "C:\\Users\\amby\\Downloads\\SUSH\\SpringMVC\\src\\main\\resources\\diseases.data";

	@RequestMapping(value = "/disease/{diseaseName}", method = RequestMethod.GET)
	public List<String> getPatientNames(@PathVariable String diseaseName,
			ModelMap model) {// Filtering patients with Alzheimer's disease
		System.out.println("-----------------------------");

		List<Row> patientDataTemp = patientDataFrame
				.filter(patientDataFrame.col("symptoms").equalTo("Mood swings"))
				.select(patientDataFrame.col("name"))
				.intersect(
						patientDataFrame.filter(
								patientDataFrame.col("symptoms").equalTo(
										"Memory Loss")).select(
								patientDataFrame.col("name")))
				.intersect(
						patientDataFrame.filter(
								patientDataFrame.col("symptoms").equalTo(
										"Poor Judgement")).select(
								patientDataFrame.col("name"))).collectAsList();

		List<String> patientName = new ArrayList<String>();
		for (Row r : patientDataTemp) {
			PatientReport pr = new PatientReport(r.getLong(0), r.getString(1),
					r.getString(2), r.getString(3), r.getString(4),
					r.getString(5), r.getString(6));
			patientName.add(pr.getName());
		}

		return patientName;

	}

	// DISEASE MAPPED WITH SYMPTOMS DATA FILE
	private static String pathDiseaseMapSymptoms = "C:\\Users\\amby\\Downloads\\SUSH\\SpringMVC\\src\\main\\resources\\diseaseMapSymptoms.data";

	@RequestMapping(value = "/diagnostic/{name}", method = RequestMethod.GET)
	public List<String> getPatientDisease(@PathVariable String name,
			ModelMap model) throws JsonIOException, JsonSyntaxException,
			FileNotFoundException {// mapping symptoms with disease

		List<PatientReport> patientHistory = getPatientRecord(name, model);
		Map<String, List<String>> hashMap = new HashMap<>();

		Gson gson = new Gson();
		// Object things = gson.fromJson(new FileReader(pathDiseaseMapSymptoms),
		// Object.class);

		Type listType = new TypeToken<HashMap<String, List<String>>>() {
		}.getType();

		Map<String, List<String>> myList = gson.fromJson(new FileReader(
				pathDiseaseMapSymptoms), listType);

		for (String key : myList.keySet()) {

			List<String> symptoms = myList.get(key);
			hashMap.put(key, symptoms);
		}

		List<String> patientSymptoms = new ArrayList<String>();
		for (PatientReport r : patientHistory) {

			patientSymptoms.add(r.getSymptoms());
		}

		List<String> patientDiseaseList = new ArrayList<String>();
		for (String nkey : hashMap.keySet()) {
			boolean isExists = true;

			List<String> symptoms = hashMap.get(nkey);
			for (String string : symptoms) {
				if (!patientSymptoms.contains(string)) {
					isExists = false;
					break;
				}

			}
			System.out.println(nkey);
			if (isExists) {
				patientDiseaseList.add(nkey);
			}
		}

		return patientDiseaseList;

	}

}