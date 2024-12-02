package br.com.fsbr.gerenciamento_clientes.configuration.profiles.local.database;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile("local")
@EnableJpaRepositories(basePackages = "br.com.fsbr.gerenciamento_clientes.model.repository", entityManagerFactoryRef = "entityManager", transactionManagerRef = "transactionManager")
public class DatabaseAutoConfiguration {

    @Value("${LOCAL_DATASOURCE_JDBCURL:jdbc:h2:mem:local}")
    private String dbJdbcUrl;

    @Value("${LOCAL_DATASOURCE_USER:sa}")
    private String dbUsername;

    @Value("${LOCAL_DATASOURCE_PASSWORD:}")
    private String dbPassword;

    @Bean
    @Primary
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create()
                .url(dbJdbcUrl)
                .username(dbUsername)
                .password(dbPassword)
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(primaryDataSource());
        em.setPackagesToScan(new String[] { "br.com.fsbr.gerenciamento_clientes.model.entity" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.sharedCache.mode", "ENABLE_SELECTIVE");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("spring.h2.console.enabled", true);
        properties.put("spring.h2.console.path", "/h2-console");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager().getObject());
        return transactionManager;
    }

}
