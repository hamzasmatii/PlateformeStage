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
public class QA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_qa;
    private String question;
    private String reponse;
    @ManyToMany(mappedBy = "qas")
    private Set<User> users;

}
