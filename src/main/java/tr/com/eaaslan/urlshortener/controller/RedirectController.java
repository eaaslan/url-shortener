package tr.com.eaaslan.urlshortener.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.eaaslan.urlshortener.service.RedirectService;

@RestController
@RequestMapping("/redirect")
public class RedirectController {

    private final RedirectService redirectService;

    public RedirectController( RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl){
        String originalUrl=  redirectService.handleRedirect(shortUrl);
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add("Location",originalUrl);
        return ResponseEntity.status(302).headers(httpHeaders).build();
    }
}
