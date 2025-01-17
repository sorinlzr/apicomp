package net.pfsnc.apicomp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApicompApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApicompApplication.class, args);
	}

}
