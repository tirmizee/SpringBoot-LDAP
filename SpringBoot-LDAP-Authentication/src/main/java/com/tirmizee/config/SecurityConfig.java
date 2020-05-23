package com.tirmizee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
		
//		if(Boolean.parseBoolean(ldapEnabled)) {
//			auth
//				.ldapAuthentication()
//				.contextSource()
//					.url(ldapUrls + ldapBaseDn)
//						.managerDn(ldapSecurityPrincipal)
//						.managerPassword(ldapPrincipalPassword)
//					.and()
//						.userDnPatterns(ldapUserDnPattern);
//		} else {
		authBuilder
	        .inMemoryAuthentication()
	            .withUser("user").password("{noop}password").roles("USER").and()
	            .withUser("admin").password("{noop}password").roles("ADMIN").and()
	            .withUser("tirmizee").password("{noop}password").roles("ADMIN");
//		}
	}
	
}
