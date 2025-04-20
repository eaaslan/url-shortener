package tr.com.eaaslan.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.eaaslan.urlshortener.entity.ClickEvent;

public interface ClickEventRepository extends JpaRepository<ClickEvent,Long> {
}
