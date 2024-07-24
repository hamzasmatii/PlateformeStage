package tn.esprit.artifact.entity;

import lombok.*;

import jakarta.persistence.*;
import org.springframework.core.SpringVersion;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_role;
    private int permission;
    @Enumerated(EnumType.STRING)
    private TypeRole typerole;

    @ManyToOne
    User user;
}
