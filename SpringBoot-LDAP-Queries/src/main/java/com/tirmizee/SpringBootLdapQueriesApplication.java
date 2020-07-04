package com.tirmizee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import com.tirmizee.properties.LdapProperty;
import com.tirmizee.repositories.LdapRepository;

@SpringBootApplication
@EnableConfigurationProperties({
	LdapProperty.class
})
public class SpringBootLdapQueriesApplication implements CommandLineRunner {

	@Autowired
	ApplicationContext context;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootLdapQueriesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LdapRepository LdapRepository = context.getBean(LdapRepository.class);
		System.out.println(LdapRepository.getPersonByUID("John"));
	}

}
