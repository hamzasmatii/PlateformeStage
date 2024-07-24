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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    private String nom;
    private String prenom;
    private String email;
    private Long num;
    private String mdp;
    private String image;
    private String descripsociete;
    private String localisationsocie;
    @Enumerated(EnumType.STRING)
    private Niveau niveau;
    @Enumerated(EnumType.STRING)
    private Specialite specialite;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Conditature> conditatures;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<QA> qas;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Event> events;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Formation> formations;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Reclamtion> reclamtions;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private Set<Role> roles;

    @ManyToOne
    Offretravail offretravail;



}
