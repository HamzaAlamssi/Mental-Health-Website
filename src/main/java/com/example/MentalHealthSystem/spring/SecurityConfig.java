package com.example.MentalHealthSystem.spring;


import com.example.MentalHealthSystem.Handlers.AuthenticationSuccessHandler;
import com.example.MentalHealthSystem.constants.UserRoles;
import com.example.MentalHealthSystem.service.MentalHealthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MentalHealthUserDetailsService mentalHealthUserDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {

                    registry.requestMatchers("/check/**").permitAll();
                    registry.requestMatchers("/clinic/**").permitAll();
                    registry.requestMatchers("/login").permitAll();
                    registry.requestMatchers("/home/**").permitAll();
                    registry.requestMatchers("/signup/**").permitAll();
                    registry.requestMatchers("/blog/**").permitAll();
                    registry.requestMatchers("/assessments/**").hasRole(UserRoles.PATIENT.name());
                    registry.requestMatchers("/doctors/**").hasRole(UserRoles.DOCTOR.name());
                    registry.requestMatchers("/admins/**").hasRole(UserRoles.ADMIN.name());
                    registry.requestMatchers("/patients/**").hasRole(UserRoles.PATIENT.name());
                    registry.anyRequest().authenticated();


                }).formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/login")
                            .successHandler(new AuthenticationSuccessHandler())
                            .permitAll();
                }).sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .invalidSessionUrl("/logout?expired")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false))
                .logout(logout -> logout.deleteCookies("JSESSIONIO").invalidateHttpSession(true))
                .build();
    }
    
    @Bean
    public UserDetailsService UserDetailsService(){
        return mentalHealthUserDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(mentalHealthUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}