package com.promotion.aggregate.configure;

import java.util.Properties;

import javax.sql.DataSource;

import com.tmc.frmk.core.service.RedisService;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.tmc.frmk.core.service.VaultService;

@Configuration
@EnableTransactionManagement
public class HibernateConfigure {

    @Autowired
    private VaultService vaultService;

    private Logger log = LoggerFactory.getLogger(DataSourceConfigure.class);

    @Autowired
    RedisService redisService;

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.promotion.aggregate.domain", "com.promotion.aggregate.dto");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        transactionManager.setNestedTransactionAllowed(true);
        return transactionManager;
    }

    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "update|");
        properties.setProperty("hibernate.generate_statistics", "false");
        properties.setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        //properties.setProperty("hibernate.cache.use_second_level_cache", "true");
        //properties.setProperty("hibernate.cache.use_query_cache", "true");
        //properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
       // properties.setProperty("net.sf.ehcache.configurationResourceName", "/cache/ehcache.xml");
        properties.setProperty("hibernate.default_schema", vaultService.get("promotion-aggregation-staging", "schema"));
        return properties;
    }
}
