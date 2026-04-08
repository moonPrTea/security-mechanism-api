package dev.moon.security.api_security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
public class AppAuthConfig {

  @Bean
  @ConditionalOnProperty(name = "app.auth.mode", havingValue = "none")
  public SecurityFilterChain noneSecurityChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(csrf -> csrf.disable());
    return httpSecurity.build();
  }


  @Bean
  @ConditionalOnProperty(name = "app.auth.mode", havingValue = "basic")
  public SecurityFilterChain basicSecurityChain(HttpSecurity httpSecurity) throws Exception {
    BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
    entryPoint.setRealmName("security-api");
    entryPoint.afterPropertiesSet();

    httpSecurity
        .authorizeHttpRequests(expressionInterceptUrlRegistry ->
            expressionInterceptUrlRegistry
                .requestMatchers("/user/status").permitAll()
                .anyRequest().authenticated())
        .httpBasic(httpSecurityHttpBasicConfigurer ->
            httpSecurityHttpBasicConfigurer.authenticationEntryPoint(entryPoint));

    return httpSecurity.build();
  }

  @Bean
  @ConditionalOnProperty(name = "app.auth.mode", havingValue = "oauth2")
  public SecurityFilterChain oauthSecurityChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/user/status").permitAll()
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));

    return httpSecurity.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
