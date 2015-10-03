package click.rmx.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by bilbowm on 29/09/2015.
 */
@Configuration
@EnableWebSecurity
//@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final boolean USE_REMOTE_DATABASE = false, REQUIRE_LOGIN = false, USE_DATABASE_PASSWORDS = false;

    @Override //TODO should not be overridden?
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        if (!REQUIRE_LOGIN) {
            http.authorizeRequests().anyRequest().permitAll();
            return;
        }
            http //TODO:   <http auto-config="true" use-expressions="true" > (might not be needed)
                .authorizeRequests()
                    .antMatchers("/login.html", "/login.html?error=bad_credentials", "/loginFailed.html", "/logout.html", "/403.html").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/**").hasRole("USER")//hasRole('USER')")// and hasRole('DBA')")
                .anyRequest().permitAll()//.authenticated()
                .and()
                .formLogin()
                    .loginPage("/login.html").failureUrl("/login.html?error=bad_credentials").permitAll() //("/loginFailed.html").permitAll()
                    .defaultSuccessUrl("/boom.html", false)
                    .usernameParameter("j_username")
                    .passwordParameter("j_password")
                    .and()
                .logout()
                    .logoutUrl("/logout.html").permitAll()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout.html", "GET"))
                    .and()
//                .rememberMe()
//                    .tokenValiditySeconds(1209600)
//                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/403.html")
                    .and();
//                .csrf().disable();



    }


//    @Autowired
//    private JdbcDaoImpl jdbcDaol;

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        if (!USE_DATABASE_PASSWORDS)
            auth
                .jdbcAuthentication()
                    .dataSource(dataSource);
        else
            auth
                //In Memory usage
                .inMemoryAuthentication()
                    .withUser("user").password("password").roles("USER").and()
                    .withUser("admin").password("secret").roles("USER", "ADMIN");

    }

//    @Bean //For use with auth.inMemoryAuthentication()
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }


    // Expose the UserDetailsService as a Bean
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }


//Data
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "click.rmx.persistence.model" });

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", USE_REMOTE_DATABASE ? "update" : "create-drop");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true");
        return hibernateProperties;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        if (USE_REMOTE_DATABASE) {
            dataSource.setUrl("jdbc:mysql://devsql.maxbilbow.com/spring_mvc_dev?autoReconnect=true");
//            dataSource.setUrl("jdbc:mysql://rmxdb.c8kzyhurz6of.us-west-2.rds.amazonaws.com:3306/rmx?autoReconnect=true");
            dataSource.setUsername("maxbilbow");
            dataSource.setPassword("Purple22");
        } else {
            dataSource.setUrl("jdbc:mysql://localhost:3306/rmx?autoReconnect=true&createDatabaseIfNotExist=true");
            dataSource.setUsername("root");
            dataSource.setPassword("password");
        }
        return dataSource;
    }


    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }




//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        return new AuthenticationProvider() {
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                System.err.println(authentication.getCredentials());
//                return authentication;
//            }
//
//            @Override
//            public boolean supports(Class<?> authentication) {
//                System.err.println("AuthenticationProvider::supports(Class<?> authentication)");
//                return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
//            }
//        };
//    }

}
