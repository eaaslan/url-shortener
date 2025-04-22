package tr.com.eaaslan.urlshortener;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tr.com.eaaslan.urlshortener.entity.ClickEvent;
import tr.com.eaaslan.urlshortener.entity.UrlMapping;
import tr.com.eaaslan.urlshortener.entity.User;
import tr.com.eaaslan.urlshortener.repository.ClickEventRepository;
import tr.com.eaaslan.urlshortener.repository.UrlMappingRepository;
import tr.com.eaaslan.urlshortener.repository.UserRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest()
public class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClickEventRepository clickEventRepository;

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @BeforeEach
    public void setUp() {

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
                .urlMapping(urlMapping)
                .build();

        clickEventRepository.save(clickEvent);
    }


    @Test
    void testUserAndUrlMappingRelation(){

        User user=userRepository.findUserByEmail("test@example.com");

        assertNotNull(user);
        assertThat(urlMappingRepository.findAll()).hasSize(1);
        assertThat(user.getUrlMappings()).isNotNull();
    }

    @Test
    void testUrlMappingAndClickEventRelation() throws InterruptedException {
        User user= userRepository.findUserByEmail("test@example.com");
        UrlMapping urlMapping=urlMappingRepository.findAll().getFirst();
        assertThat(urlMapping.getClickEvents()).hasSize(1);

    }



}
