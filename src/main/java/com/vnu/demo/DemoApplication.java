package com.vnu.demo;

import com.vnu.demo.repository.ApplianceRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@AllArgsConstructor
public class DemoApplication implements CommandLineRunner {
    private final ApplianceRepository applianceRepository;
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
