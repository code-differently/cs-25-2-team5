package com.api.demo.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for LocationIQ API integration. Provides RestTemplate bean and API key
 * management for geocoding services.
 */
@Configuration
public class LocationIQConfig {

  private static final org.slf4j.Logger log =
      org.slf4j.LoggerFactory.getLogger(LocationIQConfig.class);

  @Value("${locationiq.api.key}")
  private String apiKey;

  @Value("${locationiq.api.base-url}")
  private String baseUrl;

  /** Validates configuration after bean construction. */
  @PostConstruct
  public void validateConfig() {
    log.info("Initializing LocationIQ configuration with base URL: {}", baseUrl);

    if (!StringUtils.hasText(apiKey)) {
      throw new IllegalStateException("LocationIQ API key is required but not configured");
    }
    if (!StringUtils.hasText(baseUrl)) {
      throw new IllegalStateException("LocationIQ base URL is required but not configured");
    }

    log.info("LocationIQ configuration validated successfully");
  }

  /**
   * Creates a RestTemplate bean configured for LocationIQ API calls.
   *
   * @return RestTemplate instance for LocationIQ service
   */
  @Bean(name = "restTemplate")
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  /**
   * Gets the LocationIQ API key.
   *
   * @return API key for LocationIQ service
   */
  public String getApiKey() {
    return apiKey;
  }

  /**
   * Gets the LocationIQ base URL.
   *
   * @return Base URL for LocationIQ service
   */
  public String getBaseUrl() {
    return baseUrl;
  }
}
