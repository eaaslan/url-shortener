package tr.com.eaaslan.urlshortener.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.eaaslan.urlshortener.entity.Dto.UrlMappingDto;
import tr.com.eaaslan.urlshortener.entity.UrlMapping;
import tr.com.eaaslan.urlshortener.entity.User;
import tr.com.eaaslan.urlshortener.repository.UrlMappingRepository;
import tr.com.eaaslan.urlshortener.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlMappingService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UrlMappingRepository urlMappingRepository;

    public UrlMappingService(UserService userService, UserRepository userRepository, UrlMappingRepository urlMappingRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.urlMappingRepository = urlMappingRepository;
    }

    public UrlMappingDto createShortUrl(String longUrl, User user) {
        String shortUrl=generateShortUrl();
        UrlMapping urlMapping=UrlMapping.builder()
                .original_url(longUrl)
                .short_url(shortUrl)
                .user(user)
                .build();

        urlMappingRepository.save(urlMapping);
        return toDto(urlMapping);
    }

    public UrlMapping fromDto(UrlMappingDto urlMappingDto){
        User user=userRepository.findUserByUsername(urlMappingDto.username()).orElseThrow(()->new UsernameNotFoundException("Username not found: "+urlMappingDto.username()));
        return UrlMapping.builder()
                .user(user)
                .short_url(urlMappingDto.shortUrl())
                .original_url(urlMappingDto.originalUrl())
                .build();
    }

    public UrlMappingDto toDto(UrlMapping urlMapping){
        return new UrlMappingDto(
                urlMapping.getId(),
                urlMapping.getOriginal_url(),
                urlMapping.getShort_url(),
                Optional.ofNullable(urlMapping.getClickEvents()).map(List::size).orElseThrow(() -> new IllegalStateException("Click events list is null")),
                urlMapping.getUser().getUsername());
    }

    private String generateShortUrl() {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(allowedChars.length());
            shortUrl.append(allowedChars.charAt(index));
        }

        return shortUrl.toString();
    }

}
