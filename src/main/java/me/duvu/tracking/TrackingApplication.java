package me.duvu.tracking;

import me.duvu.tracking.repository.ExtendedRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = ExtendedRepositoryImpl.class, basePackages = {"me.duvu"})
@EntityScan(basePackages = {"me.duvu.tracking.domain"})
@EnableAspectJAutoProxy
public class TrackingApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TrackingApplication.class, args);
	}
}
