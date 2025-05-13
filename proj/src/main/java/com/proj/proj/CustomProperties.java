package com.proj.proj;


import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="com.proj.proj")
public class CustomProperties {

    private String apiUrl;

}
