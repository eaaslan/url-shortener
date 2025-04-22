package tr.com.eaaslan.urlshortener.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Table
@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ClickEvent extends BaseEntityAudit{

    public ClickEvent(UrlMapping urlMapping){
        this.urlMapping=urlMapping;
    }

    @ManyToOne
    @JsonBackReference
    private UrlMapping urlMapping;
}
