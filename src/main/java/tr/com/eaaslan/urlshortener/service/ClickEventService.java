package tr.com.eaaslan.urlshortener.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.eaaslan.urlshortener.entity.ClickEvent;
import tr.com.eaaslan.urlshortener.entity.Dto.ClickEventDto;
import tr.com.eaaslan.urlshortener.entity.Dto.ClickEventResponseDto;
import tr.com.eaaslan.urlshortener.entity.UrlMapping;
import tr.com.eaaslan.urlshortener.repository.ClickEventRepository;
import tr.com.eaaslan.urlshortener.repository.UrlMappingRepository;

import java.util.List;

@Service
public class ClickEventService {


    private final ClickEventRepository clickEventRepository;
    private final UrlMappingRepository urlMappingRepository;

    public ClickEventService(ClickEventRepository clickEventRepository, UrlMappingRepository urlMappingRepository) {
        this.clickEventRepository = clickEventRepository;
        this.urlMappingRepository = urlMappingRepository;
    }

    public ClickEventResponseDto createClickEvent(Long urlMappingId){
        UrlMapping urlMapping=urlMappingRepository.findById(urlMappingId).orElseThrow(EntityNotFoundException::new);
        ClickEvent clickEvent = ClickEvent.builder().urlMapping(urlMapping).build();
        clickEventRepository.save(clickEvent);
        return new ClickEventResponseDto(urlMapping.getShortUrl());
    }
//
//    public List<ClickEventDto> getUrlMappingClickEvents(Long urlMappingId){
//        List<ClickEvent> clickEvents=clickEventRepository.findByUrlMapping_Id(urlMappingId);
//        return clickEvents.stream().map(clickEvent -> new ClickEventDto(clickEvent.getUrlMapping().getId())).toList();
//    }

}
