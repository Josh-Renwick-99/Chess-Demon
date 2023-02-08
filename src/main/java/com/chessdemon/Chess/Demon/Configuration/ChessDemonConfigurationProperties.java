package com.chessdemon.Chess.Demon.Configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
public class ChessDemonConfigurationProperties {
    private String name;
    private String environment;
    private Boolean enabled;
    private String  databaseDriver;
    private String  databaseSource;
    private String dbUser;
    private String dbPass;
}
