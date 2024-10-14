package uz.pdp.revolusiondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Random;

@SpringBootApplication
//@EnableCaching
@EnableAsync
public class RevolusionDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RevolusionDemoApplication.class, args);
    }

    @Bean
    public Random random() {
        return new Random();
    }


}
