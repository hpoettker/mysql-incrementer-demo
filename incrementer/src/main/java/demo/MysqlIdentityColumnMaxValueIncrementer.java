package demo;

import javax.sql.DataSource;

import org.springframework.jdbc.support.incrementer.AbstractIdentityColumnMaxValueIncrementer;

public class MysqlIdentityColumnMaxValueIncrementer extends AbstractIdentityColumnMaxValueIncrementer {

  public MysqlIdentityColumnMaxValueIncrementer(DataSource dataSource, String incrementerName, String columnName) {
    super(dataSource, incrementerName, columnName);
  }

  @Override
  protected String getIncrementStatement() {
    return "insert into " + getIncrementerName() + " () values ()";
  }

  @Override
  protected String getIdentityStatement() {
    return "select last_insert_id()";
  }

}
