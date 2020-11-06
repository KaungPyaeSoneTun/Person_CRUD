package com.kpst.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:database.properties" })
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class }) 
public class PersonCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonCrudApplication.class, args);
	}

}
