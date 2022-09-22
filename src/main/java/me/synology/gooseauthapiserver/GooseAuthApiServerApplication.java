package me.synology.gooseauthapiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GooseAuthApiServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(GooseAuthApiServerApplication.class, args);
  }

}
