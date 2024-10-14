package uz.pdp.revolusiondemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.revolusiondemo.enums.RoleEnum;
import uz.pdp.revolusiondemo.model.User;
import uz.pdp.revolusiondemo.repository.UserRepository;

import java.util.Random;

@SpringBootApplication
@EnableAsync
public class RevolusionDemoApplication {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${app.user.email}")
    private String email;
    @Value("${app.user.name}")
    private String name;
    @Value("${app.user.password}")
    private String password;

    public RevolusionDemoApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(RevolusionDemoApplication.class, args);
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            saveUser();
        };

    }

    @Async
    public void saveUser() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (userRepository.findByEmail(email).isPresent()) {
            return;
        }
        userRepository.save(new User(email, name, passwordEncoder.encode(password), RoleEnum.ADMIN, true, 100000000d));
    }


}
