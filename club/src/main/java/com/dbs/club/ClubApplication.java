package com.dbs.club;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubApplication.class, args);
	}

}
