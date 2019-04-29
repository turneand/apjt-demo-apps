package com.apjt.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import org.glassfish.jersey.server.ManagedAsync;

import com.apjt.dto.Greeting;
import com.codahale.metrics.annotation.Timed;

@Path("/greeting")
public class GreetingResource {
    private final Executor fixedExecutor = Executors.newFixedThreadPool(10);
    private final AtomicLong counter = new AtomicLong();

    @Path("/sync")
    @GET
    @Produces(APPLICATION_JSON)
    @Timed
    public Greeting sync(@QueryParam("name") Optional<String> name) {
        return new Greeting(this.counter.incrementAndGet(), "Greetings from DropWizard - sync! - " + Thread.currentThread().getName());            
    }

    @Path("/async-managed")
    @GET
    @Produces(APPLICATION_JSON)
    @Timed
    @ManagedAsync
    public void managedAsync(@Suspended final AsyncResponse resp) {
        // utilises the "@ManagedAsync" annotation for the executor pools
        try {
            Thread.sleep(3000);
            resp.resume(new Greeting(this.counter.incrementAndGet(), "Greetings from DropWizard - async-managed! - " + Thread.currentThread().getName() + " - " + System.getProperty("user.name")));
        } catch (final Exception e) {
            resp.resume(e);
        }
    }

    @Path("/async-manual")
    @GET
    @Produces(APPLICATION_JSON)
    @Timed
    public void manualAsync(@Suspended final AsyncResponse resp) {
        // uses a manually created thread pool
        this.fixedExecutor.execute(() -> {
            try {
                Thread.sleep(5000);
                resp.resume(new Greeting(this.counter.incrementAndGet(), "Greetings from DropWizard - async-manual! - " + Thread.currentThread().getName() + " - " + System.getProperty("user.name")));
            } catch (final Exception e) {
                resp.resume(e);
            }
        });
    }
}
