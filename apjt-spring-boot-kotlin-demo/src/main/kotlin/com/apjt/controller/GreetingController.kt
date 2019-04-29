package com.apjt.controller

import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicLong

import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.apjt.dto.Greeting

/**
 * Primary controller for this sample application.  Demonstrates simple spring-boot wiring of some basic functions.
 */
@RestController
@RequestMapping("/greeting")
open class GreetingController {
	val counter = AtomicLong()

	// TODO - Bug here, that this is NOT executed asynchronously!
	// had thought that class and async methods need to be defined "open" (i.e. non-final) would resolve, but no luck so far...
    @GetMapping("/async2")
    @Async("asyncExecutor")
    open fun async2() = CompletableFuture.completedFuture(Greeting(counter.incrementAndGet(), "Greetings from Spring Boot Kotlin - async2! - " + Thread.currentThread().getName() + " - " + System.getProperty("user.name")))

    @GetMapping("/sync")
    fun sync() = Greeting(counter.incrementAndGet(), "Greetings from Spring Boot Kotlin - sync! - " + Thread.currentThread().getName())

    @GetMapping("/echo/{id}")
    fun echo(@PathVariable("id") id: Long) = Greeting(counter.incrementAndGet(), "Echo from Spring Boot Kotlin - $id")
}
