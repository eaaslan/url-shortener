package tr.com.eaaslan.urlshortener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tr.com.eaaslan.urlshortener.entity.ClickEvent;
import tr.com.eaaslan.urlshortener.entity.UrlMapping;
import tr.com.eaaslan.urlshortener.entity.User;
import tr.com.eaaslan.urlshortener.repository.ClickEventRepository;
import tr.com.eaaslan.urlshortener.repository.UrlMappingRepository;
import tr.com.eaaslan.urlshortener.repository.UserRepository;

import java.time.LocalDateTime;

@SpringBootApplication
public class UrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }

    //@Bean
    public CommandLineRunner commandLineRunner(
            ClickEventRepository clickEventRepository,
            UrlMappingRepository urlMappingRepository,
            UserRepository userRepository

    ){
        return args -> {
            User user = User.builder()
                    .email("test@example.com")
                    .username("testuser")
                    .password("password")
                    .build();

            user = userRepository.save(user);

            UrlMapping urlMapping=UrlMapping.builder()
                    .original_url("Long url")
                    .short_url("Short url")
                    .user(user)
                    .build();

            urlMappingRepository.save(urlMapping);

            ClickEvent clickEvent= ClickEvent.builder()
                    .clickDate(LocalDateTime.now())
                    .urlMapping(urlMapping)
                    .build();

            clickEventRepository.save(clickEvent);
        };
    }
}
