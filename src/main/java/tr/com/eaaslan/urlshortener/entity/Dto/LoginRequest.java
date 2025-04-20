package tr.com.eaaslan.urlshortener.entity.Dto;

public record LoginRequest(
        String username,
        String password
) {
}
