package com.apjt.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Spring context configuration to wire up asynchronous executors.
 */
@Configuration
@EnableAsync
public class AsyncConfig {
	@Value("${asyncExecutor.threadCount:2}")
	@Positive
	private int asyncExecutorThreadCount;

	@Bean
	public ExecutorService asyncExecutor() {
		return Executors.newFixedThreadPool(this.asyncExecutorThreadCount, new ThreadFactory() {
	        private final AtomicInteger threadNumber = new AtomicInteger(1);

	        @Override
			public Thread newThread(final Runnable r) {
	            final Thread t = new Thread(r, "spring-managed-thread-" + this.threadNumber.getAndIncrement());
            	t.setDaemon(false);
            	t.setPriority(Thread.NORM_PRIORITY);
            	return t;
			}
		});
	}
}
