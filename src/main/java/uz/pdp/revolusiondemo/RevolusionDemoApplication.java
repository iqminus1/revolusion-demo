package uz.pdp.revolusiondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RevolusionDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RevolusionDemoApplication.class, args);
    }

}
