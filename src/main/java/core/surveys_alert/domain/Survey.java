package core.surveys_alert.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pointOfSale;
    private String expectedPrice;
    private String product;
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Answer> answers;


    // Dados usados para parte do Validator
    @Transient // para não persistir no banco de dados
    @JsonIgnore // para não serializar no JSON
    private LocalDate dateAnswered;

    @Transient
    @JsonIgnore
    private String owner;

    public Survey(LocalDate dateAnswered, String owner) {
        this.dateAnswered = dateAnswered;
        this.owner = owner;
    }
}
