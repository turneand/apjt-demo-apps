package com.apjt;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class DropWizardDemoConfiguration extends Configuration {
    @NotEmpty
    private String ignoredParameter;

    @JsonProperty
	public void setIgnoredParameter(String ignoredParameter) {
		this.ignoredParameter = ignoredParameter;
	}
}
