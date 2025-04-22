package tr.com.eaaslan.urlshortener.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends BaseEntityAudit{
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String role;
    @Column(unique = true,nullable = false)
    private String username;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Builder.Default
    @JsonManagedReference
    private List<UrlMapping> urlMappings=new ArrayList<>();

}
