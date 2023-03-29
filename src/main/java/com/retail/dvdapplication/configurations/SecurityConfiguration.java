package com.retail.dvdapplication.configurations;

import com.retail.dvdapplication.repositories.User;
import com.retail.dvdapplication.repositories.UserRepository;
import com.retail.dvdapplication.security.DatabaseAuthenticationManager;
import com.retail.dvdapplication.security.MyBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private MyBasicAuthenticationEntryPoint customAuthenticationEntryPoint;
    private UserRepository repository;
    private DatabaseAuthenticationManager manager;

    SecurityConfiguration( DatabaseAuthenticationManager manager,
                           MyBasicAuthenticationEntryPoint customAuthenticationEntryPoint,
                           UserRepository repository) {
        this.manager = manager;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.repository = repository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/**").hasRole("USER")
                )
                .httpBasic(withDefaults());

        BasicAuthenticationFilter base_auth_filter = new BasicAuthenticationFilter(manager);
        http.addFilter(base_auth_filter);

        http.addFilter(new ExceptionTranslationFilter(customAuthenticationEntryPoint));
        
        return http.build();
    }

    @Bean
    public void createUsers() {
        if ( repository.findByName("icsd15201") == null ) {
            repository.save(new User("icsd15201", "password"));

        }
        if ( repository.findByName("t") == null ) {
            repository.save(new User("t", "t"));
        }
    }

}
