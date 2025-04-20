package tr.com.eaaslan.urlshortener.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClickEvent extends BaseEntityAudit{

    private LocalDateTime clickDate;

    @ManyToOne
    private UrlMapping urlMapping;
}
