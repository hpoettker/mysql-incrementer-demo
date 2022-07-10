package demo;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiguration {

  private static final String URL_PATTERN = "jdbc:mysql://localhost:%d/SPRING";

  @Bean
  @ConfigurationProperties("spring.datasource")
  DataSourceProperties dataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  HikariDataSource dataSource1(DataSourceProperties dataSourceProperties) {
    dataSourceProperties.setUrl(URL_PATTERN.formatted(3306));
    return dataSourceProperties.initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean
  HikariDataSource dataSource2(DataSourceProperties dataSourceProperties) {
    dataSourceProperties.setUrl(URL_PATTERN.formatted(3307));
    return dataSourceProperties.initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean
  MysqlIdentityColumnMaxValueIncrementer incrementer1(DataSource dataSource1) {
    return new MysqlIdentityColumnMaxValueIncrementer(dataSource1, "SEQUENCE", "id");
  }

  @Bean
  MysqlIdentityColumnMaxValueIncrementer incrementer2(DataSource dataSource2) {
    return new MysqlIdentityColumnMaxValueIncrementer(dataSource2, "SEQUENCE", "id");
  }

}
