package tr.com.eaaslan.urlshortener.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.eaaslan.urlshortener.entity.Dto.UrlMappingDto;
import tr.com.eaaslan.urlshortener.entity.User;
import tr.com.eaaslan.urlshortener.service.UrlMappingService;
import tr.com.eaaslan.urlshortener.service.UserService;

import java.security.Principal;
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
}
