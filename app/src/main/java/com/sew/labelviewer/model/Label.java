package com.sew.labelviewer.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Label implements Serializable {
    private String controlGuId, controlId, controlPlaceHolder,
            controlText, controlTitle, errorMessage,
            languageCode, lastUpdated, moduleGuId;

    private Label(String controlGuId, String controlId, String controlPlaceHolder,
                  String controlText, String controlTitle, String errorMessage,
                  String languageCode, String lastUpdated, String moduleGuId) {

        this.controlGuId = controlGuId;
        this.controlId = controlId;
        this.controlPlaceHolder = controlPlaceHolder;
        this.controlText = controlText;
        this.controlTitle = controlTitle;
        this.errorMessage = errorMessage;
        this.languageCode = languageCode;
        this.lastUpdated = lastUpdated;
        this.moduleGuId = moduleGuId;
    }

    public static Label getLabelFromJSON(JSONObject labelJSON) {
            return new Label(labelJSON.optString("ControlGuId"), labelJSON.optString("ControlId"), labelJSON.optString("ControlPlaceHolder")
                    , labelJSON.optString("ControlText"), labelJSON.optString("ControlTitle"), labelJSON.optString("ErrorMessage")
                    , labelJSON.optString("LanguageCode"), labelJSON.optString("LastUpdated"), labelJSON.optString("ModuleGuId"));
    }

    public String getControlGuId() {
        return controlGuId;
    }

    public String getControlId() {
        return controlId;
    }

    public String getControlPlaceHolder() {
        return controlPlaceHolder;
    }

    public String getControlText() {
        return controlText;
    }

    public String getControlTitle() {
        return controlTitle;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getModuleGuId() {
        return moduleGuId;
    }
}
