package tr.com.eaaslan.urlshortener.entity.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClickEventDto(LocalDate createdAt, Long count) {

}
