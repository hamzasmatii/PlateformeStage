package tn.esprit.artifact.entity;
import lombok.*;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Doc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_doc;
    private Long id_etud;
    private Long id_societ;
    @Enumerated(EnumType.STRING)
    private TypeDoc typedoc;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "doc")
    private Set<Stage> stages;
}
