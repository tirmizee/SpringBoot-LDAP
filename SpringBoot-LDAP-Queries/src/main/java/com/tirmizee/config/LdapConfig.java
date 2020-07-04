package com.tirmizee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.pool.factory.PoolingContextSource;

@Configuration
public class LdapConfig {
	
	@Bean
	public LdapContextSource contextSource() {
	    LdapContextSource contextSource = new LdapContextSource();
	    contextSource.setPooled(true);
//	    contextSource.setUrl(url);
//	    contextSource.setBase(base);
//	    contextSource.setUserDn(userDn);
//	    contextSource.setPassword(password);
	    return contextSource;
	}
	
	@Bean
	public PoolingContextSource poolingContextSource(LdapContextSource contextSource) {
		PoolingContextSource poolingContextSourc = new PoolingContextSource();
		poolingContextSourc.setMinIdle(5);
		poolingContextSourc.setMaxActive(15);
		poolingContextSourc.setMaxTotal(15);
		poolingContextSourc.setContextSource(contextSource);
	    return poolingContextSourc;
	}
	
	@Bean
	public LdapTemplate ldapTemplate(PoolingContextSource poolingContextSource) {
	    return new LdapTemplate(poolingContextSource);
	}

}
