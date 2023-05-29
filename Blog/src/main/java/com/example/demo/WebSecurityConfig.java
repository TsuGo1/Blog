package com.example.demo;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(login -> login
				.loginPage("/login")
				.defaultSuccessUrl("/blog_list", true)
				.usernameParameter("username")
				.passwordParameter("password")
				.loginProcessingUrl("/login")
				.failureUrl("/login?error")
				.permitAll()
		).authorizeHttpRequests(authz -> authz
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
				.permitAll()
				.requestMatchers("/register", "/css/**", "/login",".css",".js")
				.permitAll()
				.anyRequest()
				.authenticated()
		);
		
		return http.build();
	}
	

	@Bean
	public UserDetailsService userDetailsService() {
	    var manager = new InMemoryUserDetailsManager();

	    manager.createUser(User.withDefaultPasswordEncoder()
	            .username("Alice")
	            .password(("ABC12345"))
	            .roles("USER")
	            .build());

	    return manager;
	}
}
