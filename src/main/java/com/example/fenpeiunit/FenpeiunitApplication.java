package com.example.fenpeiunit;

import com.example.fenpeiunit.cons.GlobalVar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableConfigurationProperties({GlobalVar.class})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class FenpeiunitApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(FenpeiunitApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(FenpeiunitApplication.class, args);
	}

}

