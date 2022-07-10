package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MysqlIncrementerApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(MysqlIncrementerApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(MysqlIncrementerApplication.class, args);
  }

  @Bean
  ApplicationRunner applicationRunner(
      MysqlIdentityColumnMaxValueIncrementer incrementer1,
      MysqlIdentityColumnMaxValueIncrementer incrementer2
  ) {
    return args -> {
      LOGGER.info("Incrementer 1 says: " + incrementer1.nextLongValue());
      LOGGER.info("Incrementer 2 says: " + incrementer2.nextLongValue());
    };
  }

}
