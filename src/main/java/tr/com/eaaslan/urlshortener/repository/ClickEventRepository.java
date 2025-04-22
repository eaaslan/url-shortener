package tr.com.eaaslan.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.eaaslan.urlshortener.entity.ClickEvent;
import tr.com.eaaslan.urlshortener.entity.UrlMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface ClickEventRepository extends JpaRepository<ClickEvent,Long> {

    List<ClickEvent> findByUrlMapping_Id(Long urlMappingId);

    List<ClickEvent> findByUrlMappingAndCreatedAtBetween(UrlMapping urlMapping, LocalDateTime createdAtAfter, LocalDateTime createdAtBefore);

    List<ClickEvent> findByUrlMappingInAndCreatedAtBetween(List<UrlMapping> urlMappings, LocalDateTime createdAtAfter, LocalDateTime createdAtBefore);


}
