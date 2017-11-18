import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "website")
public class SpringBoot {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBoot.class, args);
    }
}
