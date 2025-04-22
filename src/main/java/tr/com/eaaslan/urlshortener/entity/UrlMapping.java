package tr.com.eaaslan.urlshortener.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UrlMapping extends BaseEntityAudit {

    @Column(nullable = false)
    private String originalUrl;
    @Column(nullable = false)
    private String shortUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    //todo create your own static builder class to reduce dependency
    @OneToMany(mappedBy = "urlMapping",cascade = CascadeType.ALL,orphanRemoval = true)
    @Builder.Default
    @JsonManagedReference
    private List<ClickEvent> clickEvents=new ArrayList<>();
}
