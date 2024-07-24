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

public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idsatge;
    private Date datedebut;
    private Date datefin;
    private String sujet;
    private String email;
    private long num;
    @ManyToOne
    Doc doc;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "stage")
    private Set<Conditature> conditatures;
}
