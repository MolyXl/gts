package com.taobao.txc.sample;

import com.alibaba.druid.pool.DruidDataSource;
import com.taobao.txc.datasource.cobar.TxcDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    // We need to figure out which is @Primary DataSource since MybatisAutoConfiguration is marked as @ConditionalOnSingleCandidate(DataSource.class).
    // And @DependsOn the original DruidDataSource to avoid Spring cycle dependency issue.
    @Primary
    @DependsOn({"dataSource"})
    @Bean("dataSourceProxy")
    public TxcDataSource dataSourceProxy(DruidDataSource dataSource) {
        TxcDataSource dataSourceProxy = new TxcDataSource(dataSource);
        return dataSourceProxy;
    }

}
