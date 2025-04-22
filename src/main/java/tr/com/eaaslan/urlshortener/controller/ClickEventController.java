package tr.com.eaaslan.urlshortener.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.eaaslan.urlshortener.entity.Dto.ClickEventDto;
import tr.com.eaaslan.urlshortener.entity.Dto.ClickEventResponseDto;
import tr.com.eaaslan.urlshortener.service.ClickEventService;

import java.util.List;

@RestController
@RequestMapping("/click-event")
public class ClickEventController {

    private final ClickEventService clickEventService;

    public ClickEventController(ClickEventService clickEventService) {
        this.clickEventService = clickEventService;
    }

    @PostMapping("/{urlMappingId}")
    public ResponseEntity<ClickEventResponseDto> createClickEvent(@PathVariable Long urlMappingId){
        ClickEventResponseDto clickEventResponseDto=clickEventService.createClickEvent(urlMappingId);
        return ResponseEntity.ok(clickEventResponseDto);
    }
//
//    @GetMapping("/{urlMappingId}")
//    public ResponseEntity<List<ClickEventDto>> getUrlMappingClickEvents(@PathVariable Long urlMappingId){
//        List<ClickEventDto> clickEventDtoList= clickEventService.getUrlMappingClickEvents(urlMappingId);
//        return ResponseEntity.ok(clickEventDtoList);
//    }
}
