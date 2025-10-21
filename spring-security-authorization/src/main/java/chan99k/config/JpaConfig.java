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
@EntityScan(basePackageClasses = {EntityModule.class}) // basePackages 는 문자열이므로 오타가 있어도 컴파일 시점에 발견되지 않으므로, basePackageClasses 사용이 권장된다
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
