package fastcampus.directionsService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DirectionsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirectionsServiceApplication.class, args);
	}

}
