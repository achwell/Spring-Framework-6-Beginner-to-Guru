package com.example.spring6di.services;

import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Profile({"DEV", "default"})
@Service
public class EnvironmentServiceDev implements EnvironmentService {
    private final Environment environment;

    public EnvironmentServiceDev(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getEnv() {
        return Arrays.toString(environment.getActiveProfiles());
    }
}
