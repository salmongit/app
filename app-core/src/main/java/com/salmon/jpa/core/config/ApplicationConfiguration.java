package com.salmon.jpa.core.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.salmon.jpa.core.json.HibernateAwareObjectMapper;
import com.salmon.jpa.core.repositories.BaseRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ImportResource("classpath*:applicationContext-*.xml")
@ComponentScan(basePackages = {"com.**.service","com.**.services"},excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})})
@PropertySource("classpath:config.properties")
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages={"com.**.dao", "com.**.repository","com.**.repositories"},
        entityManagerFactoryRef="entityManagerFactory", transactionManagerRef="transactionManager",repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@EnableScheduling
public class ApplicationConfiguration {

    @Autowired
    private Environment environment;

    @Bean(name="dataSource",destroyMethod = "close")
    @Primary // Primary如果接口实现有多个，优先注入该bean
    public DataSource dataSource() throws PropertyVetoException {
        com.mchange.v2.c3p0.ComboPooledDataSource dataSource = new com.mchange.v2.c3p0.ComboPooledDataSource();
        dataSource.setDriverClass(environment.getProperty("jdbc.driverClassName"));
        dataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
        dataSource.setUser(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        //当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出 SQLException,如设为0则无限期等待。单位毫秒。Default: 0
        dataSource.setCheckoutTimeout(1000);
        //初始化时获取的连接数，取值应在minPoolSize与maxPoolSize之间。Default: 3
        dataSource.setInitialPoolSize(10);
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(60);
        //当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
        dataSource.setAcquireIncrement(5);
        dataSource.setMaxIdleTime(7200);
        //定义在从数据库获取新连接失败后重复尝试的次数。Default: 30
        dataSource.setAcquireRetryAttempts(50);
        dataSource.setMaxStatements(0);
        dataSource.setIdleConnectionTestPeriod(360);
        //两次连接中间隔时间，单位毫秒，默认为1000
        dataSource.setAcquireRetryDelay(1000);
        dataSource.setAutoCommitOnClose(false);
        dataSource.setForceIgnoreUnresolvedTransactions(false);
        dataSource.setUnreturnedConnectionTimeout(1000);
        dataSource.setMaxStatementsPerConnection(0);
        dataSource.setBreakAfterAcquireFailure(true);
        dataSource.setTestConnectionOnCheckin(false);
        dataSource.setTestConnectionOnCheckout(false);
        dataSource.setUsesTraditionalReflectiveProxies(false);
        dataSource.setNumHelperThreads(5);
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform(environment.getProperty("jdbc.databasePlatform"));
        jpaVendorAdapter.setDatabase(Database.valueOf(environment.getProperty("jdbc.database.name","DEFAULT").toUpperCase()));
        jpaVendorAdapter.setShowSql(Boolean.valueOf(environment.getProperty("show.sql","false")));
        Properties jpaProperties = new Properties();
        //jpaProperties.setProperty("hibernate.dialect",environment.getProperty("jdbc.databasePlatform"));
        jpaProperties.setProperty("hibernate.format_sql", environment.getProperty("format.sql","false"));
        jpaProperties.setProperty("hibernate.hbm2ddl.auto",environment.getProperty("hbm2ddl.auto","update"));
        jpaProperties.setProperty("hibernate.show_sql",environment.getProperty("show.sql","false"));
        jpaProperties.setProperty("hibernate.jdbc.fetch_size","50");
        jpaProperties.setProperty("hibernate.jdbc.batch_size","30");
        jpaProperties.setProperty("hibernate.max_fetch_depth","3");
        boolean enableSecondLevelCache = Boolean.valueOf(environment.getProperty("use.second.level.cache", "false"));
        if(enableSecondLevelCache){
            jpaProperties.setProperty("hibernate.cache.use_second_level_cache","true");
            jpaProperties.setProperty("hibernate.cache.region.factory_class",environment.getProperty("second.level.cache.factory","org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory"));
            jpaProperties.setProperty("javax.persistence.sharedCache.mode","ENABLE_SELECTIVE");//开启JPA的二级缓存 使用 @Cacheable注解
        } else {
            jpaProperties.setProperty("hibernate.cache.use_second_level_cache","false");//禁用二级缓存 默认为true
        }
        boolean enableHibernateSearch = Boolean.valueOf(environment.getProperty("hibernate.search.enable","false"));
        if(enableHibernateSearch){
            /*
                hibernate.search.default.directory_provider指定Directory的代理，
                即把索引的文件保存在硬盘中（org.hibernate.search.store.impl.FSDirectoryProvider）
                还是内存里（org.hibernate.search.store. impl. RAMDirectoryProvider），
                保存在硬盘的话hibernate.search.default.indexBase属性指定索引保存的路径。

                指定使用的分词器，可以通过3种方式，一种是在hibernate的配置文件设置词法分析方法
                <property name="hibernate.search.analyzer"> org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer</property>
                另外一种是在每个需要被搜索的类中定义分词方法，
                @Indexed
                @Analyzer(impl=SmartChineseAnalyzer.class)
                最后一种是对单个字段配置
                @Field(analyzer = @Analyzer(impl = AnsjAnalyzer.class))
            */
            //全文检索缓存方式
            jpaProperties.setProperty("hibernate.search.default.directory_provider",environment.getProperty("hibernate.search.directory.provider"));
            //缓存路径
            jpaProperties.setProperty("hibernate.search.default.indexBase",environment.getProperty("hibernate.search.indexBase"));
        }
        //查询缓存设置
        jpaProperties.setProperty("hibernate.cache.use_query_cache", environment.getProperty("use.query.cache", "false"));
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPersistenceProvider(new org.hibernate.jpa.HibernatePersistenceProvider());
        emf.setJpaDialect(new org.springframework.orm.jpa.vendor.HibernateJpaDialect());
        emf.setPackagesToScan(environment.getProperty("jpa.entity.packages.scan"));
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setJpaProperties(jpaProperties);
        return emf;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new HibernateAwareObjectMapper();
    }
}
