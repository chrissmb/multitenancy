package com.example.multitenancy.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.multitenancy.central.repository",
        entityManagerFactoryRef = "centralEntityManagerFactory",
        transactionManagerRef = "centralTransactionManager"
)
public class CentralDatabaseConfiguration {

    @Primary
    @Bean(name = "centralDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource centralDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "centralEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean centralEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("centralDataSource") DataSource centralDataSource) {
        return builder.dataSource(centralDataSource)
                .packages("com.example.multitenancy.central.entity")
                .persistenceUnit("central")
                .build();
    }

    @Primary
    @Bean(name = "centralTransactionManager")
    public PlatformTransactionManager centralTransactionManager(
            @Qualifier("centralEntityManagerFactory")
            EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
