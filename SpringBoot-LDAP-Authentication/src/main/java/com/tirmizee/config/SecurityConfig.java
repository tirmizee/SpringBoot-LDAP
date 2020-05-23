package com.tirmizee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tirmizee.config.properties.LdapProperty;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${ldap.enabled}")
	private boolean ldapEnabled;
	
	@Autowired
	private LdapProperty ldapProperty;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
        		.antMatchers("/login**").permitAll()
        		.antMatchers("/**").fullyAuthenticated()
            	.and()
            .formLogin()
            	.loginPage("/login")
            	.failureUrl("/login?error")
            	.permitAll()
            	.and()
            .logout()
            	.invalidateHttpSession(true)
            	.deleteCookies("JSESSIONID")
            	.permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
		if(ldapEnabled) {
			authBuilder
				.ldapAuthentication()
				.contextSource()
					.url(ldapProperty.getUrl() + ldapProperty.getBaseDn())
						.managerDn(ldapProperty.getUsername())
						.managerPassword(ldapProperty.getPassword())
					.and()
						.userDnPatterns(ldapProperty.getBaseDnPattern());
		} else {
			authBuilder
		        .inMemoryAuthentication()
		            .withUser("user").password("{noop}password").roles("USER").and()
		            .withUser("admin").password("{noop}password").roles("ADMIN").and()
		            .withUser("tirmizee").password("{noop}password").roles("ADMIN");
		}
	}
	
}
