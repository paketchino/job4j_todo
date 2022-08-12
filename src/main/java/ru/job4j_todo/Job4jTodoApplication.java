package ru.job4j_todo;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

@SpringBootApplication
public class Job4jTodoApplication {

	@Bean(destroyMethod = "close")
	public SessionFactory sf() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure().build();
		return new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}


	public static void main(String[] args) {
		SpringApplication.run(Job4jTodoApplication.class, args);
		System.out.println("Go to http://localhost:8080/loginPage");
	}

}
