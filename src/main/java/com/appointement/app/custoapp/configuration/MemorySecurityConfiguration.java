package com.appointement.app.custoapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MemorySecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${app.dashboard.password}")
	private String dashboardPassword;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.inMemoryAuthentication()
				.withUser("dashboarduser")
				.password(dashboardPassword)
				.roles("ADMIN");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http
				.authorizeRequests()
				.antMatchers("/dashboard").hasRole("ADMIN")
				.antMatchers("/api/**").hasRole("ADMIN")
				.antMatchers( "/ressources/**").permitAll()
				.and()
				.formLogin()
				.loginPage("/loginDash")
				.defaultSuccessUrl("/dashboard", true);

	}
}
