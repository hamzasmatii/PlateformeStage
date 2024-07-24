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
public class Offretravail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_offretrv;
    private Date datedebut;
    private Date datefin;
    private String descrip;
    private String email;
    private long num;
    private String societe;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "offretravail")
    private Set<User> users;

}
