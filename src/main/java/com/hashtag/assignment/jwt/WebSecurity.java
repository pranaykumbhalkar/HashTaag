package com.hashtag.assignment.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Created By Pranay on 8/31/2018
 */

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private UserDetailsService userDetailsService;
  public static final String SWAGGER = "/swagger**";
  public static final String SWAGGER2 = "/configuration/**";
  public static final String SWAGGER3 = "/error";
  public static final String SWAGGER4 = "/v2/api-docs";
  public static final String SWAGGER5 = "/webjars/**";
  public static final String SWAGGER6 = "/favicon.ico";
  public static final String SWAGGER7 = "/swagger-ui.html#/";
  public static final String SWAGGER8 = "/swagger-ui.html";


  @Autowired
  public WebSecurity(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
        .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_IN_URL).permitAll()
        .antMatchers(SWAGGER).permitAll()
        .antMatchers(SWAGGER2).permitAll()
        .antMatchers(SWAGGER3).permitAll()
        .antMatchers(SWAGGER4).permitAll()
        .antMatchers(SWAGGER5).permitAll()
        .antMatchers(SWAGGER6).permitAll()
        .antMatchers(SWAGGER7).permitAll()
        .antMatchers(SWAGGER8).permitAll()
        .antMatchers(
                HttpMethod.GET,
                "/",
                "/v2/api-docs",           // swagger
                "/webjars/**",            // swagger-ui webjars
                "/swagger-resources/**",  // swagger-ui resources
                "/configuration/**",      // swagger configuration
                "/*.html"
        ).permitAll()

        .anyRequest().authenticated()
        .and()
        .addFilter(new JWTAuthenticationFilter(authenticationManager()))
        .addFilter(new JWTAuthorizationFilter(authenticationManager()))
        // this disables session creation on Spring Security
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }

}