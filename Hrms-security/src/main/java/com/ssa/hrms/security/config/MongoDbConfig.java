/**package com.ssa.hrms.security.config;
import com.mongodb.*;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.Value;

@Configuration
@EnableMongoRepositories(basePackages = {"com.ssa.hrms.dao.mongodb"})
@PropertySource({ "classpath:application.properties" })
public abstract class MongoDbConfig extends AbstractMongoConfiguration {
	
	
	  @Autowired
	  private Environment env;

	  @Override
	  protected String getDatabaseName() {
	    return env.getProperty("spring.data.mongodb.database");
	  }

	  public Mongo mongo() throws Exception {
	    return new MongoClient(env.getProperty("spring.data.mongodb.host"), Integer.parseInt(env.getProperty("spring.data.mongodb.port")));
	  }
	  
	  @Override
	  protected String getMappingBasePackage() {
	    return "com.ssa.hrms.dto.mongodbentities";
	  }


	
	
}**/
