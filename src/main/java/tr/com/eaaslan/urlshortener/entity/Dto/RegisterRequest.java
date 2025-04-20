package tr.com.eaaslan.urlshortener.entity.Dto;

public record RegisterRequest(
        String username,
        String email,
        String password
) {
}
