package tr.com.eaaslan.urlshortener.entity.Dto;


public record UrlMappingDto(
       Long id,
       String originalUrl,
       String shortUrl,
       int clickCount,
       String username
) {
}
