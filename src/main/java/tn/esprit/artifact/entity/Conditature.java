package tn.esprit.artifact.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.core.SpringVersion;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Conditature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_candidature;
    private String nom ;
    private String cv ;
    private String lettre_motivation;
    private long idStudent;
    private long idSociete;
    private long idquiz;

    @Enumerated(EnumType.STRING)
    private Statut statut;
    @ManyToOne
    Stage stage;
    @OneToOne
    private Quiz quiz;

    @ManyToMany(mappedBy = "conditatures")
    private Set<User> users;
}
