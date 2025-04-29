package com.niulbird.clubmgr.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
@EnableAutoConfiguration(exclude = { FreeMarkerAutoConfiguration.class })
public class FixtureJobApp {
	public static void main(String[] args) {
		SpringApplication.run(FixtureJobApp.class);
	}
}
