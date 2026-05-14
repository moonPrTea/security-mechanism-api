package dev.moon.security.api_security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, "/user").permitAll()
                    .requestMatchers("/user/status").permitAll()
                    .anyRequest().authenticated())
            .csrf(AbstractHttpConfigurer::disable);
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
                            .requestMatchers(HttpMethod.POST, "/user").permitAll()
                            .requestMatchers("/user/status").permitAll()
                            .anyRequest().authenticated())
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(httpSecurityHttpBasicConfigurer ->
                    httpSecurityHttpBasicConfigurer.authenticationEntryPoint(entryPoint));

    return httpSecurity.build();
  }

  @Bean
  @ConditionalOnProperty(name = "app.auth.mode", havingValue = "oauth2")
  public SecurityFilterChain oauthSecurityChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, "/user").permitAll()
                    .requestMatchers("/user/status").permitAll()
                    .anyRequest().authenticated())
            .csrf(AbstractHttpConfigurer::disable)
            .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));

    return httpSecurity.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
