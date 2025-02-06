package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OllamaApplication {

  public static void main(String[] args) {
    //    SpringApplication.run(OllamaApplication.class, args);
    SpringApplication app = new SpringApplication(OllamaApplication.class);
    //    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
  }
}
