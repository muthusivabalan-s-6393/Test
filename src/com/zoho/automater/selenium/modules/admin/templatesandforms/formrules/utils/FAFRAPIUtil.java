// $Id$
package com.zoho.automater.selenium.modules.admin.templatesandforms.formrules.utils;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zoho.automater.selenium.base.client.api.RestAPI;
import com.zoho.automater.selenium.base.common.LocalStorage;
import com.zoho.automater.selenium.base.utils.AutomaterUtil;
import com.zoho.automater.selenium.base.utils.DataUtil;
import com.zoho.automater.selenium.base.utils.Utilities;
import com.zoho.automater.selenium.modules.admin.automation.customactions.timer.common.TimerDataConstants;

public class FAFRAPIUtil extends Utilities {
	
	public final static String PATH = AutomaterUtil.getResourceFolderPath() + "data" + File.separator + "admin" + File.separator + "templatesandforms" + File.separator + "form_rules" + File.separator + "form_rules_data.json";
	
	public static void getDesktopHardwareIdAndName() throws Exception {
		JSONObject listInfo = DataUtil.getTestCaseData(TimerDataConstants.TimerData.LIST_INFO_CRITERIA_NAME_SEARCH);
		listInfo.getJSONObject("search_criteria").put("value", "Desktop Hardware");
		
		// to get the categories -desktop hardware id
		JSONObject categoryJson = RestAPI.getInstance().getDataUsingSearchCriteria("categories", listInfo);
		String categoryId = AutomaterUtil.getValueAsStringFromInputUsingAPIPath(categoryJson, "categories[0].id");
		LocalStorage.store("categoriesId", categoryId);
		
		// to get the categories -desktop hardware name
		String categoryName = AutomaterUtil.getValueAsStringFromInputUsingAPIPath(categoryJson, "list_info.search_criteria.value");
		LocalStorage.store("categoryName", categoryName);
	}
	
	public static void createFAFR(String dataIds) throws Exception {
		JSONObject fafrData = DataUtil.getTestCaseDataUsingFilePath(PATH, dataIds);
		JSONObject fafr = null;
		
		if(fafrData.has("change_fafr")) {
			fafr = restAPI.createAndGetResponse("change_fafr", "change_fafr", fafrData);
		}else if(fafrData.has("problem_fafr")) {
			fafr = restAPI.createAndGetResponse("problem_fafr", "problem_fafr", fafrData);
		}else if(fafrData.has("release_fafr")) {
			fafr = restAPI.createAndGetResponse("release_fafr", "release_fafr", fafrData);
		}else if(fafrData.has("request_fafr")) {
			fafr = restAPI.createAndGetResponse("request_fafr", "request_fafr", fafrData);
		}
		actions.waitForAjaxComplete();
		if(fafr != null) {
			LocalStorage.store("fafrId", fafr.optString("id"));
			LocalStorage.store("fafrName", fafr.optString("name"));
			if(fafr.optJSONArray("criteria")!=null) {
				String title = fafr.getJSONArray("criteria").getJSONObject(0).getJSONArray("values").getString(0);
				LocalStorage.store("fafrTitleName", title);
			}
		}
	}
	
	public static void getSoftwareIdAndName() throws Exception {
		// Category -Software
		JSONObject listInfo = DataUtil.getTestCaseData(TimerDataConstants.TimerData.LIST_INFO_CRITERIA_NAME_SEARCH);
		listInfo.getJSONObject("search_criteria").put("value", "Software");
		
		// to get the categories -software id
		JSONObject categoryJson = RestAPI.getInstance().getDataUsingSearchCriteria("categories", listInfo);
		String softwareId = AutomaterUtil.getValueAsStringFromInputUsingAPIPath(categoryJson, "categories[0].id");
		LocalStorage.store("softwareId", softwareId);
		
		// to get the categories -Software name
		String categoryName = AutomaterUtil.getValueAsStringFromInputUsingAPIPath(categoryJson, "list_info.search_criteria.value");
		LocalStorage.store("softwareName", categoryName);
	}
	
	public static void createRequestFAFR(String dataIds) throws Exception {
		JSONObject fafrData = DataUtil.getTestCaseDataUsingFilePath(PATH, dataIds);
		JSONObject fafr = restAPI.createAndGetResponse("request_fafr", "request_fafr", fafrData);
		actions.waitForAjaxComplete();
		if(fafr != null) {
			LocalStorage.store("fafrId", fafr.optString("id"));
			LocalStorage.store("fafrName", fafr.optString("name"));
		}else {
			throw new Exception("Failed to create FAFR rule. API returned null response for request_fafr");
		}
	}
	
	public static void createReleaseFAFR(String dataIds) throws Exception {
		JSONObject fafrData = DataUtil.getTestCaseDataUsingFilePath(PATH, dataIds);
		JSONObject fafr = restAPI.createAndGetResponse("release_fafr", "release_fafr", fafrData);
		actions.waitForAjaxComplete();
		LocalStorage.store("fafrId", fafr.optString("id"));
		LocalStorage.store("fafrName", fafr.optString("name"));
	}
	
	public static void createRequestFAFR2(JSONObject fafrData) throws Exception {
		JSONObject fafr = restAPI.createAndGetResponse("request_fafr", "request_fafr", fafrData);
		actions.waitForAjaxComplete();
		if(fafr != null) {
			LocalStorage.store("fafrId", fafr.optString("id"));
			LocalStorage.store("fafrName", fafr.optString("name"));
		}else {
			throw new Exception("Failed to create FAFR rule. API returned null response for request_fafr");
		}
	}
	
	public static JSONObject buildOptionAction(String actionKey, String fieldName, String optionId, String optionValue) throws JSONException {
		JSONObject actionObj = new JSONObject();
		
		/* ---------- SIMPLE FIELD-ONLY ACTIONS ---------- */
		if("hide_fields".equals(actionKey) || "show_fields".equals(actionKey) || "clear_field_value".equals(actionKey)
				|| "disable_fields".equals(actionKey) || "enable_fields".equals(actionKey)
				|| "mandate_fields".equals(actionKey) || "non_mandate_fields".equals(actionKey)
				|| "clear_options".equals(actionKey)) {
			JSONArray fieldsArray = new JSONArray();
			fieldsArray.put(fieldName);
			
			actionObj.put(actionKey, fieldsArray);
			return actionObj;
		}
		
		/* ---------- VALUE-BASED ACTIONS ---------- */
		JSONObject optionObj = new JSONObject();
		optionObj.put("field", fieldName);
		
		if("set_value".equals(actionKey) && "email_ids_to_notify".equals(fieldName)) {
			// email_ids_to_notify uses flat "value" (email address) directly, no "id" or "values" wrapper
			optionObj.put("value", optionValue);
		}else if("set_value".equals(actionKey)) {
			JSONObject valueObj = new JSONObject();
			valueObj.put("id", optionId);
			valueObj.put("value", optionValue);
			optionObj.put("values", valueObj); // OBJECT
		}else {
			JSONObject valueObj = new JSONObject();
			valueObj.put("id", optionId);
			valueObj.put("value", optionValue);
			
			JSONArray valuesArray = new JSONArray();
			valuesArray.put(valueObj);
			optionObj.put("values", valuesArray); // ARRAY
		}
		
		JSONArray optionArray = new JSONArray();
		optionArray.put(optionObj);
		
		actionObj.put(actionKey, optionArray);
		return actionObj;
	}
	
	// On Form Load
	public static void createIncidentFAFRWithAction(String jsonFile, String actionKey, String fieldName, String optionId, String optionValue) throws Exception {
		JSONObject inputData = DataUtil.getTestCaseDataUsingFilePath(PATH, jsonFile);
		JSONArray actionsArray = inputData.getJSONObject("request_fafr").getJSONArray("actions");
		actionsArray.put(buildOptionAction(actionKey, fieldName, optionId, optionValue));
		FAFRAPIUtil.createRequestFAFR2(inputData);
	}
	
	// On Field Change
	public static void createIncidentFAFRWithActionOFC(String jsonFile, String onFieldChange, String actionKey, String fieldName, String optionId, String optionValue) throws Exception {
		JSONObject inputData = DataUtil.getTestCaseDataUsingFilePath(PATH, jsonFile);
		inputData.getJSONObject("request_fafr").put("on_field_change", onFieldChange);
		JSONArray actionsArray = inputData.getJSONObject("request_fafr").getJSONArray("actions");
		actionsArray.put(buildOptionAction(actionKey, fieldName, optionId, optionValue));
		FAFRAPIUtil.createRequestFAFR2(inputData);
	}
	
}
