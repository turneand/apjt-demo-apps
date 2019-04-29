package com.apjt.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * <p>Test class for {@link GreetingController}.</p>
 *
 * <p>Runs simple tests to verify the configuration of the controller, and to also ensure that
 * the synchronous/asynchronous calls work as expected.</p>
 *
 * <p>Note, the testing of responses is NOT exhaustive.</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GreetingControllerTest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void testAsync1() throws Exception {
		final MvcResult mvcResult = this.mvc.perform(get("/greeting/async1"))
				.andExpect(request().asyncStarted())
				.andReturn();

		this.mvc.perform(asyncDispatch(mvcResult))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString("Greetings from Spring Boot - async1! - ")));
	}

	@Test
	public void testAsync2() throws Exception {
		final MvcResult mvcResult = this.mvc.perform(get("/greeting/async2"))
				.andExpect(request().asyncStarted())
				.andReturn();

		this.mvc.perform(asyncDispatch(mvcResult))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString("Greetings from Spring Boot - async2! - ")));
	}

	@Test
	public void testSync() throws Exception {
		this.mvc.perform(get("/greeting/sync"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString("Greetings from Spring Boot - sync! - ")));
	}

	@Test
	public void testEcho() throws Exception {
		this.mvc.perform(get("/greeting/echo/123"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString("Echo from Spring Boot - 123")));
	}
}
