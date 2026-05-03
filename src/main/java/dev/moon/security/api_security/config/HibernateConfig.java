package dev.moon.security.api_security.config;

import dev.moon.security.api_security.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

  @Bean
  private static SessionFactory createSessionFactory() {
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .loadProperties("hibernate.properties")
            .build();

    Metadata metadata = new MetadataSources(serviceRegistry)
            .addAnnotatedClass(Users.class)
            .addAnnotatedClass(Currency.class)
            .addAnnotatedClass(OperationType.class)
            .addAnnotatedClass(UserCard.class)
            .addAnnotatedClass(FinancialOperation.class)
            .addAnnotatedClass(IdempotencyKeys.class)
            .buildMetadata();

    return metadata.buildSessionFactory();
  }

}
