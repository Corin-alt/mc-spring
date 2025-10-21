package fr.corentin.mcSpring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "fr.corentin.mcSpring.repository")
@ComponentScan(basePackages = "fr.corentin.mcSpring")
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class SpringDataConfig {

    private static final String DEFAULT_DB_PATH = "plugins/mc-spring/data/database";
    private static final String DEFAULT_USERNAME = "sa";
    private static final String DEFAULT_PASSWORD = "";

    @Value("${db.path:" + DEFAULT_DB_PATH + "}")
    private String dbPath;

    @Value("${db.username:" + DEFAULT_USERNAME + "}")
    private String dbUsername;

    @Value("${db.password:" + DEFAULT_PASSWORD + "}")
    private String dbPassword;

    @Value("${hibernate.hbm2ddl.auto:update}")
    private String hbm2ddlAuto;

    @Value("${hibernate.show_sql:true}")
    private String showSql;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(buildDatabaseUrl());
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("fr.corentin.mcSpring.model");
        emf.setJpaVendorAdapter(createVendorAdapter());
        emf.setJpaProperties(createHibernateProperties());
        return emf;
    }

    @Bean
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
        assert emf.getObject() != null;
        return new JpaTransactionManager(emf.getObject());
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private String buildDatabaseUrl() {
        Path dbFilePath = Paths.get(dbPath);
        File dbDirectory = dbFilePath.getParent().toFile();

        if (!dbDirectory.exists()) {
            dbDirectory.mkdirs();
        }

        return String.format("jdbc:h2:file:%s;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE",
                dbFilePath.toAbsolutePath());
    }

    private HibernateJpaVendorAdapter createVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    private Properties createHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.show_sql", showSql);
        return properties;
    }
}