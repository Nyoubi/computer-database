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
	 
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
	  throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select username,password from user where username = ?")
		.authoritiesByUsernameQuery(
			"select user_role.username username, role.name auhority from user_role join role on role.id = user_role.idRole where user_role.username = ?")
		.passwordEncoder(new BCryptPasswordEncoder());
	} 
	
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	    .authorizeRequests()
		    .antMatchers("/addComputer").hasAuthority("ADMIN")
			.antMatchers("/editComputer").hasAuthority("ADMIN")
			.antMatchers("/deleteComputer").hasAuthority("ADMIN")
			.antMatchers("/").authenticated()
			.antMatchers("/dashboard").authenticated()
			.antMatchers("/LoginProcess").permitAll()
		.and()
			.formLogin()
			.loginPage("/Login")
			.loginProcessingUrl("/LoginProcess")
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/")
			.failureForwardUrl("/Login?error")
		.and()
			.logout()
			.logoutSuccessUrl("/Login?logout")
			.logoutUrl("/LogoutProcess");
	}
}