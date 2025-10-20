package chan99k.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import chan99k.entitiy.EntityModule;
import chan99k.repository.RepositoryModule;

@Configuration
@EntityScan(basePackageClasses = {EntityModule.class}) // package-info 로도 가능하지 않나...?
@EnableJpaRepositories(basePackageClasses = {RepositoryModule.class})
public class JpaConfig {
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

	@Bean
	public TransactionTemplate writeTransactionOperations(PlatformTransactionManager transactionManager) {
		var transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.setReadOnly(false);
		return transactionTemplate;
	}

	@Bean
	public TransactionTemplate readTransactionOperations(PlatformTransactionManager transactionManager) {
		var txTemplate = new TransactionTemplate(transactionManager);
		txTemplate.setReadOnly(true);
		return txTemplate;
	}
}
