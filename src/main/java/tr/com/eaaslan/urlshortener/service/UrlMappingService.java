package tr.com.eaaslan.urlshortener.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.eaaslan.urlshortener.entity.ClickEvent;
import tr.com.eaaslan.urlshortener.entity.Dto.ClickEventDto;
import tr.com.eaaslan.urlshortener.entity.Dto.UrlMappingDto;
import tr.com.eaaslan.urlshortener.entity.UrlMapping;
import tr.com.eaaslan.urlshortener.entity.User;
import tr.com.eaaslan.urlshortener.repository.ClickEventRepository;
import tr.com.eaaslan.urlshortener.repository.UrlMappingRepository;
import tr.com.eaaslan.urlshortener.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UrlMappingService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UrlMappingRepository urlMappingRepository;
    private final ClickEventRepository clickEventRepository;

    public UrlMappingService(UserService userService, UserRepository userRepository, UrlMappingRepository urlMappingRepository, ClickEventRepository clickEventRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.urlMappingRepository = urlMappingRepository;
        this.clickEventRepository = clickEventRepository;
    }

    public UrlMappingDto getUrlMappingByShortUrl(String shortURL){
        UrlMapping urlMapping= urlMappingRepository.findByShortUrl(shortURL).orElseThrow(EntityNotFoundException::new);
        return toDto(urlMapping);
    }

    public UrlMappingDto createShortUrl(String longUrl, User user) {
        String shortUrl=generateShortUrl();
        UrlMapping urlMapping=UrlMapping.builder()
                .originalUrl(longUrl)
                .shortUrl(shortUrl)
                .user(user)
                .build();

        urlMappingRepository.save(urlMapping);
        return toDto(urlMapping);
    }

    public UrlMapping fromDto(UrlMappingDto urlMappingDto){
        User user=userRepository.findUserByUsername(urlMappingDto.username()).orElseThrow(()->new UsernameNotFoundException("Username not found: "+urlMappingDto.username()));
        return UrlMapping.builder()
                .user(user)
                .shortUrl(urlMappingDto.shortUrl())
                .originalUrl(urlMappingDto.originalUrl())
                .build();
    }

    public UrlMappingDto toDto(UrlMapping urlMapping){
        return new UrlMappingDto(
                urlMapping.getId(),
                urlMapping.getOriginalUrl(),
                urlMapping.getShortUrl(),
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

    public List<UrlMappingDto> getAllUrlMapping() {
      return  urlMappingRepository.findAll().stream().map(this::toDto).toList() ;
    }

    public List<UrlMappingDto> getUserUrlMappings(String username) {
        return urlMappingRepository.findByUser_Username(username).stream().map(this::toDto).toList();
    }

    public List<ClickEventDto> getClickEventsByDate(String shortUrl, LocalDate start, LocalDate end) {
        UrlMapping urlMapping= urlMappingRepository.findByShortUrl(shortUrl).orElseThrow(EntityNotFoundException::new);
        List<ClickEvent> clickEvents=clickEventRepository.findByUrlMappingAndCreatedAtBetween(urlMapping,start.atStartOfDay(),end.plusDays(1).atStartOfDay());
      return clickEvents.stream().collect(Collectors.groupingBy(click->click.getCreatedAt().toLocalDate(),Collectors.counting()))
               .entrySet().stream()
               .map(entry-> (new ClickEventDto(entry.getKey(),entry.getValue()))).toList();
    }
        //todo add principal instead of username after you activate the jwt
    public List<ClickEventDto> getTotalClicksByUserAndDate(String username, LocalDate start, LocalDate end) {
      List<UrlMapping> urlMappingList= urlMappingRepository.findByUser_Username(username);
      List<ClickEvent> clickEventList=  clickEventRepository.findByUrlMappingInAndCreatedAtBetween(urlMappingList,start.atStartOfDay(),end.plusDays(1).atStartOfDay());
      Map<LocalDate,Long> localDateLongMap= clickEventList.stream().collect(Collectors.groupingBy(clickEvent -> clickEvent.getCreatedAt().toLocalDate(),Collectors.counting()));
      return localDateLongMap.entrySet().stream().map(entry->(new ClickEventDto(entry.getKey(),entry.getValue()))).toList();
    }


    public String getOriginalUrl(String shortUrl) {
        return urlMappingRepository.findByShortUrl(shortUrl).orElseThrow(EntityNotFoundException::new).getOriginalUrl();
    }
}
