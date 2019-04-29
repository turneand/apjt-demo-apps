package com.apjt.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apjt.config.AsyncConfig;
import com.apjt.dto.Greeting;

/**
 * Primary controller for this sample application.  Demonstrates simple spring-boot wiring of some basic functions.
 */
@RestController
@RequestMapping("/greeting")
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();

    /**
     * Original attempt at asynchronous handling of requests.  Relies on the {@link java.util.concurrent.ForkJoinPool#commonPool()}
     *
     * @return The {@link Greeting} eventually.
     */
    @GetMapping(value = { "", "/async", "/async1" })
    public CompletableFuture<Greeting> async1() {
        return CompletableFuture.supplyAsync(() -> {
            return new Greeting(this.counter.incrementAndGet(), "Greetings from Spring Boot - async1! - " + Thread.currentThread().getName() + " - " + System.getProperty("user.name"));            
        });
    }

    /**
     * Relies on the Spring wired {@link AsyncConfig#asyncExecutor()} for executing the requests asynchronously instead of the default pool.
     *
     * @return The {@link Greeting} eventually.
     */
    @GetMapping("/async2")
    @Async("asyncExecutor")
    public CompletableFuture<Greeting> async2() {
        return CompletableFuture.completedFuture(new Greeting(this.counter.incrementAndGet(), "Greetings from Spring Boot - async2! - " + Thread.currentThread().getName() + " - " + System.getProperty("user.name")));
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
