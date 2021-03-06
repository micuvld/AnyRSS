
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = "base")
public class SpringBoot {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBoot.class, args);
    }
}
