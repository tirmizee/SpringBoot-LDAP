package com.tirmizee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import com.tirmizee.config.properties.LdapProperty;

@EnableConfigurationProperties({ LdapProperty.class })
@SpringBootApplication
public class SpringBootLdapAuthenticationApplication implements CommandLineRunner {

	@Autowired
	ApplicationContext context;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootLdapAuthenticationApplication.class, args);
	}

	@Override
	public void run(String...args) throws Exception {
		LdapProperty ldapProperty = context.getBean(LdapProperty.class);
		System.out.println(ldapProperty);
	}

}
