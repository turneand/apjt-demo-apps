package com.apjt.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apjt.dto.Greeting;

/**
 * Primary controller for this sample application.  Demonstrates simple spring-boot wiring of some basic functions.
 */
@RestController
@RequestMapping("/greeting")
public class GreetingController {
	private final AtomicLong counter = new AtomicLong();

    @GetMapping(value = { "", "async" })
    public CompletableFuture<Greeting> async() {
    	return CompletableFuture.supplyAsync(() -> {
    		return new Greeting(this.counter.incrementAndGet(), "Greetings from Spring Boot - async! - " + Thread.currentThread().getName() + " - " + System.getProperty("user.name"));    		
    	});
    }

    @GetMapping("/sync")
    public Greeting sync() {
		return new Greeting(this.counter.incrementAndGet(), "Greetings from Spring Boot - sync! - " + Thread.currentThread().getName());    		
    }

    @GetMapping("/echo/{id}")
    public Greeting echo(@PathVariable("id") long id) {
    	return new Greeting(this.counter.incrementAndGet(), "Echo from Spring Boot - " + id);
    }
}
