package com.apjt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Lightweight alternative to {@link org.springframework.boot.actuate.env.EnvironmentEndpoint} that simply exposes the
 * activeProfiles without exposing the rest of the potentially sensitive information contained within the environment.
 */
@Component
@Endpoint(id = "activeProfiles")
public class ActiveProfilesEndpoint {
	@Autowired
	private Environment environment;

	/**
	 * @see Environment#getActiveProfiles()
	 */
	@ReadOperation
	public String[] getActiveProfiles() {
		return this.environment.getActiveProfiles();
	}
}
