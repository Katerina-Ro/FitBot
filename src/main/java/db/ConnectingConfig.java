package db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("db")
@PropertySource(value = "../../resources/application.yml")
public class ConnectingConfig {


/*
    @Getter
    private Environment environment;

    @Autowired
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("driver-class-name"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc-url"));
        dataSource.setUsername(environment.getRequiredProperty("username"));
        dataSource.setPassword(environment.getRequiredProperty("password"));
        return dataSource;
    } */

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
