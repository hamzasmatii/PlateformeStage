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
public class FileCandidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_file ;
    private  Long idStudent;
    private Long id_candidature;
    private String cv;
    private String lettre_motivation;
    private Boolean accepter;
    private Long note;
}
