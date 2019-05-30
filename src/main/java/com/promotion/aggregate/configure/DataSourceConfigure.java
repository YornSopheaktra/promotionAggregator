package com.promotion.aggregate.configure;

import com.tmc.frmk.core.service.VaultService;
import com.tmc.frmk.core.tools.factory.DataSourceFactoryBean;
import com.tmc.frmk.core.tools.filter.LogOncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfigure {

    private Logger log= LoggerFactory.getLogger(DataSourceConfigure.class);

    @Autowired
    private  VaultService vaultService;

    @Bean
    public DataSource dataSource() {
        DataSourceFactoryBean factoryBean = new DataSourceFactoryBean();
        factoryBean.setDriverClassName(vaultService.get("promotion-aggregation-staging", "driver"));
        factoryBean.setUrl(vaultService.get("promotion-aggregation-staging", "url"));
        factoryBean.setUserName(vaultService.get("promotion-aggregation-staging", "username"));
        factoryBean.setPassword(vaultService.get("promotion-aggregation-staging", "password"));
        factoryBean.setSchema(vaultService.get("promotion-aggregation-staging", "schema"));

        log.info("=====>>>> Obtained database credentials from Vault");
        log.info("=====>>>> driver:" + factoryBean.getDriverClassName());
        log.info("=====>>>> url:" + factoryBean.getUrl());
        log.info("=====>>>> username:" + factoryBean.getUserName());
        log.info("=====>>>> schema:" + factoryBean.getSchema());
        return factoryBean.driverManagerDataSource();
    }

    @Bean
    public LogOncePerRequestFilter logFilter() {
        return new LogOncePerRequestFilter();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return placeholderConfigurer;
    }
}
