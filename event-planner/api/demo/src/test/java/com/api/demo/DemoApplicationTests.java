package com.api.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = DemoApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class DemoApplicationTests {

  @Test
  void contextLoads() {
    // This test verifies that the Spring application context loads successfully
  }
}
