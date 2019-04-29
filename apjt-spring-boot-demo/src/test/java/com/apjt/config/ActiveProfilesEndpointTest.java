package com.apjt.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * <p>Test class for {@link ActiveProfilesEndpoint}, verifies our actuator endpoint is correctly wired up.</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ActiveProfilesEndpointTest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void testActiveProfiles() throws Exception {
		this.mvc.perform(get("/actuator/activeProfiles"))
				.andExpect(status().isOk())
				.andExpect(content().string("[\"test\"]"));
	}

}
