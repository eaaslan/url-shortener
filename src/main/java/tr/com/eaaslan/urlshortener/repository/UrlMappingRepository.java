package tr.com.eaaslan.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.eaaslan.urlshortener.entity.ClickEvent;
import tr.com.eaaslan.urlshortener.entity.UrlMapping;

import java.util.List;

public interface UrlMappingRepository extends JpaRepository<UrlMapping,Long> {

    List<UrlMapping> findByUser_Username(String userUsername);

    UrlMapping findByShortUrl(String shortUrl);

    // @Query("SELECT u FROM UrlMapping u WHERE u.user.username = :username")
    //List<UrlMapping> customFindByUsername(@Param("username") String username);
}
