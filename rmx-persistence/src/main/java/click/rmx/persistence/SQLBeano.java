package click.rmx.persistence;

import click.rmx.debug.Bugger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

/**
 * Created by Max on 12/10/2015.
 */
public interface SQLBeano {

    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean();


    DriverManagerDataSource driverManagerDataSourceBean();


    PlatformTransactionManager transactionManager(final EntityManagerFactory emf);
}
