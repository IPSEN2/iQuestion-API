package org.spine.iquestionapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * The security configuration
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired private JWTFilter filter;
    @Autowired private MyUserDetailsService uds;

    /**
     * This filter chain is used to filter every request.
     * It checks roles and headers.
     * @param http the security config 
     * @return the filter chain
     * @throws Exception if the filter chain fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/auth/register").hasRole("SPINE_ADMIN")
                .antMatchers("/entry/export/**").hasAnyRole("SPINE_USER", "SPINE_ADMIN")
                .antMatchers("/entry/**").hasRole("CAREGIVER")
                .antMatchers(HttpMethod.GET, "/questionnaire/all").authenticated()
                .antMatchers(HttpMethod.PUT, "/questionnaire/").hasAnyRole("SPINE_USER", "SPINE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/questionnaire/{id}").hasAnyRole("SPINE_USER", "SPINE_ADMIN")
                .antMatchers("/user/me").authenticated()
                .antMatchers("/user/**").hasRole("SPINE_ADMIN")
                .and()
                .userDetailsService(uds)
                .exceptionHandling()
                    .authenticationEntryPoint(
                            (request, response, authException) ->
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                    )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * This bean is used to fetch the preconfigured password encoder
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This bean is used to fetch the authentication manager
     * @param authenticationConfiguration the authentication configuration
     * @return the authentication manager
     * @throws Exception if the authentication manager cannot be created
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}