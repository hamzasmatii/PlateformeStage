package tn.esprit.artifact.entity;

import lombok.*;

import jakarta.persistence.*;
import org.springframework.core.SpringVersion;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reclamtion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_reclamation;
    private Date date;
    private String contenu;
    private String piece;
    @Enumerated(EnumType.STRING)
    private StateRec staterec;

    @ManyToMany(mappedBy = "reclamtions")
    private Set<User> users;
}
