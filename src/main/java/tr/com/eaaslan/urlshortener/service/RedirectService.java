package tr.com.eaaslan.urlshortener.service;

import org.springframework.stereotype.Service;
import tr.com.eaaslan.urlshortener.entity.ClickEvent;
import tr.com.eaaslan.urlshortener.entity.Dto.UrlMappingDto;
import tr.com.eaaslan.urlshortener.entity.UrlMapping;

@Service
public class RedirectService {


    private final UrlMappingService urlMappingService;
    private final ClickEventService clickEventService;

    public RedirectService(UrlMappingService urlMappingService, ClickEventService clickEventService) {
        this.urlMappingService = urlMappingService;
        this.clickEventService = clickEventService;
    }

    public String handleRedirect(String shortURL){
        UrlMappingDto urlMapping= urlMappingService.getUrlMappingByShortUrl(shortURL);
        clickEventService.createClickEvent(urlMapping.id());
        return urlMapping.originalUrl();
    }


}
