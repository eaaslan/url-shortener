package tr.com.eaaslan.urlshortener.entity;

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
    private String original_url;
    @Column(nullable = false)
    private String short_url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //todo create your own static builder class to reduce dependency
    @OneToMany(mappedBy = "urlMapping",cascade = CascadeType.ALL,orphanRemoval = true)
    @Builder.Default
    private List<ClickEvent> clickEvents=new ArrayList<>();
}
