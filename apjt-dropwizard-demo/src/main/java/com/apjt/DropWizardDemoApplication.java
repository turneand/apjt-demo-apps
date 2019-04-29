package com.apjt;

import com.apjt.resources.GreetingResource;

import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropWizardDemoApplication extends Application<DropWizardDemoConfiguration> {
    public static void main(final String[] args) throws Exception {
        new DropWizardDemoApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<DropWizardDemoConfiguration> bootstrap) {
        // override the configuration source to load the config from the classpath
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
    }

    @Override
    public void run(final DropWizardDemoConfiguration configuration,
                    final Environment environment) throws Exception {
        environment.jersey().register(new GreetingResource());
    }
}
