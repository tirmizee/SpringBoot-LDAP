package com.tirmizee.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("ldap")
public class LdapProperty {
	
	private String url;
	private String baseDn;
	private String baseDnPattern;
	private String username;
	private String password;

}
