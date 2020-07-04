package com.tirmizee.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("spring.ldap")
public class LdapProperty {
	
	private String urls;
	private String base;
	private String baseDnPattern;
	private String username;
	private String password;

}
