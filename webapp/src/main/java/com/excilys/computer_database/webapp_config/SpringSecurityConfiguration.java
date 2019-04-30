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
			"select username,password from user where username = ?")
		.authoritiesByUsernameQuery(
			"select user_role.username username, role.name auhority from user_role join role on role.id = user_role.idRole where user_role.username = ?")
		.passwordEncoder(new BCryptPasswordEncoder());
	} 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	    .authorizeRequests()
		    .antMatchers("/computer/addComputer").hasAuthority("ADMIN")
			.antMatchers("/computer/editComputer").hasAuthority("ADMIN")
			.antMatchers("/computer/deleteComputer").hasAuthority("ADMIN")
			.antMatchers("/").authenticated()
			.antMatchers("/computer/dashboard").authenticated()
			.antMatchers("/LoginProcess").permitAll()
		.and()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/LoginProcess")
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/computer/dashboard")
			.failureForwardUrl("/login?error")
		.and()
			.logout()
			.logoutSuccessUrl("/login?logout")
			.logoutUrl("/LogoutProcess");
	}
}