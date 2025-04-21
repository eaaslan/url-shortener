package tr.com.eaaslan.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.eaaslan.urlshortener.entity.UrlMapping;

public interface UrlMappingRepository extends JpaRepository<UrlMapping,Long> {

}
