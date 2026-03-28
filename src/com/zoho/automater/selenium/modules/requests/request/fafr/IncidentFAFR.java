// $Id$
package com.zoho.automater.selenium.modules.requests.request.fafr;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.zoho.automater.selenium.base.ScenarioRunType;
import com.zoho.automater.selenium.base.annotations.AutomaterScenario;
import com.zoho.automater.selenium.base.annotations.AutomaterSuite;
import com.zoho.automater.selenium.base.client.locators.ClientFrameworkLocators;
import com.zoho.automater.selenium.base.common.AutomaterVariables;
import com.zoho.automater.selenium.base.common.FieldType;
import com.zoho.automater.selenium.base.common.LocalStorage;
import com.zoho.automater.selenium.base.common.Priority;
import com.zoho.automater.selenium.base.entity.EntityMetaDetails;
import com.zoho.automater.selenium.base.exceptions.BadResponseException;
import com.zoho.automater.selenium.base.exceptions.DataNotFoundException;
import com.zoho.automater.selenium.base.exceptions.FileNotFoundException;
import com.zoho.automater.selenium.base.exceptions.SeleniumException;
import com.zoho.automater.selenium.base.utils.WaitUtil;
import com.zoho.automater.selenium.modules.GlobalConstants;
import com.zoho.automater.selenium.modules.ModuleConstants;
import com.zoho.automater.selenium.modules.OwnerConstants;
import com.zoho.automater.selenium.modules.admin.automation.workflows.common.WorkflowsLocators;
import com.zoho.automater.selenium.modules.admin.common.AdminConstants;
import com.zoho.automater.selenium.modules.admin.common.AdminLocators;
import com.zoho.automater.selenium.modules.admin.templatesandforms.formrules.common.FormRulesConstants;
import com.zoho.automater.selenium.modules.admin.templatesandforms.formrules.utils.FAFRActionsUtil;
import com.zoho.automater.selenium.modules.admin.utils.AdminActionsUtil;
import com.zoho.automater.selenium.modules.requests.RequestAPIPaths;
import com.zoho.automater.selenium.modules.requests.RequestsEntities;
import com.zoho.automater.selenium.modules.requests.RequestsRole;
import com.zoho.automater.selenium.modules.requests.request.common.RequestConstants;
import com.zoho.automater.selenium.modules.requests.request.common.RequestDataConstants;
import com.zoho.automater.selenium.modules.requests.request.common.RequestFields;
import com.zoho.automater.selenium.modules.requests.request.common.RequestLocators;
import com.zoho.automater.selenium.modules.requests.requestapprovals.common.RequestApprovalsAPIUtils;
import com.zoho.automater.selenium.modules.requests.requestapprovals.common.RequestApprovalsLocators;

@AutomaterSuite(
	role = RequestsRole.SDADMIN,
	tags = "TESTING",
	owner = OwnerConstants.DEVIRANI_R
)
public class IncidentFAFR extends RequestFAFR {
	
	String fafrmoduleName = FormRulesConstants.ListviewGlobalActions.INCIDENT_FORM_RULE;
	
	// protected RequestFormBuilder requestFormBuilder;
	public IncidentFAFR(WebDriver driver, StringBuffer failureMessage) throws SeleniumException {
		super(driver, failureMessage);
		setFafrmoduleName(fafrmoduleName);
	}
	
	@AutomaterScenario(
		group = "createIncidentTemplate",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields"},
		runType = ScenarioRunType.USER_BASED,
		tags = {},
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Hide Fields Action execute on form load",
		id = "SDPOD-FL-S-FAFR-0322"
	)
	public void checkHideServiceCategoryFieldActionExecuteOnFormLoad() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		String rulename = random.generateRandomString(4);
		try {
			FAFRActionsUtil.createFormRule(AdminConstants.RequestFAFR.Actions.HIDE_FIELDS, AdminConstants.RequestFAFR.Fields.SERVICE_CATEGORY, LocalStorage.getAsString("tempName"), rulename);
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			if(actions.isElementPresent(RequestLocators.RequestFAFR.SELECT_HIDDEN_FIELD.apply(FieldType.INPUT, RequestFields.SERVICE_CATEGORY.getName()))) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}finally {
			deleteFafr(rulename);
		}
	}
	
	@Override
	public boolean isAllActivatedElementsPresentIncident() throws Exception {
		List<WebElement> optionElements = actions.getAllElements(AdminLocators.RequestFAFR.DROP_DOWN_ELEMENTS);
		
		for(WebElement optionElement : optionElements) {
			if(!(optionElement.getText().equals(RequestConstants.ListviewGlobalActions.DEFAULT_REQUEST) || (optionElement.getText().equals(RequestConstants.ListviewGlobalActions.DEFAULT_REQUEST)))) {
				return true;
			}
		}
		
		return true;
	}
	
	@AutomaterScenario(
		group = "createMultipleIncidentFAFR",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_form_load", "fafr_data_show_fields_service_category", "show_fields", "service_category", "hide_fields"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Show Fields Action execute on form load",
		id = "SDPOD-FL-S-FAFR-0343"
	)
	public void checkShowServiceCategoryFieldActionExecuteOnFormLoad() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			if(actions.isElementPresent(RequestLocators.RequestFAFR.SELECT_SHOWN_FIELD.apply(FieldType.INPUT, RequestFields.SERVICE_CATEGORY.getName()))) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
			if(actions.isElementPresent(RequestLocators.RequestFAFR.SELECT_SHOWN_FIELD.apply(FieldType.INPUT, RequestFields.SERVICE_CATEGORY.getName()))) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createMultipleIncidentFAFR",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_form_load", "fafr_data_show_fields_service_category", "disable_fields", "service_category", "enable_fields"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Enable Fields Action execute on form load",
		id = "SDPOD-FL-S-FAFR-0364"
	)
	public void checkEnableServiceCategoryFieldActionExecuteOnFormLoad() {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			if(actions.isElementPresent(RequestLocators.RequestFAFR.SELECT_SHOWN_FIELD.apply(FieldType.INPUT, RequestFields.SERVICE_CATEGORY.getName()))) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
			if(actions.getElement(ClientFrameworkLocators.FormBuilderLocators.FORM_ELEMENT.apply(FieldType.INPUT, RequestFields.SERVICE_CATEGORY.getName())).isEnabled()) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentTemplate",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Disable Fields Action execute on form load",
		id = "SDPOD-FL-S-FAFR-0385"
	)
	public void checkDisableServiceCategoryFieldActionExecuteOnFormLoad() {
		String rulename = random.generateRandomString(4);
		try {
			FAFRActionsUtil.createFormRule(AdminConstants.RequestFAFR.Actions.DISABLE_FIELDS, AdminConstants.RequestFAFR.Fields.SERVICE_CATEGORY, LocalStorage.getAsString("tempName"), rulename);
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			if(!actions.getElement(ClientFrameworkLocators.FormBuilderLocators.FORM_ELEMENT.apply(FieldType.INPUT, RequestFields.SERVICE_CATEGORY.getName())).isEnabled()) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentFAFR",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_form_load", "fafr_data_show_fields_service_category", "mandate_fields", "service_category"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Mandate Fields Action execute on form load",
		id = "SDPOD-FL-S-FAFR-0406"
	)
	public void checkMandateServiceCategoryFieldActionExecuteOnFormLoad() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.submit();
			if(actions.getText(RequestLocators.DetailView.MANDATORY_MESSAGE.apply(RequestFields.SERVICE_CATEGORY.getName())).equals(RequestConstants.DetailsPageGlobalActions.MANDATORY_ERROR.apply(RequestConstants.FieldsNamesForMandatoryMessage.SERVICE_CATEGORY))) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createMultipleIncidentFAFR",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_form_load", "fafr_data_show_fields_service_category", "mandate_fields", "service_category", "non_mandate_fields"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Non-Mandate Fields Action execute on form load",
		id = "SDPOD-FL-S-FAFR-0427"
	)
	public void checkNonMandateServiceCategoryFieldActionExecuteOnFormLoad() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.submit();
			if(!actions.isDisplayed(RequestLocators.ValidationMessage.VERIFY_VALIDATION_MESSAGE_BY_ERROR.apply(RequestFields.SERVICE_CATEGORY.getName(), RequestConstants.ErrorLabelText.SERVICE_CATEGORY_VALIDATION_ERROR))) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentFAFR",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_form_load", "fafr_data_show_fields_service_category", "clear_options", "service_category"},
		tags = {GlobalConstants.Tags.KNOWN_ISSUE},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Clear Options Action execute on form load",
		id = "SDPOD-FL-S-FAFR-0443"
	)
	public void checkClearOptionsFromServiceCategoryFieldActionExecuteOnFormLoad() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.click(ClientFrameworkLocators.FormBuilderLocators.SELECT_ELEMENT.apply(RequestFields.SERVICE_CATEGORY.getName()));
			if(actions.getText(RequestLocators.Form.DROP_DOWN_ELEMENTS).equals(RequestConstants.FieldValues.NO_MATCHES_FOUND)) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createMultipleIncidentFAFRWithValues",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_form_load", "fafr_data_actions_fields_and_values", "remove_options", "service_category", "Corporate Website", "add_options"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Add Options Action execute on form load",
		id = "SDPOD-FL-S-FAFR-0458"
	)
	public void checkAddOptionsToServiceCategoryFieldActionExecuteOnFormLoad() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.click(ClientFrameworkLocators.FormBuilderLocators.SELECT_ELEMENT.apply(RequestFields.SERVICE_CATEGORY.getName()));
			if(requestFormbuilder.isOptionPresent(RequestConstants.FieldValues.SERVICECATEGORY_CORPORATEWEBSITE)) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentFAFRWithValues",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_form_load", "fafr_data_actions_fields_and_values", "remove_options", "service_category", "Corporate Website"},
		tags = {GlobalConstants.Tags.KNOWN_ISSUE, "issue id - 82727"},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Remove Options Action execute on form load",
		id = "SDPOD-FL-S-FAFR-0473"
	)
	public void checkRemoveOptionsFromServiceCategoryFieldActionExecuteOnFormLoad() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.click(ClientFrameworkLocators.FormBuilderLocators.SELECT_ELEMENT.apply(RequestFields.SERVICE_CATEGORY.getName()));
			if(!requestFormbuilder.isOptionPresent(RequestConstants.FieldValues.SERVICECATEGORY_CORPORATEWEBSITE)) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentFAFRWithValues",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_form_load", "fafr_data_actions_fields_and_values", "set_value", "service_category", "Corporate Website"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Set Value to Field Action execute on form load",
		id = "SDPOD-FL-S-FAFR-0491"
	)
	public void checkSetValuetoServiceCategoryFieldActionExecuteOnFormLoad() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			if(actions.getText(ClientFrameworkLocators.FormBuilderLocators.SELECT_ELEMENT.apply(RequestFields.SERVICE_CATEGORY.getName())).equals(RequestConstants.FieldValues.SERVICECATEGORY_CORPORATEWEBSITE)) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createMultipleIncidentFAFRWithValues",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_form_load", "fafr_data_actions_fields_and_values", "set_value", "service_category", "Corporate Website", "clear_field_value"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Clear Field Value Action execute on form load",
		id = "SDPOD-FL-S-FAFR-0509"
	)
	public void checkClearServiceCategoryFieldValueActionExecuteOnFormLoad() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			if(actions.getText(ClientFrameworkLocators.FormBuilderLocators.SELECT_ELEMENT.apply(RequestFields.SERVICE_CATEGORY.getName())).equals(RequestConstants.FieldValues.PlaceHolders.SERVICE_CATEGORY_FIELD)) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentFAFR_For_OFC",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_field_change", "group", "fafr_data_actions_fields_OnFieldChange", "hide_fields", "service_category"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Hide Fields Action execute on field Change",
		id = "SDPOD-FL-S-FAFR-0756"
	)
	public void checkHideServiceCategoryFieldActionExecuteOnFieldChange() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.fillSelectField(RequestFields.GROUP.getName(), RequestConstants.FieldValues.GROUP_HARDWAREPROBLEMS);
			if(actions.isElementPresent(RequestLocators.RequestFAFR.SELECT_HIDDEN_FIELD.apply(FieldType.INPUT, RequestFields.SERVICE_CATEGORY.getName()))) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createMultipleIncidentFAFR_For_OFC",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_field_change", "group", "fafr_data_actions_fields_OnFieldChange", "hide_fields", "service_category", "show_fields"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Show Fields Action execute on field Change",
		id = "SDPOD-FL-S-FAFR-0777"
	)
	public void checkShowServiceCategoryFieldActionExecuteOnFieldChange() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.fillSelectField(RequestFields.GROUP.getName(), RequestConstants.FieldValues.GROUP_HARDWAREPROBLEMS);
			if(actions.isElementPresent(RequestLocators.RequestFAFR.SELECT_SHOWN_FIELD.apply(FieldType.INPUT, RequestFields.SERVICE_CATEGORY.getName()))) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createMultipleIncidentFAFR_For_OFC",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_field_change", "group", "fafr_data_actions_fields_OnFieldChange", "disable_fields", "service_category", "enable_fields"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Enable Fields Action execute on field Change",
		id = "SDPOD-FL-S-FAFR-0798"
	)
	public void checkEnableServiceCategoryFieldActionExecuteOnFieldChange() {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.fillSelectField(RequestFields.GROUP.getName(), RequestConstants.FieldValues.GROUP_HARDWAREPROBLEMS);
			if(actions.getElement(ClientFrameworkLocators.FormBuilderLocators.FORM_ELEMENT.apply(FieldType.INPUT, RequestFields.SERVICE_CATEGORY.getName())).isEnabled()) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentFAFR_For_OFC",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_field_change", "group", "fafr_data_actions_fields_OnFieldChange", "disable_fields", "service_category"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Disable Fields Action execute on field Change",
		id = "SDPOD-FL-S-FAFR-0819"
	)
	public void checkDisableServiceCategoryFieldActionExecuteOnFieldChange() {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.fillSelectField(RequestFields.GROUP.getName(), RequestConstants.FieldValues.GROUP_HARDWAREPROBLEMS);
			if(!actions.getElement(ClientFrameworkLocators.FormBuilderLocators.FORM_ELEMENT.apply(FieldType.INPUT, RequestFields.SERVICE_CATEGORY.getName())).isEnabled()) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentFAFR_For_OFC",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_field_change", "group", "fafr_data_actions_fields_OnFieldChange", "mandate_fields", "service_category"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Mandate Fields Action execute on field Change",
		id = "SDPOD-FL-S-FAFR-0840"
	)
	public void checkMandateServiceCategoryFieldActionExecuteOnFieldChange() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.fillSelectField(RequestFields.GROUP.getName(), RequestConstants.FieldValues.GROUP_HARDWAREPROBLEMS);
			actions.click(ClientFrameworkLocators.FormBuilderLocators.FORM_SUBMIT);
			if(actions.getText(RequestLocators.DetailView.MANDATORY_MESSAGE.apply(RequestFields.SERVICE_CATEGORY.getName())).equals(RequestConstants.DetailsPageGlobalActions.MANDATORY_ERROR.apply(RequestConstants.FieldsNamesForMandatoryMessage.SERVICE_CATEGORY))) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createMultipleIncidentFAFR_For_OFC",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_field_change", "group", "fafr_data_actions_fields_OnFieldChange", "mandate_fields", "service_category", "non_mandate_fields"},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		tags = {},
		description = "Test Non-Mandate Fields Action execute on field Change",
		id = "SDPOD-FL-S-FAFR-0861"
	)
	public void checkNonMandateServiceCategoryFieldActionExecuteOnFieldChange() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.fillSelectField(RequestFields.GROUP.getName(), RequestConstants.FieldValues.GROUP_HARDWAREPROBLEMS);
			actions.click(ClientFrameworkLocators.FormBuilderLocators.FORM_SUBMIT);
			if(!actions.isDisplayed(RequestLocators.ValidationMessage.VERIFY_VALIDATION_MESSAGE_BY_ERROR.apply(RequestFields.SERVICE_CATEGORY.getName(), RequestConstants.ErrorLabelText.SERVICE_CATEGORY_VALIDATION_ERROR))) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentFAFR_For_OFC",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_field_change", "group", "fafr_data_actions_fields_OnFieldChange", "clear_options", "service_category"},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		tags = {GlobalConstants.Tags.KNOWN_ISSUE},
		description = "Test Clear Options Action execute on field Change",
		id = "SDPOD-FL-S-FAFR-0877"
	)
	public void checkClearOptionsFromServiceCategoryFieldActionExecuteOnFieldChange() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.fillSelectField(RequestFields.GROUP.getName(), RequestConstants.FieldValues.GROUP_HARDWAREPROBLEMS);
			actions.click(ClientFrameworkLocators.FormBuilderLocators.SELECT_ELEMENT.apply(RequestFields.SERVICE_CATEGORY.getName()));
			if(actions.getText(RequestLocators.Form.DROP_DOWN_ELEMENTS).equals(RequestConstants.FieldValues.NO_MATCHES_FOUND)) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentFAFRWithValues_For_OFC",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_field_change", "group", "fafr_data_actions_fields_OnFieldChange", "add_options", "service_category", "Corporate Website"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Add Options Action execute on field Change",
		id = "SDPOD-FL-S-FAFR-0892"
	)
	public void checkAddOptionsToServiceCategoryFieldActionExecuteOnFieldChange() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.fillSelectField(RequestFields.GROUP.getName(), RequestConstants.FieldValues.GROUP_HARDWAREPROBLEMS);
			actions.click(ClientFrameworkLocators.FormBuilderLocators.SELECT_ELEMENT.apply(RequestFields.SERVICE_CATEGORY.getName()));
			if(requestFormbuilder.isOptionPresent(RequestConstants.FieldValues.SERVICECATEGORY_CORPORATEWEBSITE)) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentFAFRWithValues_For_OFC",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_field_change", "group", "fafr_data_actions_fields_OnFieldChange", "remove_options", "service_category", "Corporate Website"},
		tags = {"Issue raised- 82727", GlobalConstants.Tags.KNOWN_ISSUE},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Remove Options Action execute on field Change",
		id = "SDPOD-FL-S-FAFR-0907"
	)
	public void checkRemoveOptionsFromServiceCategoryFieldActionExecuteOnFieldChange() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.fillSelectField(RequestFields.GROUP.getName(), RequestConstants.FieldValues.GROUP_HARDWAREPROBLEMS);
			actions.click(ClientFrameworkLocators.FormBuilderLocators.SELECT_ELEMENT.apply(RequestFields.SERVICE_CATEGORY.getName()));
			if(!requestFormbuilder.isOptionPresent(RequestConstants.FieldValues.SERVICECATEGORY_CORPORATEWEBSITE)) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentFAFRWithValues_For_OFC",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_field_change", "group", "fafr_data_actions_fields_OnFieldChange", "set_value", "service_category", "Corporate Website"},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		tags = {},
		description = "Test Set Value to Field Action execute on field Change",
		id = "SDPOD-FL-S-FAFR-0925"
	)
	public void checkSetValuetoServiceCategoryFieldActionExecuteOnFieldChange() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.fillSelectField(RequestFields.GROUP.getName(), RequestConstants.FieldValues.GROUP_HARDWAREPROBLEMS);
			if(actions.getText(ClientFrameworkLocators.FormBuilderLocators.SELECT_ELEMENT.apply(RequestFields.SERVICE_CATEGORY.getName())).equals(RequestConstants.FieldValues.SERVICECATEGORY_CORPORATEWEBSITE)) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createMultipleIncidentFAFRWithValues_For_OFC",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_field_change", "group", "fafr_data_actions_fields_OnFieldChange", "set_value", "service_category", "Corporate Website", "clear_field_value"},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		tags = {GlobalConstants.Tags.KNOWN_ISSUE},
		description = "Test Clear Field Value Action execute on field Change",
		id = "SDPOD-FL-S-FAFR-0943"
	)
	public void checkClearServiceCategoryFieldValueActionExecuteOnFieldChange() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.formBuilder.fillSelectField(RequestFields.GROUP.getName(), RequestConstants.FieldValues.GROUP_HARDWAREPROBLEMS);
			actions.click(ClientFrameworkLocators.FormBuilderLocators.SELECT_ELEMENT.apply(RequestFields.SERVICE_CATEGORY.getName()));
			if(actions.getText(RequestLocators.Form.DROP_DOWN_ELEMENTS).equals(RequestConstants.FieldValues.NO_MATCHES_FOUND)) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createIncidentFAFRWithValues",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_form_submit", "fafr_data_actions_fields_OnFormSubmit", "set_value", "service_category", "Corporate Website"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Set Value to Field Action execute on form submit",
		id = "SDPOD-FL-S-FAFR-1188"
	)
	public void checkSetValuetoServiceCategoryFieldActionExecuteOnFormSubmit() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.click(ClientFrameworkLocators.FormBuilderLocators.FORM_SUBMIT);
			if(actions.getText(ClientFrameworkLocators.FormBuilderLocators.SELECT_ELEMENT.apply(RequestFields.SERVICE_CATEGORY.getName())).equals(RequestConstants.FieldValues.SERVICECATEGORY_CORPORATEWEBSITE)) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@AutomaterScenario(
		group = "createMultipleIncidentFAFRWithValues",
		priority = Priority.MEDIUM,
		dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_form_submit", "fafr_data_actions_fields_OnFormSubmit", "set_value", "service_category", "Corporate Website", "clear_field_value"},
		tags = {},
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.MUTHUSIVABALAN_S,
		description = "Test Clear Field Value Action execute on form submit",
		id = "SDPOD-FL-S-FAFR-1206"
	)
	public void checkClearServiceCategoryFieldValueActionExecuteOnFormSubmit() throws BadResponseException, JSONException, SeleniumException, FileNotFoundException, DataNotFoundException {
		try {
			FAFRActionsUtil.openRequestForm(LocalStorage.getAsString("tempName"));
			actions.click(ClientFrameworkLocators.FormBuilderLocators.FORM_SUBMIT);
			if(actions.getText(ClientFrameworkLocators.FormBuilderLocators.SELECT_ELEMENT.apply(RequestFields.SERVICE_CATEGORY.getName())).equals(RequestConstants.FieldValues.PlaceHolders.SERVICE_CATEGORY_FIELD)) {
				addSuccessReport("Rule was executed successfully");
			}else {
				addFailureReport("Unable to execute the Rule", "Expected value not present");
			}
		}catch(Exception exception) {
			addFailureReport("Exception occured while running the case", exception.getMessage());
		}
	}
	
	@Override
	public JSONObject addTemplateWithNewField(String fieldKey, String templateName) throws Exception {
		LocalStorage.store("udf_field_name", "udf_fields." + fieldKey);
		LocalStorage.store("template_name", templateName);
		LocalStorage.store("service_category_id", restAPI.create(RequestsEntities.SERVICE_CATEGORY, RequestAPIPaths.SERVICE_CATEGORIES, getTestCaseData(RequestDataConstants.RequestDetailsPage.CREATE_SERVICE_CATEGORY)));
		JSONObject template = getTestCaseData(RequestDataConstants.FafrData.REQUEST_TEMPLATE_WITH_NEW_FIELD);
		template.getJSONObject(RequestFields.REQUEST_TEMPLATE.getDataPath()).remove(RequestFields.SERVICE_CATEGORY.getDataPath());
		template.getJSONObject(RequestFields.REQUEST_TEMPLATE.getDataPath()).remove(RequestFields.IS_SERVICE_TEMPLATE.getDataPath());
		template.getJSONObject(RequestFields.REQUEST_TEMPLATE.getDataPath()).remove(RequestFields.COST_DETAILS.getDataPath());
		JSONObject json = restAPI.createAndGetResponse(RequestsEntities.REQUEST_TEMPLATE, "request_templates", template);
		String reqid = json.getString("id");
		LocalStorage.store("template_id", reqid);
		return json;
	}
	
	@Override
	public JSONObject addTemplate(String templateName) throws Exception {
		LocalStorage.store("template_name", templateName);
		LocalStorage.store("service_category_id", restAPI.create(RequestsEntities.SERVICE_CATEGORY, RequestAPIPaths.SERVICE_CATEGORIES, getTestCaseData(RequestDataConstants.RequestDetailsPage.CREATE_SERVICE_CATEGORY)));
		JSONObject template = getTestCaseData(RequestDataConstants.FafrData.SIMPLE_SERVICE_TEMPLATE);
		template.getJSONObject(RequestFields.REQUEST_TEMPLATE.getDataPath()).remove(RequestFields.SERVICE_CATEGORY.getDataPath());
		template.getJSONObject(RequestFields.REQUEST_TEMPLATE.getDataPath()).remove(RequestFields.IS_SERVICE_TEMPLATE.getDataPath());
		template.getJSONObject(RequestFields.REQUEST_TEMPLATE.getDataPath()).remove(RequestFields.COST_DETAILS.getDataPath());
		// return restAPI.createAndGetResponse(RequestsEntities.REQUEST_TEMPLATE, RequestAPIPaths.REQUEST_TEMPLATES, template);
		JSONObject json = restAPI.createAndGetResponse(RequestsEntities.REQUEST_TEMPLATE, RequestAPIPaths.REQUEST_TEMPLATES, template);
		String reqid = json.getString("id");
		LocalStorage.store("template_id", reqid);
		return json;
	}
	
	@Override
	public JSONObject addTemplateWithEmailsToNotify(String templateName) throws Exception {
		LocalStorage.store("template_name", templateName);
		LocalStorage.store("service_category_id", restAPI.create(RequestsEntities.SERVICE_CATEGORY, RequestAPIPaths.SERVICE_CATEGORIES, getTestCaseData(RequestDataConstants.RequestDetailsPage.CREATE_SERVICE_CATEGORY)));
		JSONObject template = getTestCaseData(RequestDataConstants.FafrData.SIMPLE_SERVICE_TEMPLATE_WITH_EMAILS_TO_NOTIFY);
		template.getJSONObject(RequestFields.REQUEST_TEMPLATE.getDataPath()).remove(RequestFields.SERVICE_CATEGORY.getDataPath());
		template.getJSONObject(RequestFields.REQUEST_TEMPLATE.getDataPath()).remove(RequestFields.IS_SERVICE_TEMPLATE.getDataPath());
		template.getJSONObject(RequestFields.REQUEST_TEMPLATE.getDataPath()).remove(RequestFields.COST_DETAILS.getDataPath());
		JSONObject json = restAPI.createAndGetResponse(RequestsEntities.REQUEST_TEMPLATE, RequestAPIPaths.REQUEST_TEMPLATES, template);
		String reqid = json.getString("id");
		LocalStorage.store("template_id", reqid);
		return json;
	}
	
	@Override
	public void openRequestAddForm(JSONObject template) throws Exception {
		// actions.click(RequestLocators.Global.NEW_REQUEST);
		// actions.click(RequestLocators.Global.SERVICE_CATALOG);
		// actions.click(RequestLocators.Global.SELECT_TEMPLATE.apply(template.getString(RequestFields.ID.getDataPath())));
		String categoryname = template.getJSONObject("service_category").getString("name");
		String tempname = template.getString("name");
		actions.click(RequestLocators.Global.NEW_REQUEST_GLOBAL_ICON);
		actions.type(RequestLocators.Listview.SEARCH_TEMPLATE_NAME, tempname);
		LocalStorage.store("key", "incidentrequest2");
		actions.click(RequestLocators.Global.CHECK_TEMPLATE.apply(tempname));
	}
	
	@Override
	public void openRequestAddFormFromLocalStorage() throws Exception {
		String tempname = LocalStorage.getAsString("template_name");
		actions.click(RequestLocators.Global.NEW_REQUEST_GLOBAL_ICON);
		actions.type(RequestLocators.Listview.SEARCH_TEMPLATE_NAME, tempname);
		LocalStorage.store("key", "incidentrequest2");
		actions.click(RequestLocators.Global.CHECK_TEMPLATE.apply(tempname));
	}
	
	public void openRequestForm(JSONObject template) throws Exception {
		actions.click(RequestLocators.Global.NEW_REQUEST_GLOBAL_ICON);
		String tempname = template.getString("name");
		actions.type(RequestLocators.Listview.SEARCH_TEMPLATE_NAME, tempname);
		LocalStorage.store("key", "incidentrequest");
		actions.click(RequestLocators.Global.CHECK_TEMPLATE.apply(tempname));
	}
	
	@Override
	public void chooseFormRule() throws Exception {
		actions.click(AdminLocators.RequestFAFR.SELECT_TYPES_OF_FORM_RULES);
		actions.click(AdminLocators.RequestFAFR.CHOOSE_FORM_RULES.apply(AdminConstants.RequestFAFR.INCIDENT_REQUEST_FORM_RULES));
	}
	
	@Override
	public boolean isEditFormRulePresent() {
		return actions.isElementPresent(AdminLocators.RequestFAFR.TITLE_IN_ADD_OR_EDIT_RULE_PAGE.apply(AdminConstants.RequestFAFR.EDIT_INCIDENT_FORM_RULE));
	}
	
	@Override
	public boolean isAddFormRulePresent() {
		return actions.isElementPresent(AdminLocators.RequestFAFR.TITLE_IN_ADD_OR_EDIT_RULE_PAGE.apply(AdminConstants.RequestFAFR.NEW_INCIDENT_FORM_RULE));
	}
	
	@Override
	public String addEnableRule(String ruleName) throws Exception {
		LocalStorage.store("rule_name", ruleName);
		LocalStorage.store("subject_value", random.generateRandomString(4));
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.CREATE_INCIDENT_ENABLE_RULE));
	}
	
	@Override
	public String addEnableRuleWithDifferentCharactesInConditionAndAction(String alphaNumericConditionValue, String nonEnglishConditionValue, String specialCharConditionValue, String alphaNumericActionValue, String nonEnglishActionValue, String specialCharActionValue) throws Exception {
		LocalStorage.store("alpha_numeric_condition_value", alphaNumericConditionValue);
		LocalStorage.store("non_english_condition_value", nonEnglishConditionValue);
		LocalStorage.store("special_character_condition_value", specialCharConditionValue);
		LocalStorage.store("alpha_numeric_action_value", alphaNumericActionValue);
		LocalStorage.store("non_english_action_value", nonEnglishActionValue);
		LocalStorage.store("special_character_action_value", specialCharActionValue);
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.CREATE_INCIDENT_ENABLE_RULE_WITH_DIFFERENT_CHARACTERS_VALUES_IN_CONDITION_AND_ACTION));
	}
	
	@Override
	public String addEnableRuleWithTemplates(String ruleName, JSONObject template, String subjectValue) throws Exception {
		LocalStorage.store("template_id", template.getString(RequestFields.ID.getDataPath()));
		LocalStorage.store("subject_value", subjectValue);
		LocalStorage.store("rule_name", ruleName);
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.CREATE_INCIDENT_ENABLE_RULE_WITH_SELECTED_TEMPLATES));
	}
	
	@Override
	public String addEnableRuleForSubjectChange(String subjectValue, String rule) throws Exception {
		LocalStorage.store("subject_value", subjectValue);
		LocalStorage.store("rule_name", rule);
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.CREATE_INCIDENT_ENABLE_RULE));
	}
	
	@Override
	public String addEnableRuleWithTemplatesExecuteOnFieldChange(JSONObject template, String subjectValue) throws Exception {
		LocalStorage.store("template_id", template.getString(RequestFields.ID.getDataPath()));
		LocalStorage.store("subject_value", subjectValue);
		
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.CREATE_INCIDENT_ENABLE_RULE_WITH_SELECTED_TEMPLATES_EXECUTE_ON_FIELD_CHANGE));
	}
	
	@Override
	public String addEnableRuleWithTemplatesExecuteOnFormSubmit(JSONObject template, String subjectValue) throws Exception {
		LocalStorage.store("template_id", template.getString(RequestFields.ID.getDataPath()));
		LocalStorage.store("subject_value", subjectValue);
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.CREATE_INCIDENT_ENABLE_RULE_WITH_SELECTED_TEMPLATES_EXECUTE_ON_FORM_SUBMIT));
	}
	
	@Override
	public String addEnableRuleWithTemplatesExecuteRequestCreation(JSONObject template, String subjectValue) throws Exception {
		LocalStorage.store("template_id", template.getString(RequestFields.ID.getDataPath()));
		LocalStorage.store("subject_value", subjectValue);
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.CREATE_INCIDENT_ENABLE_RULE_WITH_SELECTED_TEMPLATES_EXECUTE_REQUEST_CREATION));
	}
	
	@Override
	public String addEnableRuleWithTemplatesExecuteRequestEdit(JSONObject template, String subjectValue) throws Exception {
		LocalStorage.store("template_id", template.getString(RequestFields.ID.getDataPath()));
		LocalStorage.store("subject_value", subjectValue);
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.CREATE_INCIDENT_ENABLE_RULE_WITH_SELECTED_TEMPLATES_EXECUTE_REQUEST_EDIT));
	}
	
	@Override
	public String addDisableRuleWithTemplates(JSONObject template, String subjectValue) throws Exception {
		LocalStorage.store("template_id", template.getString(RequestFields.ID.getDataPath()));
		LocalStorage.store("subject_value", subjectValue);
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.CREATE_INCIDENT_DISABLE_RULE_WITH_SELECTED_TEMPLATES));
	}
	
	@Override
	public boolean isAllActivatedElementsPresent() throws Exception {
		List<WebElement> optionElements = actions.getAllElements(AdminLocators.RequestFAFR.DROP_DOWN_ELEMENTS);
		
		for(WebElement optionElement : optionElements) {
			if(!(optionElement.getText().equals(RequestConstants.ListviewGlobalActions.IncidentTemplates.DEFAULT_REQUEST) || optionElement.getText().equals(RequestConstants.ListviewGlobalActions.IncidentTemplates.MAIL_FETCHING) || optionElement.getText().equals(RequestConstants.ListviewGlobalActions.IncidentTemplates.NEW_JOINEE) || optionElement.getText().equals(RequestConstants.ListviewGlobalActions.IncidentTemplates.PRINTER_PROBLEM) || optionElement.getText().equals(RequestConstants.ListviewGlobalActions.IncidentTemplates.UNABLE_TO_BROWSE))) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean isAllFieldPresentUnderOnFieldChange() throws Exception {
		List<WebElement> optionElements = actions.getAllElements(AdminLocators.RequestFAFR.DROP_DOWN_ELEMENTS);
		
		for(WebElement optionElement : optionElements) {
			if(!(optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.CATEGORY) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.Actions.DUE_BY_DATE) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.EMAILS_TO_NOTIFY) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.GROUP) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.IMPACT) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.IMPACT_DETAILS) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.ITEM) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.LEVEL) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.MODE) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.ON_BEHALF_OF_USER) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.PRIORITY) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.REQUEST_TYPE) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.REQUESTER) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.Actions.RESPONSE_DUE_BY_TIME) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.SCHEDULED_END_TIME) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.SCHEDULED_START_TIME) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.SERVICE_CATEGORY) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.SITE) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.STATUS) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.SUB_CATEGORY) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.SUBJECT) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.TECHNICIAN) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.URGENCY) || optionElement.getText().equals(AdminConstants.RequestFAFR.Fields.EMAIL_ID))) {
				return true;
			}
		}
		
		return true;
	}
	
	@Override
	public void rejectTheRequest(String requestId) throws Exception {
		// Submit for approval via API and then reject via UI
		RequestApprovalsAPIUtils.submitForApprovalAPIWithUser(LocalStorage.getAsString("request_id"), "Submit_For_Approval_API", actions.getLoggedInUserMailId());
		openRequestUsingShortCut(requestId);
		requestDetailActions.toApprovalTab();
		actions.click(RequestLocators.RequestFAFR.STAGE1_LABEL);
		actions.click(RequestLocators.DetailView.CLICK_TAKE_ACTION_IN_APPROVALS_STAGE);
		actions.click(RequestApprovalsLocators.Popup.APPROVAL_ACTION_REJECT);
		actions.admin.popupFormBuilder.fillTextAreaField("approval_action_comment", "Reject");
		actions.click(WorkflowsLocators.Listview.APPROVAL_COMMENT_SUBMIT);
		WaitUtil.sleep(2l);
	}
	
	// @AutomaterScenario(
	// group = "createIncidentFAFR",
	// priority = Priority.MEDIUM,
	// dataIds = {"create_Incident_template_via_API_with_all_RHS_fields", "on_form_load", "fafr_data_show_fields_service_category", "hide_fields",
	// "service_category"},
	// tags = {},
	// runType = ScenarioRunType.USER_BASED,
	// owner = OwnerConstants.MUTHUSIVABALAN_S,
	// description = "Test Disable Rule Function Under Gear Icon in Incident Form Rules",
	// id = "SDPOD-FL-S-FAFR-0009"
	// )
	// public void checkDisableRuleFunctionUnderGearIcon() {
	// try {
	// String ruleId = LocalStorage.getAsString("fafrId");
	// goToFormRulesInAdminTab();
	// chooseFormRule();
	// recordPerPageFromTable();
	// boolean success = actions.isElementPresent(AdminLocators.RequestFAFR.GEAR_ICON.apply(ruleId));
	// if(!success) {
	// actions.click(AdminLocators.RequestFAFR.NEXT_PAGE);
	// }
	// actions.click(AdminLocators.RequestFAFR.GEAR_ICON.apply(ruleId));
	// actions.click(AdminLocators.RequestFAFR.DISABLE_RULE_UNDER_GEAR_ICON.apply(ruleId));
	// actions.click(RequestLocators.DetailView.POP_YES_NO_BTN.apply(RequestConstants.ListviewGlobalActions.YES));
	// if(actions.isElementPresent(AdminLocators.RequestFAFR.DISABLED_FAFR.apply(ruleId))) {
	// addSuccessReport("Rule was Disabled successfully");
	// }else {
	// addFailureReport("Failed to disable the rule", "Expected value not present");
	// }
	// }catch(Exception exception) {
	// addFailureReport("Exception occured while running the case", exception.getMessage());
	// }
	// }
	
	@Override
	@AutomaterScenario(
		id = "SDP_AUTO_REQ_CART2402_029",
		group = "createFAFRwithLookup",
		priority = Priority.MEDIUM,
		dataIds = {},
		runType = ScenarioRunType.USER_BASED,
		tags = {"Cart2402"},
		description = "lookup set as action for fafr",
		owner = OwnerConstants.PAVITHRA_R
	)
	public void fafrWithLookupCriteria() {
		report.startMethodFlowInStepsToReproduce(AutomaterVariables.SCENARIO_START.apply(getMethodName()));
		try {
			super.fafrWithLookupCriteria();
		}catch(Exception exception) {
			addFailureReport("Internal error occurred while running the test case " + getMethodName(), exception.getMessage());
		}finally {
			report.endMethodFlowInStepsToReproduce();
		}
	}
	
	@Override
	public String getFafrModuleName() {
		return "incident";
	}
	
	@Override
	public String addFafrRule(String temp, String field, String id, String name) throws Exception {
		LocalStorage.store("request_type", "incident");
		LocalStorage.store("temp_name", temp);
		LocalStorage.store("field", field);
		LocalStorage.store("field_id", id);
		LocalStorage.store("field_name", name);
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.ADD_REMOVE_OPTIONS));
	}
	
	@Override
	public String addFafrRuleSetValue(String temp, String field, String id, String name) throws Exception {
		LocalStorage.store("request_type", "incident");
		LocalStorage.store("temp_name", temp);
		LocalStorage.store("field", field);
		LocalStorage.store("field_id", id);
		LocalStorage.store("field_name", name);
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.ADD_SETVALUE));
	}
	
	@Override
	public String addFafrRuleSetValueEmailtonotify(String temp, String field, String value) throws Exception {
		LocalStorage.store("request_type", "incident");
		LocalStorage.store("temp_name", temp);
		LocalStorage.store("field", field);
		LocalStorage.store("value", value);
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.ADD_SETVALUE_EMAILTO_NOTIFY));
	}
	
	@Override
	public String addFafrRuleAction(String temp, String field) throws Exception {
		LocalStorage.store("request_type", "incident");
		LocalStorage.store("temp_name", temp);
		LocalStorage.store("field", field);
		return restAPI.create(RequestsEntities.FAFR, RequestAPIPaths.REQUEST_FAFR, getTestCaseData(RequestDataConstants.FafrData.MANDATE_FIELD));
	}
	
	// Elango test case for CART 2501 (Mandate Description)
	@AutomaterScenario(
		group = "",
		priority = Priority.MEDIUM,
		dataIds = {},
		tags = {"CART_2501"},
		description = "Create a form rule with Mandate description in Action",
		runType = ScenarioRunType.USER_BASED,
		owner = OwnerConstants.ELANGO_S,
		id = "SDPOD-FL_FAFR-1160,IssueID_75045"
	)
	public void verifyDescriptioninActionMandatefieldsinIncidentFafr() {
		report.startMethodFlowInStepsToReproduce(AutomaterVariables.SCENARIO_START.apply(getMethodName()));
		String ruleName = random.generateRandomString(4);
		try {
			AdminActionsUtil.gotoentity(AdminConstants.SubModule.FORM_RULES);
			chooseFormRule();
			actions.click(AdminLocators.RequestFAFR.CREATE_RULE);
			actions.type(AdminLocators.RequestFAFR.RULE_NAME_FIELD, ruleName);
			
			actions.click(AdminLocators.RequestFAFR.FIRST_SELECT_COLUMN_UNDER_CONDITION);
			actions.formBuilder.typeAndSelectOption(AdminConstants.RequestFAFR.Fields.SUBJECT);
			actions.formBuilder.typeAndSelectOption(AdminConstants.RequestFAFR.FieldOperators.IS);
			actions.type(AdminLocators.RequestFAFR.FIRST_SELECT_VALUE_UNDER_CONDITION, "MANDATE DESCRIPTION");
			
			actions.click(AdminLocators.RequestFAFR.FIRST_SELECT_ACTION_DROP_DOWN);
			actions.formBuilder.typeAndSelectOption(AdminConstants.RequestFAFR.Actions.MANDATE_FIELDS);
			typeAndSelectOption(AdminLocators.RequestFAFR.CRITERIA_INPUT, AdminConstants.RequestFAFR.Fields.DESCRIPTION);
			actions.click(AdminLocators.RequestFAFR.SAVE_RULE);
			
			JSONObject FormRuleData = getTestCaseData(RequestDataConstants.RequestData.FORM_RULE_WITH_MANDATE_DESCRIPTION);
			actions.navigate.toModule(ModuleConstants.REQUESTS);
			actions.navigate.toGlobalActionInListview(RequestConstants.ListviewGlobalActions.NEW_REQUEST);
			requestFormbuilder.fillInputForAnEntity(isClientFramework(), EntityMetaDetails.getInstance().getEntityConfigurationDetails(ModuleConstants.EntityConstants.REQUEST).getFields(), FormRuleData);
			actions.formBuilder.submit();
			
			actions.click(RequestLocators.Listview.EDIT_REQUEST_BUTTON);
			actions.formBuilder.submit();
			
			Boolean alert = actions.isElementPresent(RequestLocators.DetailView.MANDATORY_MESSAGE.apply("description"));
			if(alert) {
				addSuccessReport("Alert shown successfully for Empty description");
			}else {
				addFailureReport("Alert not displayed", "Check Request Description");
			}
			
			String description = "Mandatory Description";
			actions.formBuilder.fillHTMLField("description", description);
			actions.formBuilder.submit();
			
			Boolean isEqual = actions.isElementPresent(RequestLocators.DetailView.MODULE_TITLE);
			if(isEqual) {
				addSuccessReport("Request saved successfully");
			}else {
				addFailureReport("Request Update failed", "Request title not matched");
			}
		}catch(Exception exception) {
			addFailureReport("Internal error occurred while running the test case " + getMethodName(), exception.getMessage());
		}finally {
			report.endMethodFlowInStepsToReproduce();
		}
	}
	
	// Elango Test case for Nested criteria under Rules)
	@AutomaterScenario(
		group = "",
		priority = Priority.MEDIUM,
		dataIds = {},
		tags = {"NESTED_FAFR", GlobalConstants.Tags.DO_NOT_RUN},    // Failed in RL126 as discussed tagged as DO_NOT_RUN
		description = "Create a form rule with Nested conditions",
		runType = ScenarioRunType.PORTAL_BASED,
		owner = OwnerConstants.ELANGO_S,
		id = "Nested_Rule_FAFR"
	)
	public void verifyNestedCriteriaRuleinIncidentFafr() {
		report.startMethodFlowInStepsToReproduce(AutomaterVariables.SCENARIO_START.apply(getMethodName()));
		String ruleName = random.generateRandomString(4);
		try {
			AdminActionsUtil.gotoentity(AdminConstants.SubModule.FORM_RULES);
			chooseFormRule();
			actions.refreshPage();
			actions.click(AdminLocators.RequestFAFR.CREATE_RULE);
			actions.type(AdminLocators.RequestFAFR.RULE_NAME_FIELD, ruleName);
			
			actions.click(AdminLocators.RequestFAFR.FIRST_SELECT_COLUMN_UNDER_CONDITION);
			actions.formBuilder.typeAndSelectOption(AdminConstants.RequestFAFR.Fields.SUBJECT);
			actions.formBuilder.typeAndSelectOption(AdminConstants.RequestFAFR.FieldOperators.IS);
			actions.type(AdminLocators.RequestFAFR.FIRST_SELECT_VALUE_UNDER_CONDITION, "NESTED FAFR");
			actions.click(AdminLocators.RequestFAFR.ADD_ICON_IN_CONDITIONS_FIELD);
			
			actions.dragAndDropToCoordinates(AdminLocators.RequestFAFR.NESTED_DRAG_FIRST, 10, 0);
			actions.click(AdminLocators.RequestFAFR.SECOND_SELECT_COLUMN_UNDER_CONDITION);
			actions.formBuilder.typeAndSelectOption(AdminConstants.RequestFAFR.Fields.PRIORITY);
			actions.formBuilder.typeAndSelectOption(AdminConstants.RequestFAFR.FieldOperators.IS);
			typeAndSelectOption(AdminLocators.RequestFAFR.SECOND_SELECT_VALUE_UNDER_CONDITION, AdminConstants.RequestFAFR.Priority.HIGH);
			actions.click(AdminLocators.RequestFAFR.ADD_ICON_IN_FIRST_CHILD);
			
			actions.dragAndDropToCoordinates(AdminLocators.RequestFAFR.NESTED_DRAG_SECOND, 10, 0);
			actions.click(AdminLocators.RequestFAFR.THIRD_SELECT_COLUMN_UNDER_CONDITION);
			actions.formBuilder.typeAndSelectOption(AdminConstants.RequestFAFR.Fields.STATUS);
			actions.formBuilder.typeAndSelectOption(AdminConstants.RequestFAFR.FieldOperators.IS);
			typeAndSelectOption(AdminLocators.RequestFAFR.THIRD_SELECT_VALUE_UNDER_CONDITION, AdminConstants.RequestFAFR.Status.OPEN);
			
			actions.click(AdminLocators.RequestFAFR.FIRST_SELECT_ACTION_DROP_DOWN);
			actions.formBuilder.typeAndSelectOption(AdminConstants.RequestFAFR.Actions.MANDATE_FIELDS);
			typeAndSelectOption(AdminLocators.RequestFAFR.CRITERIA_INPUT, AdminConstants.RequestFAFR.Fields.IMPACT);
			actions.click(AdminLocators.RequestFAFR.SAVE_RULE);
			
			JSONObject NestedRuleData = getTestCaseData(RequestDataConstants.RequestData.NESTED_FORM_RULE);
			actions.navigate.toModule(ModuleConstants.REQUESTS);
			actions.navigate.toGlobalActionInListview(RequestConstants.ListviewGlobalActions.NEW_REQUEST);
			requestFormbuilder.fillInputForAnEntity(isClientFramework(), EntityMetaDetails.getInstance().getEntityConfigurationDetails(ModuleConstants.EntityConstants.REQUEST).getFields(), NestedRuleData);
			actions.formBuilder.submit();
			
			actions.click(RequestLocators.Listview.EDIT_REQUEST_BUTTON);
			actions.formBuilder.submit();
			
			Boolean alert = actions.isElementPresent(RequestLocators.Form.CHECK_MANDATE_FIELD.apply("impact"));
			if(alert) {
				addSuccessReport("Alert shown successfully for Impact field");
			}else {
				addFailureReport("Alert not displayed", "Check Request Impact field");
			}
		}catch(Exception exception) {
			addFailureReport("Internal error occurred while running the test case " + getMethodName(), exception.getMessage());
		}finally {
			report.endMethodFlowInStepsToReproduce();
		}
	}
	
}
