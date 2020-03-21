package cn.sign.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import lombok.Data;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;


@Configuration
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidConfiguration {

    private String url;

    private String userName;

    private String driverClassName;

    private String password;

    private int minIdle;

    private int maxWait;

    private int initialSize;

    private int maxActive;

    private long timeBetweenEvictionRunsMillis;

    private Integer removeAbandonedTimeout;

    private boolean defaultReadOnly;

    private String filters;

    private boolean useGlobalDataSourceStat;

    /**
     * 注册一个StatViewServlet
     *
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/cn/sign/config/druid/*");
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", Boolean.FALSE.toString());
        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //.添加不需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    /**
     * 如果不手动注册数据源，就不会显示SQL监控数据，所以这里采用了手动注册数据源方式
     *
     * @return DataSource
     */
    @Bean
    public DataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        // 慢SQL记录
        Properties properties = new Properties();
        properties.put("druid.stat.mergeSql", "true");
        properties.put("druid.stat.slowSqlMillis", "200");
        dataSource.setConnectProperties(properties);
        dataSource.setFilters(filters);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        dataSource.setMaxActive(maxActive);
        dataSource.setInitialSize(initialSize);
        dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        dataSource.setDefaultReadOnly(defaultReadOnly);
        dataSource.setTimeBetweenConnectErrorMillis(timeBetweenEvictionRunsMillis);
        dataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
        return dataSource;
    }

    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    @Bean
    @Scope("prototype")
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern("cn.sign.service.*");
        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor druidStatAdvisor(DruidStatInterceptor druidStatInterceptor, JdkRegexpMethodPointcut druidStatPointcut) {
        DefaultPointcutAdvisor defaultPointAdvisor = new DefaultPointcutAdvisor();
        defaultPointAdvisor.setPointcut(druidStatPointcut);
        defaultPointAdvisor.setAdvice(druidStatInterceptor);
        return defaultPointAdvisor;
    }

}