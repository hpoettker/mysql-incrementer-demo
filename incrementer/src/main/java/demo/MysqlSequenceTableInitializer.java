package demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MysqlSequenceTableInitializer implements InitializingBean {

  private static final String TABLE_STATEMENT = """
      CREATE TABLE IF NOT EXISTS `SEQUENCE` (
        id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT
      )
      """;

  private final JdbcTemplate jdbcTemplate;

  public MysqlSequenceTableInitializer(DataSource dataSource1) {
    jdbcTemplate = new JdbcTemplate(dataSource1);
  }

  @Override
  public void afterPropertiesSet() {
    jdbcTemplate.execute(TABLE_STATEMENT);
  }

}
