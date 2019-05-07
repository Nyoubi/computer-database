package com.excilys.computer_database.webapp_config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	 
	@Override
	public void configure(AuthenticationManagerBuilder auth) 
	  throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"SELECT username,password,enabled FROM user WHERE username = ?")
		.authoritiesByUsernameQuery(
			"SELECT user_role.username username, role.name auhority FROM user_role JOIN role ON role.id = user_role.idRole WHERE user_role.username = ?")
		.passwordEncoder(new BCryptPasswordEncoder());
	} 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable().authorizeRequests()
		    .mvcMatchers("/computer/addComputer").hasAuthority("ADMIN")
			.mvcMatchers("/computer/editComputer").hasAuthority("ADMIN")
			.mvcMatchers("/computer/deleteComputer").hasAuthority("ADMIN")
			.mvcMatchers("/").authenticated()
			.mvcMatchers("/computer/dashboard").authenticated()
			.mvcMatchers("/LoginProcess").permitAll()
		.and()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/LoginProcess")
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/computer/dashboard")
			.failureUrl("/login?error=true")
		.and()
			.logout()
			.logoutSuccessUrl("/login?logout=true")
			.logoutUrl("/LogoutProcess")
	    .and()
	    	.exceptionHandling().accessDeniedPage("/403");
	}
}