package tr.edu.ogu.ceng.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.enums.UserType;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@AllArgsConstructor
public class WebSecurityConfig {

	ImsUserDetailsService imsUserDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeRequests().antMatchers("/public/**").permitAll()
				.antMatchers("/facultysupervisor/**", "**/facultysupervisor/**")
				.hasAuthority(UserType.FACULTYSUPERVISOR.name()).antMatchers("/student/**", "**/student/**")
				.hasAuthority(UserType.STUDENT.name()).antMatchers("/companysupervisor/**", "**/companysupervisor/**")
				.hasAuthority(UserType.COMPANYSUPERVISOR.name()).antMatchers("/admin/**", "**/admin/**")
				.hasAuthority(UserType.ADMIN.name()).anyRequest().authenticated().and().formLogin()
				.loginPage("/public/login.html").loginProcessingUrl("/login")
				.successHandler(authenticationSuccessHandler()).failureHandler(authenticationFailureHandler()).and()
				.logout().logoutSuccessUrl("/public/login.html?logout=true").deleteCookies("JSESSIONID").and()
				.httpBasic().and().build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
			UserDetailsService userDetailService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(imsUserDetailsService)
				.passwordEncoder(passwordEncoder).and().build();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new RoleBasedAuthenticationSuccessHandler();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
