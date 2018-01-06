import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "base")
public class SpringBoot {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBoot.class, args);
    }
}
