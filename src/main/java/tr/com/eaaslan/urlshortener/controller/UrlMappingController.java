package tr.com.eaaslan.urlshortener.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.eaaslan.urlshortener.entity.ClickEvent;
import tr.com.eaaslan.urlshortener.entity.Dto.ClickEventDto;
import tr.com.eaaslan.urlshortener.entity.Dto.UrlMappingDto;
import tr.com.eaaslan.urlshortener.entity.UrlMapping;
import tr.com.eaaslan.urlshortener.entity.User;
import tr.com.eaaslan.urlshortener.service.UrlMappingService;
import tr.com.eaaslan.urlshortener.service.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
public class UrlMappingController {

    private final UrlMappingService urlMappingService;
    private final UserService userService;

    public UrlMappingController(UrlMappingService urlMappingService, UserService userService){
        this.urlMappingService=urlMappingService;
        this.userService = userService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlMappingDto> createShortUrl(@RequestBody Map<String,String> request, Principal principal){
            String longUrl= request.get("originalUrl");
            User user= userService.getUserById(principal.getName());
            UrlMappingDto urlMappingDto=urlMappingService.createShortUrl(longUrl,user);
            return ResponseEntity.ok(urlMappingDto);
    }

    @GetMapping
    public ResponseEntity<List<UrlMappingDto>> getAllUrlMapping(){
        return ResponseEntity.ok(urlMappingService.getAllUrlMapping());
    }

    //TODO Change USERNAME PATH TO Principal when add jwt
    @GetMapping("/analytics/{shortUrl}")
    public ResponseEntity<List<ClickEventDto>> getUrlAnalytics(@PathVariable String shortUrl,
                                                               @RequestParam("startDate") String startDate,
                                                               @RequestParam("endDate") String endDate
    ){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start =LocalDate.parse(startDate,dateTimeFormatter);
        LocalDate end= LocalDate.parse(endDate,dateTimeFormatter);
        return ResponseEntity.ok(urlMappingService.getClickEventsByDate(shortUrl,start,end)) ;
    }

    //TODO Change USERNAME PATH TO Principal when add jwt
    @GetMapping("/analytics/{username}/totalClicks")
    public ResponseEntity<List<ClickEventDto>> getUserAnalytics(@PathVariable String username,
                                                             @RequestParam("startDate") String startDate,
                                                             @RequestParam("endDate") String endDate
    ){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start =LocalDate.parse(startDate,dateTimeFormatter);
        LocalDate end= LocalDate.parse(endDate,dateTimeFormatter);
            return ResponseEntity.ok(urlMappingService.getTotalClicksByUserAndDate(username,start,end)) ;
    }


    @GetMapping("/user-mappings/{username}")
    public ResponseEntity<List<UrlMappingDto>> getUserUrlMappings(@PathVariable String username) {
        return ResponseEntity.ok(urlMappingService.getUserUrlMappings(username));
    }
}
