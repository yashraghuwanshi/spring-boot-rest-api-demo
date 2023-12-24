package com.example.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomInfoContributor implements InfoContributor {

    private final Environment environment;

    @Autowired
    public CustomInfoContributor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("customInfo", "This is a dynamic custom information.");
        builder.withDetail("port", environment.getProperty("local.server.port"));
        builder.withDetail("profiles", environment.getDefaultProfiles());

        Map<String, Object> additionalProperties = new HashMap<>();
        additionalProperties.put("timestamp", System.currentTimeMillis());
        additionalProperties.put("build version", environment.getProperty("build.version"));

        builder.withDetails(additionalProperties);
    }
}
