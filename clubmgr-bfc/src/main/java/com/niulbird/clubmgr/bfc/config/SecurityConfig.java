package com.niulbird.clubmgr.bfc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.niulbird.clubmgr.bfc.controller.CustomAuthenticationEntryPoint;
import com.niulbird.clubmgr.bfc.controller.admin.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(CustomAccessDeniedHandler accessDeniedHandler,
                          CustomAuthenticationEntryPoint authenticationEntryPoint) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }
    
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
		
		http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/admin/**").authenticated()
            .anyRequest().permitAll()
        )
        .formLogin(form -> form
            .loginPage("/login.html")
            .loginProcessingUrl("/login")
            .permitAll()
            .defaultSuccessUrl("/admin/players.html")
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        )
        .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
            );

		return http.build();
    }
}
