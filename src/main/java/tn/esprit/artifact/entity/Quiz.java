package tn.esprit.artifact.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private String nom;
    private String description ;
    private int timelimit ;
    private int score ;
    private int note;
    private int id_societe;
    @Enumerated(EnumType.STRING)
    private EtatQ etat;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;





}
