package com.clutchacademy.user_service.configs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EnvConfig implements EnvironmentPostProcessor {

    private static final String ENV_FILE = ".env";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            Map<String, Object> properties = new HashMap<>();

            // Carrega o arquivo .env linha por linha
            Files.lines(Paths.get(ENV_FILE))
                    .filter(line -> line.contains("=") && !line.startsWith("#")) // Ignora comentários
                    .forEach(line -> {
                        String[] parts = line.split("=", 2);
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        properties.put(key, value);
                    });

            // Adiciona as propriedades ao ambiente
            MapPropertySource propertySource = new MapPropertySource("customEnvProperties", properties);
            environment.getPropertySources().addLast(propertySource);

        } catch (IOException e) {
            System.err.println("Falha ao carregar o arquivo .env: " + e.getMessage());
        }
    }
}
